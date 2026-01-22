package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.OwnerDto;
import raf.aleksabuncic.dto.OwnerCreateDto;
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
     * @return List of owners
     */
    List<OwnerDto> findAllOwners();

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
