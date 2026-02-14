package raf.aleksabuncic.mapper;

import org.mapstruct.*;
import raf.aleksabuncic.domain.Species;
import raf.aleksabuncic.dto.SpeciesCreateDto;
import raf.aleksabuncic.dto.SpeciesDto;
import raf.aleksabuncic.dto.SpeciesUpdateDto;

@Mapper(componentModel = "spring")
public interface SpeciesMapper {
    SpeciesDto speciesToSpeciesDto(Species species);

    @Mapping(target = "id", ignore = true)
    Species speciesCreateDtoToSpecies(SpeciesCreateDto speciesCreateDto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void speciesUpdateDtoToSpecies(SpeciesUpdateDto speciesUpdateDto, @MappingTarget Species species);
}
