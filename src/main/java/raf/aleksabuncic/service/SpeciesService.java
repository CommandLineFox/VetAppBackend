package raf.aleksabuncic.service;

import org.springframework.data.domain.Slice;
import raf.aleksabuncic.dto.PaginationDto;
import raf.aleksabuncic.dto.SpeciesDto;
import raf.aleksabuncic.dto.SpeciesCreateDto;
import raf.aleksabuncic.dto.SpeciesUpdateDto;

import java.util.List;

public interface SpeciesService {
    /**
     * Find species by id
     *
     * @param id Species id
     * @return SpeciesDto
     */
    SpeciesDto findSpeciesById(Long id);

    /**
     * Find all species with pagination
     *
     * @param paginationDto Pagination object
     * @return Slice of SpeciesDto
     */
    Iterable<SpeciesDto> findAllSpecies(PaginationDto paginationDto);

    /**
     * Create new species
     *
     * @param speciesCreateDto Species create object
     * @return SpeciesDto
     */
    SpeciesDto createSpecies(SpeciesCreateDto speciesCreateDto);

    /**
     * Update species
     *
     * @param id               Species id
     * @param speciesUpdateDto Species update object
     * @return SpeciesDto
     */
    SpeciesDto updateSpecies(Long id, SpeciesUpdateDto speciesUpdateDto);

    /**
     * Delete species by id
     *
     * @param id Species id
     */
    void deleteSpecies(Long id);
}
