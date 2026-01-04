package raf.aleksabuncic.service.implementation;

import org.springframework.stereotype.Service;
import raf.aleksabuncic.dto.BreedDto;
import raf.aleksabuncic.dto.BreedRequestDto;
import raf.aleksabuncic.service.BreedService;

@Service
public class BreedServiceImplementation implements BreedService {
    @Override
    public BreedDto findBreedById(Long id) {
        return null;
    }

    @Override
    public BreedDto createBreed(BreedRequestDto breedRequestDto) {
        return null;
    }

    @Override
    public BreedDto updateBreed(BreedRequestDto breedRequestDto) {
        return null;
    }

    @Override
    public void deleteBreed(Long id) {

    }
}
