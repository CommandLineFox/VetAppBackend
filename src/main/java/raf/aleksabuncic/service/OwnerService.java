package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.OwnerDto;
import raf.aleksabuncic.dto.OwnerCreateDto;
import raf.aleksabuncic.dto.OwnerUpdateDto;

import java.util.List;

public interface OwnerService {
    OwnerDto findOwnerById(Long id);

    List<OwnerDto> findAllOwners();

    OwnerDto createOwner(OwnerCreateDto ownerCreateDto);

    OwnerDto updateOwner(Long id, OwnerUpdateDto ownerUpdateDto);

    void deleteOwner(Long id);
}
