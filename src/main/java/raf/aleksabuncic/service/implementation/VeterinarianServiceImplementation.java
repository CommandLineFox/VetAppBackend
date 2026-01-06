package raf.aleksabuncic.service.implementation;

import org.springframework.stereotype.Service;
import raf.aleksabuncic.dto.VeterinarianDto;
import raf.aleksabuncic.dto.VeterinarianCreateDto;
import raf.aleksabuncic.dto.VeterinarianUpdateDto;
import raf.aleksabuncic.service.VeterinarianService;

@Service
public class VeterinarianServiceImplementation implements VeterinarianService {
    @Override
    public VeterinarianDto findVeterinarianById(Long id) {
        return null;
    }

    @Override
    public VeterinarianDto createVeterinarian(VeterinarianCreateDto veterinarianCreateDto) {
        return null;
    }

    @Override
    public VeterinarianDto updateVeterinarian(VeterinarianUpdateDto veterinarianUpdateDto) {
        return null;
    }

    @Override
    public void deleteVeterinarian(Long id) {

    }
}
