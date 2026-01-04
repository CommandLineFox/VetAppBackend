package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.VeterinarianDto;
import raf.aleksabuncic.dto.VeterinarianRequestDto;

public interface VeterinarianService {
    VeterinarianDto findVeterinarianById(Long id);

    VeterinarianDto createVeterinarian(VeterinarianRequestDto veterinarianRequestDto);

    VeterinarianDto updateVeterinarian(VeterinarianRequestDto veterinarianRequestDto);

    void deleteVeterinarian(Long id);
}
