package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
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
public class BreedServiceImplementation implements BreedService {
    private final BreedMapper breedMapper;
    private final BreedRepository breedRepository;
    private final SpeciesRepository speciesRepository;

    @Transactional(readOnly = true)
    @Override
    public BreedDto findBreedById(Long id) {
        Breed breed = breedRepository.getBreedById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Breed not found for this id: " + id));

        return breedMapper.breedToBreedDto(breed);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BreedDto> findAllBreeds() {
        return breedRepository.findAll()
                .stream()
                .map(breedMapper::breedToBreedDto)
                .toList();
    }

    @Override
    public BreedDto createBreed(BreedCreateDto breedCreateDto) {
        Breed breed = breedMapper.breedCreateDtoToBreed(breedCreateDto);

        Species species = speciesRepository.getSpeciesById(breedCreateDto.getSpeciesId())
                .orElseThrow(() -> new ResourceNotFoundException("Species not found for this id: " + breedCreateDto.getSpeciesId()));
        breed.setSpecies(species);

        try {
            breedRepository.save(breed);
            return breedMapper.breedToBreedDto(breed);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceNotFoundException("Breed already exists with this name: " + breed.getName());
        }
    }

    @Override
    public BreedDto updateBreed(Long id, BreedUpdateDto breedUpdateDto) {
        Breed breed = breedRepository.getBreedById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Breed not found for this id: " + id));

        if (breedUpdateDto.getName() != null) {
            breed.setName(breedUpdateDto.getName());
        }

        if (breedUpdateDto.getSpeciesId() != null) {
            Species species = speciesRepository.getSpeciesById(breedUpdateDto.getSpeciesId())
                    .orElseThrow(() -> new ResourceNotFoundException("Species not found for this id: " + breedUpdateDto.getSpeciesId()));
            breed.setSpecies(species);
        }

        return breedMapper.breedToBreedDto(breed);
    }

    @Override
    public void deleteBreed(Long id) {
        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Breed not found for this id: " + id));

        try {
            breedRepository.delete(breed);
        } catch (DataIntegrityViolationException e) {
            throw new UsedResourceException("Cannot delete breed with id: " + id + " because it is associated with other resources");
        }
    }
}
