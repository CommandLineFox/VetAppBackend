package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.VeterinarianDto;
import raf.aleksabuncic.dto.VeterinarianCreateDto;
import raf.aleksabuncic.dto.VeterinarianUpdateDto;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.exception.UsedResourceException;
import raf.aleksabuncic.mapper.VeterinarianMapper;
import raf.aleksabuncic.repository.VeterinarianRepository;
import raf.aleksabuncic.service.VeterinarianService;

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

    @Transactional(readOnly = true)
    @Override
    public List<VeterinarianDto> findAllVeterinarians() {
        log.info("Finding all veterinarians");

        return veterinarianRepository.findAll()
                .stream()
                .map(veterinarianMapper::veterinarianToVeterinarianDto)
                .toList();
    }

    @Override
    public VeterinarianDto createVeterinarian(VeterinarianCreateDto veterinarianCreateDto) {
        log.info("Creating veterinarian: {}", veterinarianCreateDto);

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

        Veterinarian veterinarian = veterinarianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + id));


        if (veterinarianUpdateDto.getFirstName() != null) {
            veterinarian.setFirstName(veterinarianUpdateDto.getFirstName());
        }

        if (veterinarianUpdateDto.getLastName() != null) {
            veterinarian.setLastName(veterinarianUpdateDto.getLastName());
        }

        if (veterinarianUpdateDto.getLicenseNumber() != null) {
            veterinarian.setLicenseNumber(veterinarianUpdateDto.getLicenseNumber());
        }

        if (veterinarianUpdateDto.getPassword() != null) {
            veterinarian.setPassword(passwordEncoder.encode(veterinarianUpdateDto.getPassword()));
        }

        if (veterinarianUpdateDto.getPermissions() != null) {
            veterinarian.setPermissions(veterinarianUpdateDto.getPermissions());
        }

        log.info("Veterinarian updated: {}", veterinarianUpdateDto);
        return veterinarianMapper.veterinarianToVeterinarianDto(veterinarian);
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
