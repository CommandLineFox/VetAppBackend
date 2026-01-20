package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.aleksabuncic.domain.Owner;
import raf.aleksabuncic.dto.OwnerDto;
import raf.aleksabuncic.dto.OwnerCreateDto;
import raf.aleksabuncic.dto.OwnerUpdateDto;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.exception.UsedResourceException;
import raf.aleksabuncic.mapper.OwnerMapper;
import raf.aleksabuncic.repository.OwnerRepository;
import raf.aleksabuncic.service.OwnerService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerServiceImplementation implements OwnerService {
    private final OwnerMapper ownerMapper;
    private final OwnerRepository ownerRepository;

    @Transactional(readOnly = true)
    @Override
    public OwnerDto findOwnerById(Long id) {
        Owner owner = ownerRepository.getOwnerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found for this id: " + id));

        return ownerMapper.ownerToOwnerDto(owner);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OwnerDto> findAllOwners() {
        return ownerRepository.findAll()
                .stream()
                .map(ownerMapper::ownerToOwnerDto)
                .toList();
    }

    @Override
    public OwnerDto createOwner(OwnerCreateDto ownerCreateDto) {
        Owner owner = ownerMapper.ownerCreateDtoToOwner(ownerCreateDto);

        try {
            ownerRepository.save(owner);
            return ownerMapper.ownerToOwnerDto(owner);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Owner already exists with this JMBG: " + ownerCreateDto.getJmbg());
        }
    }

    @Override
    public OwnerDto updateOwner(Long id, OwnerUpdateDto ownerUpdateDto) {
        Owner owner = ownerRepository.getOwnerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found for this id: " + id));

        if (ownerUpdateDto.getFirstName() != null) {
            owner.setFirstName(ownerUpdateDto.getFirstName());
        }

        if (ownerUpdateDto.getLastName() != null) {
            owner.setLastName(ownerUpdateDto.getLastName());
        }

        if (ownerUpdateDto.getAddress() != null) {
            owner.setAddress(ownerUpdateDto.getAddress());
        }

        if (ownerUpdateDto.getPhoneNumber() != null) {
            owner.setPhoneNumber(ownerUpdateDto.getPhoneNumber());
        }

        if (ownerUpdateDto.getEmail() != null) {
            owner.setEmail(ownerUpdateDto.getEmail());
        }

        if (ownerUpdateDto.getJmbg() != null) {
            owner.setJmbg(ownerUpdateDto.getJmbg());
        }

        return ownerMapper.ownerToOwnerDto(owner);
    }

    @Override
    public void deleteOwner(Long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found for this id: " + id));

        try {
            ownerRepository.delete(owner);
        } catch (DataIntegrityViolationException e) {
            throw new UsedResourceException("Cannot delete owner with id: " + id + " because it is associated with other resources");
        }
    }
}
