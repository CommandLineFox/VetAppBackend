package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Breed;
import raf.aleksabuncic.dto.BreedCreateDto;
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

    public Breed breedCreateDtoToBreed(BreedCreateDto breedCreateDto) {
        Breed breed = new Breed();

        breed.setName(breedCreateDto.getName());

        return breed;
    }
}
