package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.aleksabuncic.domain.Species;
import raf.aleksabuncic.dto.PaginationDto;
import raf.aleksabuncic.dto.SpeciesDto;
import raf.aleksabuncic.dto.SpeciesCreateDto;
import raf.aleksabuncic.dto.SpeciesUpdateDto;
import raf.aleksabuncic.exception.BadRequestException;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.exception.UsedResourceException;
import raf.aleksabuncic.mapper.SpeciesMapper;
import raf.aleksabuncic.repository.SpeciesRepository;
import raf.aleksabuncic.service.SpeciesService;
import raf.aleksabuncic.utils.SortUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SpeciesServiceImplementation implements SpeciesService {
    private final SpeciesMapper speciesMapper;
    private final SpeciesRepository speciesRepository;

    @Transactional(readOnly = true)
    @Override
    public SpeciesDto findSpeciesById(Long id) {
        log.info("Finding species by id: {}", id);

        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Species not found for this id: " + id));

        return speciesMapper.speciesToSpeciesDto(species);
    }

    private List<SpeciesDto> findAllSpecies() {
        log.info("Finding all species");

        return speciesRepository.findAll()
                .stream()
                .map(speciesMapper::speciesToSpeciesDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<SpeciesDto> findAllSpecies(PaginationDto paginationDto) {
        if (paginationDto == null) {
            return findAllSpecies();
        }

        Integer page = paginationDto.getPage();
        Integer size = paginationDto.getSize();
        String sortBy = paginationDto.getSortBy();
        String direction = paginationDto.getDirection();

        if (page == null && size == null) {
            return findAllSpecies();
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

        if (!SortUtils.isValidField(Species.class, sortBy)) {
            throw new BadRequestException("Invalid sort field");
        }

        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortBy);

        log.info("Finding all appointments with pagination: Page {} of size {}", page, size);

        return speciesRepository.findBy(pageable)
                .map(speciesMapper::speciesToSpeciesDto);

    }

    @Override
    public SpeciesDto createSpecies(SpeciesCreateDto speciesCreateDto) {
        log.info("Creating species: {}", speciesCreateDto);

        boolean existingSpecies = speciesRepository.existsByName(speciesCreateDto.getName());
        if (existingSpecies) {
            throw new DuplicateResourceException("Species already exists for this name: " + speciesCreateDto.getName());
        }

        Species species = speciesMapper.speciesCreateDtoToSpecies(speciesCreateDto);

        try {
            speciesRepository.save(species);
            log.info("Species created: {}", species);
            return speciesMapper.speciesToSpeciesDto(species);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Species already exists for this name: " + speciesCreateDto.getName());
        }
    }

    @Override
    public SpeciesDto updateSpecies(Long id, SpeciesUpdateDto speciesUpdateDto) {
        log.info("Updating species: {}", speciesUpdateDto);

        boolean existingSpecies = speciesRepository.existsByName(speciesUpdateDto.getName());

        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Species not found for this id: " + id));

        if (speciesUpdateDto.getName() != null) {
            if (species.getName().equals(speciesUpdateDto.getName())) {
                throw new DuplicateResourceException("Species name cannot be the same as the old one");
            }

            if (existingSpecies) {
                throw new DuplicateResourceException("Species already exists for this name: " + speciesUpdateDto.getName());
            }
        }

        speciesMapper.speciesUpdateDtoToSpecies(speciesUpdateDto, species);

        try {
            speciesRepository.save(species);
            log.info("Species updated: {}", species);
            return speciesMapper.speciesToSpeciesDto(species);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("There was a problem updating the species");
        }
    }

    @Override
    public void deleteSpecies(Long id) {
        log.info("Deleting species with id: {}", id);

        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Species not found for this id: " + id));

        try {
            speciesRepository.delete(species);
            log.info("Species deleted: {}", species);
        } catch (DataIntegrityViolationException e) {
            throw new UsedResourceException("Cannot delete species with id: " + id + " because it is associated with other resources");
        }
    }
}
