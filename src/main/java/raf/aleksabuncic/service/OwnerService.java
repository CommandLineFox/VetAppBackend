package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.OwnerDto;
import raf.aleksabuncic.dto.OwnerCreateDto;
import raf.aleksabuncic.dto.OwnerUpdateDto;

public interface OwnerService {
    OwnerDto findOwnerById(Long id);

    OwnerDto createOwner(OwnerCreateDto ownerCreateDto);

    OwnerDto updateOwner(Long id, OwnerUpdateDto ownerUpdateDto);

    void deleteOwner(Long id);
}
