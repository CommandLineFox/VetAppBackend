package raf.aleksabuncic.service;

import org.springframework.data.domain.Slice;
import raf.aleksabuncic.dto.OwnerDto;
import raf.aleksabuncic.dto.OwnerCreateDto;
import raf.aleksabuncic.dto.OwnerUpdateDto;
import raf.aleksabuncic.dto.PaginationDto;

import java.util.List;

public interface OwnerService {
    /**
     * Find owner by id
     *
     * @param id Owner id
     * @return OwnerDto
     */
    OwnerDto findOwnerById(Long id);

    /**
     * Find all owners with pagination
     *
     * @param paginationDto Pagination object
     * @return Slice of owners
     */
    Iterable<OwnerDto> findAllOwners(PaginationDto paginationDto);

    /**
     * Create new owner
     *
     * @param ownerCreateDto Owner create object
     * @return OwnerDto
     */
    OwnerDto createOwner(OwnerCreateDto ownerCreateDto);

    /**
     * Update owner
     *
     * @param id             Owner id
     * @param ownerUpdateDto Owner update object
     * @return OwnerDto
     */
    OwnerDto updateOwner(Long id, OwnerUpdateDto ownerUpdateDto);

    /**
     * Delete owner by id
     *
     * @param id Owner id
     */
    void deleteOwner(Long id);
}
