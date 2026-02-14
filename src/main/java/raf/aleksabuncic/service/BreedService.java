package raf.aleksabuncic.service;

import org.springframework.data.domain.Slice;
import raf.aleksabuncic.dto.BreedDto;
import raf.aleksabuncic.dto.BreedCreateDto;
import raf.aleksabuncic.dto.BreedUpdateDto;
import raf.aleksabuncic.dto.PaginationDto;

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
     * Find all breeds with pagination
     *
     * @param paginationDto Pagination object
     * @return Slice of BreedDto
     */
    Iterable<BreedDto> findAllBreeds(PaginationDto paginationDto);

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
