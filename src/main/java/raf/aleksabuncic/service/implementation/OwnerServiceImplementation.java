package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OwnerServiceImplementation implements OwnerService {
    private final OwnerMapper ownerMapper;
    private final OwnerRepository ownerRepository;

    @Transactional(readOnly = true)
    @Override
    public OwnerDto findOwnerById(Long id) {
        log.info("Finding owner by id: {}", id);

        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found for this id: " + id));

        return ownerMapper.ownerToOwnerDto(owner);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OwnerDto> findAllOwners() {
        log.info("Finding all owners");

        return ownerRepository.findAll()
                .stream()
                .map(ownerMapper::ownerToOwnerDto)
                .toList();
    }

    @Override
    public OwnerDto createOwner(OwnerCreateDto ownerCreateDto) {
        log.info("Creating owner: {}", ownerCreateDto);

        boolean existingOwner = ownerRepository.existsByJmbgOrEmailOrPhoneNumber(ownerCreateDto.getJmbg(), ownerCreateDto.getEmail(), ownerCreateDto.getPhoneNumber());
        if (existingOwner) {
            throw new DuplicateResourceException("Owner already exists with this JMBG, email or phone number");
        }

        Owner owner = ownerMapper.ownerCreateDtoToOwner(ownerCreateDto);

        try {
            ownerRepository.save(owner);
            log.info("Owner created: {}", owner);
            return ownerMapper.ownerToOwnerDto(owner);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Owner already exists with this JMBG: " + ownerCreateDto.getJmbg());
        }
    }

    @Override
    public OwnerDto updateOwner(Long id, OwnerUpdateDto ownerUpdateDto) {
        log.info("Updating owner: {}", ownerUpdateDto);

        boolean existingOwner = ownerRepository.existsByJmbgOrEmailOrPhoneNumber(ownerUpdateDto.getJmbg(), ownerUpdateDto.getEmail(), ownerUpdateDto.getPhoneNumber());

        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found for this id: " + id));

        if (ownerUpdateDto.getFirstName() != null) {
            if (owner.getFirstName().equals(ownerUpdateDto.getFirstName())) {
                throw new DuplicateResourceException("Owner first name cannot be the same as the old one");
            }

            owner.setFirstName(ownerUpdateDto.getFirstName());
        }

        if (ownerUpdateDto.getLastName() != null) {
            if (owner.getLastName().equals(ownerUpdateDto.getLastName())) {
                throw new DuplicateResourceException("Owner last name cannot be the same as the old one");
            }

            owner.setLastName(ownerUpdateDto.getLastName());
        }

        if (ownerUpdateDto.getAddress() != null) {
            if (owner.getAddress().equals(ownerUpdateDto.getAddress())) {
                throw new DuplicateResourceException("Owner address cannot be the same as the old one");
            }

            owner.setAddress(ownerUpdateDto.getAddress());
        }

        if (ownerUpdateDto.getPhoneNumber() != null) {
            if (owner.getPhoneNumber().equals(ownerUpdateDto.getPhoneNumber())) {
                throw new DuplicateResourceException("Owner phone number cannot be the same as the old one");
            }

            if (existingOwner) {
                throw new DuplicateResourceException("Owner already exists with this phone number: " + ownerUpdateDto.getPhoneNumber());
            }

            owner.setPhoneNumber(ownerUpdateDto.getPhoneNumber());
        }

        if (ownerUpdateDto.getEmail() != null) {
            if (owner.getEmail().equals(ownerUpdateDto.getEmail())) {
                throw new DuplicateResourceException("Owner email cannot be the same as the old one");
            }

            if (existingOwner) {
                throw new DuplicateResourceException("Owner already exists with this email: " + ownerUpdateDto.getEmail());
            }

            owner.setEmail(ownerUpdateDto.getEmail());
        }

        if (ownerUpdateDto.getJmbg() != null) {
            if (owner.getJmbg().equals(ownerUpdateDto.getJmbg())) {
                throw new DuplicateResourceException("Owner JMBG cannot be the same as the old one");
            }

            if (existingOwner) {
                throw new DuplicateResourceException("Owner already exists with this JMBG: " + ownerUpdateDto.getJmbg());
            }

            owner.setJmbg(ownerUpdateDto.getJmbg());
        }

        try {
            ownerRepository.save(owner);
            log.info("Owner updated: {}", owner);
            return ownerMapper.ownerToOwnerDto(owner);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Owner already exists with this JMBG: " + ownerUpdateDto.getJmbg());
        }
    }

    @Override
    public void deleteOwner(Long id) {
        log.info("Deleting owner with id: {}", id);

        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found for this id: " + id));

        try {
            ownerRepository.delete(owner);
            log.info("Owner deleted: {}", owner);
        } catch (DataIntegrityViolationException e) {
            throw new UsedResourceException("Cannot delete owner with id: " + id + " because it is associated with other resources");
        }
    }
}
