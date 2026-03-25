package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.aleksabuncic.domain.Owner;
import raf.aleksabuncic.dto.OwnerDto;
import raf.aleksabuncic.dto.OwnerCreateDto;
import raf.aleksabuncic.dto.OwnerSearchDto;
import raf.aleksabuncic.dto.OwnerUpdateDto;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.exception.UsedResourceException;
import raf.aleksabuncic.mapper.OwnerMapper;
import raf.aleksabuncic.repository.OwnerRepository;
import raf.aleksabuncic.repository.specification.OwnerSpecifications;
import raf.aleksabuncic.service.OwnerService;

import java.util.List;

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
    public List<OwnerDto> findAllOwners(OwnerSearchDto ownerSearchDto) {
        log.info("Finding all owners with filters: {}", ownerSearchDto);

        Specification<Owner> specification = OwnerSpecifications.build(ownerSearchDto);

        return ownerRepository.findAll(specification)
                .stream()
                .map(ownerMapper::ownerToOwnerDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<OwnerDto> findAllOwners(OwnerSearchDto ownerSearchDto, Pageable pageable) {
        log.info("Finding all owners with filters: {} and pagination: {}", ownerSearchDto, pageable);

        Specification<Owner> specification = OwnerSpecifications.build(ownerSearchDto);

        return ownerRepository.findAll(specification, pageable)
                .map(ownerMapper::ownerToOwnerDto);
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

        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found for this id: " + id));

        if (ownerUpdateDto.getPhoneNumber() != null) {
            boolean existingOwner = ownerRepository.existsByPhoneNumberAndIdNot(ownerUpdateDto.getPhoneNumber(), id);

            if (existingOwner) {
                throw new DuplicateResourceException("Owner already exists with this phone number: " + ownerUpdateDto.getPhoneNumber());
            }
        }

        if (ownerUpdateDto.getEmail() != null) {
            boolean existingOwner = ownerRepository.existsByEmailAndIdNot(ownerUpdateDto.getEmail(), id);

            if (existingOwner) {
                throw new DuplicateResourceException("Owner already exists with this email: " + ownerUpdateDto.getEmail());
            }
        }

        if (ownerUpdateDto.getJmbg() != null) {
            boolean existingOwner = ownerRepository.existsByJmbgAndIdNot(ownerUpdateDto.getJmbg(), id);

            if (existingOwner) {
                throw new DuplicateResourceException("Owner already exists with this JMBG: " + ownerUpdateDto.getJmbg());
            }
        }

        ownerMapper.ownerUpdateDtoToOwner(ownerUpdateDto, owner);

        try {
            ownerRepository.save(owner);
            log.info("Owner updated: {}", owner);
            return ownerMapper.ownerToOwnerDto(owner);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("There was a problem updating the owner");
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
