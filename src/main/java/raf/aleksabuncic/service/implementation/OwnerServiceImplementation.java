package raf.aleksabuncic.service.implementation;

import org.springframework.stereotype.Service;
import raf.aleksabuncic.dto.OwnerDto;
import raf.aleksabuncic.dto.OwnerRequestDto;
import raf.aleksabuncic.service.OwnerService;

@Service
public class OwnerServiceImplementation implements OwnerService {
    @Override
    public OwnerDto findOwnerById(Long id) {
        return null;
    }

    @Override
    public OwnerDto createOwner(OwnerRequestDto ownerRequestDto) {
        return null;
    }

    @Override
    public OwnerDto updateOwner(OwnerRequestDto ownerRequestDto) {
        return null;
    }

    @Override
    public void deleteOwner(Long id) {

    }
}
