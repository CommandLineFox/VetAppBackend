package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Species;
import raf.aleksabuncic.dto.SpeciesRequestDto;
import raf.aleksabuncic.dto.SpeciesDto;

@Component
public class SpeciesMapper {
    public SpeciesDto speciesToSpeciesDto(Species species) {
        SpeciesDto speciesDto = new SpeciesDto();

        speciesDto.setId(species.getId());
        speciesDto.setName(species.getName());

        return speciesDto;
    }

    public Species speciesRequestDtoToSpecies(SpeciesRequestDto speciesRequestDto) {
        Species species = new Species();

        species.setName(speciesRequestDto.getName());

        return species;
    }
}
