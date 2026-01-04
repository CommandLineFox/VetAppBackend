package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.SpeciesDto;
import raf.aleksabuncic.dto.SpeciesRequestDto;

public interface SpeciesService {
    SpeciesDto findSpeciesById(Long id);

    SpeciesDto createSpecies(SpeciesRequestDto speciesRequestDto);

    SpeciesDto updateSpecies(Long id, SpeciesRequestDto speciesRequestDto);

    void deleteSpecies(Long id);
}
