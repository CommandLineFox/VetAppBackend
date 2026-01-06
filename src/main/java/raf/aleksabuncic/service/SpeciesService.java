package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.SpeciesDto;
import raf.aleksabuncic.dto.SpeciesCreateDto;
import raf.aleksabuncic.dto.SpeciesUpdateDto;

public interface SpeciesService {
    SpeciesDto findSpeciesById(Long id);

    SpeciesDto createSpecies(SpeciesCreateDto speciesCreateDto);

    SpeciesDto updateSpecies(Long id, SpeciesUpdateDto speciesUpdateDto);

    void deleteSpecies(Long id);
}
