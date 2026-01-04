package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.dto.SpeciesDto;

@Component
public class SpeciesMapper {
    public SpeciesDto speciesToSpeciesDto(raf.aleksabuncic.domain.Species species) {
        SpeciesDto speciesDto = new SpeciesDto();

        speciesDto.setId(species.getId());
        speciesDto.setName(species.getName());

        return speciesDto;
    }
}
