package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.BreedDto;
import raf.aleksabuncic.dto.BreedCreateDto;
import raf.aleksabuncic.dto.BreedUpdateDto;

public interface BreedService {
    BreedDto findBreedById(Long id);

    BreedDto createBreed(BreedCreateDto breedCreateDto);

    BreedDto updateBreed(Long id, BreedUpdateDto breedUpdateDto);

    void deleteBreed(Long id);
}
