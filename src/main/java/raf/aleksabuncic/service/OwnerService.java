package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.OwnerDto;
import raf.aleksabuncic.dto.OwnerRequestDto;

public interface OwnerService {
    OwnerDto findOwnerById(Long id);

    OwnerDto createOwner(OwnerRequestDto ownerRequestDto);

    OwnerDto updateOwner(OwnerRequestDto ownerRequestDto);

    void deleteOwner(Long id);
}
