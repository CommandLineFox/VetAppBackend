package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.BreedDto;
import raf.aleksabuncic.dto.BreedCreateDto;
import raf.aleksabuncic.dto.BreedUpdateDto;

import java.util.List;

public interface BreedService {
    BreedDto findBreedById(Long id);

    List<BreedDto> findAllBreeds();

    BreedDto createBreed(BreedCreateDto breedCreateDto);

    BreedDto updateBreed(Long id, BreedUpdateDto breedUpdateDto);

    void deleteBreed(Long id);
}
