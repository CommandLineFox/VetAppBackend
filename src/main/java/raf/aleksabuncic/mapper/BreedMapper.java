package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Breed;
import raf.aleksabuncic.dto.BreedRequestDto;
import raf.aleksabuncic.dto.BreedDto;

@Component
public class BreedMapper {
    public BreedDto breedToBreedDto(Breed breed) {
        BreedDto breedDto = new BreedDto();

        breedDto.setId(breed.getId());
        breedDto.setName(breed.getName());
        breedDto.setSpeciesId(breed.getSpecies().getId());

        return breedDto;
    }

    public Breed breedCRequestDtoToBreed(BreedRequestDto breedRequestDto) {
        Breed breed = new Breed();

        breed.setName(breedRequestDto.getName());

        return breed;
    }
}
