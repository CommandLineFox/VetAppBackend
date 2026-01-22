package raf.aleksabuncic.service.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.VeterinarianCreateDto;
import raf.aleksabuncic.dto.VeterinarianDto;
import raf.aleksabuncic.dto.VeterinarianUpdateDto;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.mapper.VeterinarianMapper;
import raf.aleksabuncic.repository.VeterinarianRepository;
import raf.aleksabuncic.service.implementation.VeterinarianServiceImplementation;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeterinarianServiceTest {
    @Mock
    private VeterinarianRepository veterinarianRepository;

    @Mock
    private VeterinarianMapper veterinarianMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private VeterinarianServiceImplementation veterinarianService;

    @Test
    void findVeterinarianByIdSuccess() {
        Long id = 1L;
        Veterinarian veterinarian = new Veterinarian();
        VeterinarianDto veterinarianDto = new VeterinarianDto();

        when(veterinarianRepository.findById(id)).thenReturn(Optional.of(veterinarian));
        when(veterinarianMapper.veterinarianToVeterinarianDto(veterinarian)).thenReturn(veterinarianDto);

        VeterinarianDto result = veterinarianService.findVeterinarianById(id);

        assertNotNull(result);
        verify(veterinarianRepository, times(1)).findById(id);
    }

    @Test
    void findVeterinarianByIdNotFound() {
        Long id = 100L;
        when(veterinarianRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> veterinarianService.findVeterinarianById(id));

        verifyNoInteractions(veterinarianMapper);
        verify(veterinarianRepository, times(1)).findById(id);
    }

    @Test
    void createVeterinarianSuccess() {
        VeterinarianCreateDto veterinarianCreateDto = new VeterinarianCreateDto();

        Veterinarian veterinarian = new Veterinarian();
        VeterinarianDto veterinarianDto = new VeterinarianDto();

        when(veterinarianMapper.veterinarianCreateDtoToVeterinarian(veterinarianCreateDto)).thenReturn(veterinarian);
        when(veterinarianRepository.save(veterinarian)).thenReturn(veterinarian);
        when(veterinarianMapper.veterinarianToVeterinarianDto(veterinarian)).thenReturn(veterinarianDto);

        VeterinarianDto result = veterinarianService.createVeterinarian(veterinarianCreateDto);

        assertNotNull(result);
        verify(veterinarianMapper, times(1)).veterinarianCreateDtoToVeterinarian(veterinarianCreateDto);
        verify(veterinarianRepository, times(1)).save(veterinarian);
    }

    @Test
    void createVeterinarianDuplicate() {
        Integer licenseNumber = 1;
        VeterinarianCreateDto veterinarianCreateDto = new VeterinarianCreateDto();
        veterinarianCreateDto.setLicenseNumber(licenseNumber);

        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setLicenseNumber(licenseNumber);

        when(veterinarianMapper.veterinarianCreateDtoToVeterinarian(veterinarianCreateDto)).thenReturn(veterinarian);
        when(veterinarianRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DuplicateResourceException.class, () -> veterinarianService.createVeterinarian(veterinarianCreateDto));
        verify(veterinarianRepository, times(1)).save(any());
    }

    @Test
    void updateVeterinarianSuccess() {
        Long id = 1L;
        VeterinarianUpdateDto veterinarianUpdateDto = new VeterinarianUpdateDto();
        veterinarianUpdateDto.setFirstName("Test");
        veterinarianUpdateDto.setLastName("Testing");

        Veterinarian existingVeterinarian = new Veterinarian();
        existingVeterinarian.setFirstName("Old Test");
        existingVeterinarian.setLastName("Old Testing");

        VeterinarianDto updatedVeterinarianDto = new VeterinarianDto();
        updatedVeterinarianDto.setFirstName("Test");
        updatedVeterinarianDto.setLastName("Testing");

        when(veterinarianRepository.findById(id)).thenReturn(Optional.of(existingVeterinarian));
        when(veterinarianMapper.veterinarianToVeterinarianDto(existingVeterinarian)).thenReturn(updatedVeterinarianDto);

        VeterinarianDto result = veterinarianService.updateVeterinarian(id, veterinarianUpdateDto);

        assertNotNull(result);
        assertEquals("Test", result.getFirstName());
        assertEquals("Testing", result.getLastName());

        verify(veterinarianRepository, times(1)).findById(id);
        verify(veterinarianMapper, times(1)).veterinarianToVeterinarianDto(existingVeterinarian);
        verify(veterinarianMapper).veterinarianToVeterinarianDto(argThat(vet ->
                vet.getFirstName().equals("Test") && vet.getLastName().equals("Testing")
        ));
    }

    @Test
    void updateVeterinarianNotFound() {
        Long id = 100L;
        VeterinarianUpdateDto veterinarianUpdateDto = new VeterinarianUpdateDto();

        when(veterinarianRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> veterinarianService.updateVeterinarian(id, veterinarianUpdateDto));
        verify(veterinarianRepository, times(1)).findById(id);
    }

    @Test
    void deleteVeterinarianSuccess() {
        Long id = 1L;

        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setId(id);

        when(veterinarianRepository.findById(id)).thenReturn(Optional.of(veterinarian));

        doNothing().when(veterinarianRepository).delete(veterinarian);

        veterinarianService.deleteVeterinarian(id);

        verify(veterinarianRepository, times(1)).findById(id);
        verify(veterinarianRepository, times(1)).delete(veterinarian);
    }

    @Test
    void deleteVeterinarianNotFound() {
        Long id = 100L;

        when(veterinarianRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> veterinarianService.deleteVeterinarian(id));

        verify(veterinarianRepository, times(1)).findById(id);
    }
}
