package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import raf.aleksabuncic.domain.Breed;
import raf.aleksabuncic.domain.Species;
import raf.aleksabuncic.dto.BreedDto;
import raf.aleksabuncic.dto.BreedCreateDto;
import raf.aleksabuncic.dto.BreedUpdateDto;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.mapper.BreedMapper;
import raf.aleksabuncic.repository.BreedRepository;
import raf.aleksabuncic.repository.SpeciesRepository;
import raf.aleksabuncic.service.BreedService;

@Service
@RequiredArgsConstructor
public class BreedServiceImplementation implements BreedService {
    private final BreedMapper breedMapper;
    private final BreedRepository breedRepository;
    private final SpeciesRepository speciesRepository;

    @Override
    public BreedDto findBreedById(Long id) {
        Breed breed = breedRepository.getBreedById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Breed not found for this id: " + id));

        return breedMapper.breedToBreedDto(breed);
    }

    @Override
    public BreedDto createBreed(BreedCreateDto breedCreateDto) {
        Breed breed = breedMapper.breedCreateDtoToBreed(breedCreateDto);

        Species species = speciesRepository.getSpeciesById(breedCreateDto.getSpeciesId())
                .orElseThrow(() -> new ResourceNotFoundException("Species not found for this id: " + breedCreateDto.getSpeciesId()));
        breed.setSpecies(species);

        try {
            breedRepository.save(breed);
            return breedMapper.breedToBreedDto(breedRepository.save(breed));
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

        try {
            breedRepository.save(breed);
            return breedMapper.breedToBreedDto(breedRepository.save(breed));
        } catch (DataIntegrityViolationException e) {
            throw new ResourceNotFoundException("Breed already exists with this name: " + breed.getName());
        }
    }

    @Override
    public void deleteBreed(Long id) {
        if (!breedRepository.existsById(id)) {
            throw new ResourceNotFoundException("Breed not found for this id: " + id);
        }

        breedRepository.deleteById(id);
    }
}
