package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.VeterinarianDto;
import raf.aleksabuncic.dto.VeterinarianCreateDto;
import raf.aleksabuncic.dto.VeterinarianUpdateDto;

public interface VeterinarianService {
    VeterinarianDto findVeterinarianById(Long id);

    VeterinarianDto createVeterinarian(VeterinarianCreateDto veterinarianCreateDto);

    VeterinarianDto updateVeterinarian(VeterinarianUpdateDto veterinarianUpdateDto);

    void deleteVeterinarian(Long id);
}
