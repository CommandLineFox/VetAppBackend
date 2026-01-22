package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.BreedDto;
import raf.aleksabuncic.dto.BreedCreateDto;
import raf.aleksabuncic.dto.BreedUpdateDto;

import java.util.List;

public interface BreedService {
    /**
     * Find breed by id
     *
     * @param id Breed id
     * @return BreedDto
     */
    BreedDto findBreedById(Long id);

    /**
     * Find all breeds
     *
     * @return List of BreedDto
     */
    List<BreedDto> findAllBreeds();

    /**
     * Create new breed
     *
     * @param breedCreateDto Breed create object
     * @return BreedDto
     */
    BreedDto createBreed(BreedCreateDto breedCreateDto);

    /**
     * Update breed
     *
     * @param id             Breed id
     * @param breedUpdateDto Breed update object
     * @return BreedDto
     */
    BreedDto updateBreed(Long id, BreedUpdateDto breedUpdateDto);

    /**
     * Delete breed by id
     *
     * @param id Breed id
     */
    void deleteBreed(Long id);
}
