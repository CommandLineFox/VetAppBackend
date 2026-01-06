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
import raf.aleksabuncic.mapper.OwnerMapper;
import raf.aleksabuncic.repository.OwnerRepository;
import raf.aleksabuncic.service.OwnerService;

@Service
@RequiredArgsConstructor
public class OwnerServiceImplementation implements OwnerService {
    private final OwnerMapper ownerMapper;
    private final OwnerRepository ownerRepository;

    @Override
    public OwnerDto findOwnerById(Long id) {
        Owner owner = ownerRepository.getOwnerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found for this id: " + id));

        return ownerMapper.ownerToOwnerDto(owner);
    }

    @Transactional
    @Override
    public OwnerDto createOwner(OwnerCreateDto ownerCreateDto) {
        Owner owner = ownerMapper.ownerCreateDtoToOwner(ownerCreateDto);

        try {
            ownerRepository.save(owner);
            return ownerMapper.ownerToOwnerDto(ownerRepository.save(owner));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Owner already exists with this JMBG: " + ownerCreateDto.getJmbg());
        }
    }

    @Transactional
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

        try {
            ownerRepository.save(owner);
            return ownerMapper.ownerToOwnerDto(owner);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Owner already exists with this JMBG: " + ownerUpdateDto.getJmbg());
        }
    }

    @Transactional
    @Override
    public void deleteOwner(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new RuntimeException("Owner not found for this id: " + id);
        }

        ownerRepository.deleteById(id);
    }
}
