package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Species;
import raf.aleksabuncic.dto.SpeciesCreateDto;
import raf.aleksabuncic.dto.SpeciesDto;
import raf.aleksabuncic.dto.SpeciesUpdateDto;

@Component
public class SpeciesMapper {
    public SpeciesDto speciesToSpeciesDto(Species species) {
        SpeciesDto speciesDto = new SpeciesDto();

        speciesDto.setId(species.getId());
        speciesDto.setName(species.getName());

        return speciesDto;
    }

    public Species speciesCreateDtoToSpecies(SpeciesCreateDto speciesCreateDto) {
        Species species = new Species();

        species.setName(speciesCreateDto.getName());

        return species;
    }

    public Species speciesUpdateDtoToSpecies(SpeciesUpdateDto speciesUpdateDto) {
        Species species = new Species();

        if (speciesUpdateDto.getName() != null) {
            species.setName(speciesUpdateDto.getName());
        }

        return species;
    }
}
