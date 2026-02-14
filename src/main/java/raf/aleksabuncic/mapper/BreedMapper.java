package raf.aleksabuncic.mapper;

import org.mapstruct.*;
import raf.aleksabuncic.domain.Breed;
import raf.aleksabuncic.dto.BreedCreateDto;
import raf.aleksabuncic.dto.BreedDto;
import raf.aleksabuncic.dto.BreedUpdateDto;

@Mapper(componentModel = "spring")
public interface BreedMapper {
    @Mapping(source = "species.id", target = "speciesId")
    BreedDto breedToBreedDto(Breed breed);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "speciesId", target = "species.id")
    Breed breedCreateDtoToBreed(BreedCreateDto breedCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "species", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void breedUpdateDtoToBreed(BreedUpdateDto breedUpdateDto, @MappingTarget Breed breed);
}
