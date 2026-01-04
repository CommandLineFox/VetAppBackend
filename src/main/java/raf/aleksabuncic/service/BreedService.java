package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.BreedDto;
import raf.aleksabuncic.dto.BreedRequestDto;

public interface BreedService {
    BreedDto findBreedById(Long id);

    BreedDto createBreed(BreedRequestDto breedRequestDto);

    BreedDto updateBreed(BreedRequestDto breedRequestDto);

    void deleteBreed(Long id);
}
