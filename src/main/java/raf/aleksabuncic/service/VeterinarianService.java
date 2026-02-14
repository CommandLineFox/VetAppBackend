package raf.aleksabuncic.service;

import org.springframework.data.domain.Slice;
import raf.aleksabuncic.dto.PaginationDto;
import raf.aleksabuncic.dto.VeterinarianDto;
import raf.aleksabuncic.dto.VeterinarianCreateDto;
import raf.aleksabuncic.dto.VeterinarianUpdateDto;

import java.util.List;

public interface VeterinarianService {
    /**
     * Find veterinarian by id
     *
     * @param id Veterinarian id
     * @return VeterinarianDto
     */
    VeterinarianDto findVeterinarianById(Long id);

    /**
     * Find all veterinarians with pagination
     *
     * @param paginationDto Pagination object
     * @return Slice of VeterinarianDto
     */
    Iterable<VeterinarianDto> findAllVeterinarians(PaginationDto paginationDto);

    /**
     * Create new veterinarian
     *
     * @param veterinarianCreateDto Veterinarian create object
     * @return VeterinarianDto
     */
    VeterinarianDto createVeterinarian(VeterinarianCreateDto veterinarianCreateDto);

    /**
     * Update veterinarian
     *
     * @param id                    Veterinarian id
     * @param veterinarianUpdateDto Veterinarian update object
     * @return VeterinarianDto
     */
    VeterinarianDto updateVeterinarian(Long id, VeterinarianUpdateDto veterinarianUpdateDto);

    /**
     * Delete veterinarian by id
     *
     * @param id Veterinarian id
     */
    void deleteVeterinarian(Long id);
}
