package raf.aleksabuncic.service;

import org.springframework.data.domain.Pageable;
import raf.aleksabuncic.dto.*;

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
     * Find all species
     *
     * @param speciesSearchDto Species search object
     * @return List of SpeciesDto
     */
    List<SpeciesDto> findAllSpecies(SpeciesSearchDto speciesSearchDto);

    /**
     * Find all species with pagination
     *
     * @param speciesSearchDto Species search object
     * @param pageable         Pagination object
     * @return Slice of SpeciesDto
     */
    Iterable<SpeciesDto> findAllSpecies(SpeciesSearchDto speciesSearchDto, Pageable pageable);

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
