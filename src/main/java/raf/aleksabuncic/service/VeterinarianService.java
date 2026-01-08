package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.VeterinarianDto;
import raf.aleksabuncic.dto.VeterinarianCreateDto;
import raf.aleksabuncic.dto.VeterinarianUpdateDto;

import java.util.List;

public interface VeterinarianService {
    VeterinarianDto findVeterinarianById(Long id);

    List<VeterinarianDto> findAllVeterinarians();

    VeterinarianDto createVeterinarian(VeterinarianCreateDto veterinarianCreateDto);

    VeterinarianDto updateVeterinarian(Long id, VeterinarianUpdateDto veterinarianUpdateDto);

    void deleteVeterinarian(Long id);
}
