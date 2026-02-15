package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.aleksabuncic.domain.Breed;
import raf.aleksabuncic.domain.Species;
import raf.aleksabuncic.dto.*;
import raf.aleksabuncic.exception.BadRequestException;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.exception.UsedResourceException;
import raf.aleksabuncic.mapper.BreedMapper;
import raf.aleksabuncic.repository.BreedRepository;
import raf.aleksabuncic.repository.SpeciesRepository;
import raf.aleksabuncic.service.BreedService;
import raf.aleksabuncic.utils.SortUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BreedServiceImplementation implements BreedService {
    private final BreedMapper breedMapper;
    private final BreedRepository breedRepository;
    private final SpeciesRepository speciesRepository;

    @Transactional(readOnly = true)
    @Override
    public BreedDto findBreedById(Long id) {
        log.info("Finding breed by id: {}", id);

        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Breed not found for this id: " + id));

        return breedMapper.breedToBreedDto(breed);
    }

    private List<BreedDto> findAllBreeds() {
        return breedRepository.findAll()
                .stream()
                .map(breedMapper::breedToBreedDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<BreedDto> findAllBreeds(PaginationDto paginationDto) {
        if (paginationDto == null) {
            return findAllBreeds();
        }

        Integer page = paginationDto.getPage();
        Integer size = paginationDto.getSize();
        String sortBy = paginationDto.getSortBy();
        String direction = paginationDto.getDirection();

        if (page == null && size == null) {
            return findAllBreeds();
        }

        if (page == null || size == null) {
            throw new BadRequestException("Page and size must be provided together");
        }

        if (direction == null) {
            direction = Sort.Direction.ASC.name();
        }

        if (sortBy == null) {
            sortBy = "id";
        }

        if (!SortUtils.isValidField(Breed.class, sortBy)) {
            throw new BadRequestException("Invalid sort field");
        }

        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortBy);

        log.info("Finding all appointments with pagination: Page {} of size {}", page, size);

        return breedRepository.findBy(pageable)
                .map(breedMapper::breedToBreedDto);
    }

    @Override
    public BreedDto createBreed(BreedCreateDto breedCreateDto) {
        log.info("Creating breed: {}", breedCreateDto);

        boolean existingBreed = breedRepository.existsByName(breedCreateDto.getName());
        if (existingBreed) {
            throw new DuplicateResourceException("Breed already exists with this name: " + breedCreateDto.getName());
        }

        Breed breed = breedMapper.breedCreateDtoToBreed(breedCreateDto);

        Species species = speciesRepository.findById(breedCreateDto.getSpeciesId())
                .orElseThrow(() -> new ResourceNotFoundException("Species not found for this id: " + breedCreateDto.getSpeciesId()));
        breed.setSpecies(species);

        try {
            breedRepository.save(breed);
            log.info("Breed created: {}", breed);
            return breedMapper.breedToBreedDto(breed);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceNotFoundException("Breed already exists with this name: " + breed.getName());
        }
    }

    @Override
    public BreedDto updateBreed(Long id, BreedUpdateDto breedUpdateDto) {
        log.info("Updating breed: {}", breedUpdateDto);

        boolean existingBreed = breedRepository.existsByName(breedUpdateDto.getName());

        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Breed not found for this id: " + id));

        if (breedUpdateDto.getName() != null) {
            if (breed.getName().equals(breedUpdateDto.getName())) {
                throw new DuplicateResourceException("Breed name cannot be the same as the old one");
            }

            if (existingBreed) {
                throw new DuplicateResourceException("Breed already exists with this name: " + breedUpdateDto.getName());
            }
        }

        if (breedUpdateDto.getSpeciesId() != null) {
            Species species = speciesRepository.findById(breedUpdateDto.getSpeciesId())
                    .orElseThrow(() -> new ResourceNotFoundException("Species not found for this id: " + breedUpdateDto.getSpeciesId()));
            breed.setSpecies(species);
        }

        breedMapper.breedUpdateDtoToBreed(breedUpdateDto, breed);

        try {
            breedRepository.save(breed);
            log.info("Breed updated: {}", breed);
            return breedMapper.breedToBreedDto(breed);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("There was a problem updating the breed");
        }
    }

    @Override
    public void deleteBreed(Long id) {
        log.info("Deleting breed with id: {}", id);

        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Breed not found for this id: " + id));

        try {
            breedRepository.delete(breed);
            log.info("Breed deleted: {}", breed);
        } catch (DataIntegrityViolationException e) {
            throw new UsedResourceException("Cannot delete breed with id: " + id + " because it is associated with other resources");
        }
    }
}
