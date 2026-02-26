package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.PaginationDto;
import raf.aleksabuncic.dto.VeterinarianDto;
import raf.aleksabuncic.dto.VeterinarianCreateDto;
import raf.aleksabuncic.dto.VeterinarianUpdateDto;
import raf.aleksabuncic.exception.BadRequestException;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.exception.UsedResourceException;
import raf.aleksabuncic.mapper.VeterinarianMapper;
import raf.aleksabuncic.repository.VeterinarianRepository;
import raf.aleksabuncic.service.VeterinarianService;
import raf.aleksabuncic.utils.SortUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VeterinarianServiceImplementation implements VeterinarianService {
    private final VeterinarianMapper veterinarianMapper;
    private final VeterinarianRepository veterinarianRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public VeterinarianDto findVeterinarianById(Long id) {
        log.info("Finding veterinarian by id: {}", id);

        Veterinarian veterinarian = veterinarianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + id));

        return veterinarianMapper.veterinarianToVeterinarianDto(veterinarian);
    }

    private List<VeterinarianDto> findAllVeterinarians() {
        return veterinarianRepository.findAll()
                .stream()
                .map(veterinarianMapper::veterinarianToVeterinarianDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<VeterinarianDto> findAllVeterinarians(PaginationDto paginationDto) {
        if (paginationDto == null) {
            return findAllVeterinarians();
        }

        Integer page = paginationDto.getPage();
        Integer size = paginationDto.getSize();
        String sortBy = paginationDto.getSortBy();
        String direction = paginationDto.getDirection();

        if (page == null && size == null) {
            return findAllVeterinarians();
        }

        if (page == null || size == null) {
            throw new BadRequestException("Page and size must be provided together");
        }

        if (direction == null) {
            direction = Sort.Direction.ASC.name();
        }

        if (sortBy == null) {
            sortBy = "id";
        }

        if (SortUtils.isInvalidField(Veterinarian.class, sortBy)) {
            throw new BadRequestException("Invalid sort field");
        }

        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortBy);

        log.info("Finding all appointments with pagination: Page {} of size {}", page, size);

        return veterinarianRepository.findAll(pageable)
                .map(veterinarianMapper::veterinarianToVeterinarianDto);
    }

    @Override
    public VeterinarianDto createVeterinarian(VeterinarianCreateDto veterinarianCreateDto) {
        log.info("Creating veterinarian: {}", veterinarianCreateDto);

        boolean existingVeterinarian = veterinarianRepository.existsByLicenseNumber(veterinarianCreateDto.getLicenseNumber());
        if (existingVeterinarian) {
            throw new DuplicateResourceException("Veterinarian already exists for this license number: " + veterinarianCreateDto.getLicenseNumber());
        }

        Veterinarian veterinarian = veterinarianMapper.veterinarianCreateDtoToVeterinarian(veterinarianCreateDto);

        veterinarian.setPassword(passwordEncoder.encode(veterinarianCreateDto.getPassword()));

        try {
            veterinarianRepository.save(veterinarian);
            log.info("Veterinarian created: {}", veterinarianCreateDto);
            return veterinarianMapper.veterinarianToVeterinarianDto(veterinarian);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Veterinarian already exists for this license number: " + veterinarianCreateDto.getLicenseNumber());
        }
    }

    @Override
    public VeterinarianDto updateVeterinarian(Long id, VeterinarianUpdateDto veterinarianUpdateDto) {
        log.info("Updating veterinarian: {}", veterinarianUpdateDto);

        boolean existingVeterinarian = veterinarianRepository.existsByLicenseNumber(veterinarianUpdateDto.getLicenseNumber());

        Veterinarian veterinarian = veterinarianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + id));

        if (veterinarianUpdateDto.getFirstName() != null && veterinarian.getFirstName().equals(veterinarianUpdateDto.getFirstName())) {
            throw new DuplicateResourceException("Veterinarian first name cannot be the same as the old one");
        }

        if (veterinarianUpdateDto.getLastName() != null && veterinarian.getLastName().equals(veterinarianUpdateDto.getLastName())) {
            throw new DuplicateResourceException("Veterinarian last name cannot be the same as the old one");
        }

        if (veterinarianUpdateDto.getLicenseNumber() != null) {
            if (existingVeterinarian) {
                throw new DuplicateResourceException("Veterinarian already exists with this license number");
            }

            if (veterinarian.getLicenseNumber().equals(veterinarianUpdateDto.getLicenseNumber())) {
                throw new DuplicateResourceException("Veterinarian license number cannot be the same as the old one");
            }
        }

        if (veterinarianUpdateDto.getPermissions() != null && veterinarian.getPermissions().equals(veterinarianUpdateDto.getPermissions())) {
            throw new DuplicateResourceException("Veterinarian permissions cannot be the same as the old one");
        }

        if (veterinarianUpdateDto.getPassword() != null) {
            veterinarian.setPassword(passwordEncoder.encode(veterinarianUpdateDto.getPassword()));
        }

        veterinarianMapper.veterinarianUpdateDtoToVeterinarian(veterinarianUpdateDto, veterinarian);

        try {
            veterinarianRepository.save(veterinarian);
            log.info("Veterinarian updated: {}", veterinarian);
            return veterinarianMapper.veterinarianToVeterinarianDto(veterinarian);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("There was a problem updating the veterinarian");
        }
    }

    @Override
    public void deleteVeterinarian(Long id) {
        log.info("Deleting veterinarian with id: {}", id);

        Veterinarian veterinarian = veterinarianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + id));

        try {
            veterinarianRepository.delete(veterinarian);
            log.info("Veterinarian deleted: {}", veterinarian);
        } catch (DataIntegrityViolationException e) {
            throw new UsedResourceException("Cannot delete veterinarian with id: " + id + " because it is associated with other resources");
        }
    }
}
