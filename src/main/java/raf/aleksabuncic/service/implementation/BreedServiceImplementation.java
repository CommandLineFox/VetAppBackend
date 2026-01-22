package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.aleksabuncic.domain.Breed;
import raf.aleksabuncic.domain.Species;
import raf.aleksabuncic.dto.BreedDto;
import raf.aleksabuncic.dto.BreedCreateDto;
import raf.aleksabuncic.dto.BreedUpdateDto;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.exception.UsedResourceException;
import raf.aleksabuncic.mapper.BreedMapper;
import raf.aleksabuncic.repository.BreedRepository;
import raf.aleksabuncic.repository.SpeciesRepository;
import raf.aleksabuncic.service.BreedService;

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

    @Transactional(readOnly = true)
    @Override
    public List<BreedDto> findAllBreeds() {
        log.info("Finding all breeds");

        return breedRepository.findAll()
                .stream()
                .map(breedMapper::breedToBreedDto)
                .toList();
    }

    @Override
    public BreedDto createBreed(BreedCreateDto breedCreateDto) {
        log.info("Creating breed: {}", breedCreateDto);

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

        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Breed not found for this id: " + id));

        if (breedUpdateDto.getName() != null) {
            breed.setName(breedUpdateDto.getName());
        }

        if (breedUpdateDto.getSpeciesId() != null) {
            Species species = speciesRepository.findById(breedUpdateDto.getSpeciesId())
                    .orElseThrow(() -> new ResourceNotFoundException("Species not found for this id: " + breedUpdateDto.getSpeciesId()));
            breed.setSpecies(species);
        }

        log.info("Breed updated: {}", breed);
        return breedMapper.breedToBreedDto(breed);
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
