package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.VeterinarianDto;
import raf.aleksabuncic.dto.VeterinarianCreateDto;
import raf.aleksabuncic.dto.VeterinarianUpdateDto;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.mapper.VeterinarianMapper;
import raf.aleksabuncic.repository.VeterinarianRepository;
import raf.aleksabuncic.service.VeterinarianService;

@Service
@RequiredArgsConstructor
public class VeterinarianServiceImplementation implements VeterinarianService {
    private final VeterinarianMapper veterinarianMapper;
    private final VeterinarianRepository veterinarianRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public VeterinarianDto findVeterinarianById(Long id) {
        Veterinarian veterinarian = veterinarianRepository.getVeterinarianById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + id));

        return veterinarianMapper.veterinarianToVeterinarianDto(veterinarian);
    }

    @Override
    public VeterinarianDto createVeterinarian(VeterinarianCreateDto veterinarianCreateDto) {
        Veterinarian veterinarian = veterinarianMapper.veterinarianCreateDtoToVeterinarian(veterinarianCreateDto);

        veterinarian.setPassword(passwordEncoder.encode(veterinarianCreateDto.getPassword()));

        try {
            veterinarianRepository.save(veterinarian);
            return veterinarianMapper.veterinarianToVeterinarianDto(veterinarian);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Veterinarian already exists for this license number: " + veterinarianCreateDto.getLicenseNumber());
        }
    }

    @Override
    public VeterinarianDto updateVeterinarian(Long id, VeterinarianUpdateDto veterinarianUpdateDto) {
        Veterinarian veterinarian = veterinarianRepository.getVeterinarianById(id)
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

        try {
            veterinarianRepository.save(veterinarian);
            return veterinarianMapper.veterinarianToVeterinarianDto(veterinarian);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("No changes made to veterinarian: " + veterinarianUpdateDto.getLicenseNumber());
        }
    }

    @Override
    public void deleteVeterinarian(Long id) {
        if (!veterinarianRepository.existsById(id)) {
            throw new ResourceNotFoundException("Veterinarian not found for this id: " + id);
        }

        veterinarianRepository.deleteById(id);
    }
}
