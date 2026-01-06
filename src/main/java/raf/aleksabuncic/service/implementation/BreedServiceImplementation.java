package raf.aleksabuncic.service.implementation;

import org.springframework.stereotype.Service;
import raf.aleksabuncic.dto.BreedDto;
import raf.aleksabuncic.dto.BreedCreateDto;
import raf.aleksabuncic.dto.BreedUpdateDto;
import raf.aleksabuncic.service.BreedService;

@Service
public class BreedServiceImplementation implements BreedService {
    @Override
    public BreedDto findBreedById(Long id) {
        return null;
    }

    @Override
    public BreedDto createBreed(BreedCreateDto breedCreateDto) {
        return null;
    }

    @Override
    public BreedDto updateBreed(BreedUpdateDto breedUpdateDto) {
        return null;
    }

    @Override
    public void deleteBreed(Long id) {

    }
}
