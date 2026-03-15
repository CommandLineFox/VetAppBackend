package raf.aleksabuncic.service;

import org.springframework.data.domain.Pageable;
import raf.aleksabuncic.dto.OwnerDto;
import raf.aleksabuncic.dto.OwnerCreateDto;
import raf.aleksabuncic.dto.OwnerSearchDto;
import raf.aleksabuncic.dto.OwnerUpdateDto;

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
     * Find all owners
     *
     * @param ownerSearchDto Owner search object
     * @return List of owners
     */
    List<OwnerDto> findAllOwners(OwnerSearchDto ownerSearchDto);

    /**
     * Find all owners with pagination
     *
     * @param ownerSearchDto Owner search object
     * @param pageable       Pagination object
     * @return Slice of owners
     */
    Iterable<OwnerDto> findAllOwners(OwnerSearchDto ownerSearchDto, Pageable pageable);

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
