package raf.aleksabuncic.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import raf.aleksabuncic.dto.*;
import raf.aleksabuncic.repository.specification.BreedSpecifications;

import java.util.List;

public interface BreedService {
    /**
     * Find breed by id
     *
     * @param id Breed id
     * @return BreedDto
     */
    BreedDto findBreedById(Long id);

    /**
     * Find all breeds
     *
     * @param breedSearchDto Breed search object
     * @return List of BreedDto
     */
    List<BreedDto> findAllBreeds(BreedSearchDto breedSearchDto);

    /**
     * Find all breeds with pagination
     *
     * @param breedSearchDto Breed search object
     * @param pageable       Pagination object
     * @return Slice of BreedDto
     */
    Iterable<BreedDto> findAllBreeds(BreedSearchDto breedSearchDto, Pageable pageable);

    /**
     * Create new breed
     *
     * @param breedCreateDto Breed create object
     * @return BreedDto
     */
    BreedDto createBreed(BreedCreateDto breedCreateDto);

    /**
     * Update breed
     *
     * @param id             Breed id
     * @param breedUpdateDto Breed update object
     * @return BreedDto
     */
    BreedDto updateBreed(Long id, BreedUpdateDto breedUpdateDto);

    /**
     * Delete breed by id
     *
     * @param id Breed id
     */
    void deleteBreed(Long id);
}
