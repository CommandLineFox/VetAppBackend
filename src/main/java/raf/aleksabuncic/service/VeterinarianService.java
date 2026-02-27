package raf.aleksabuncic.service;

import org.springframework.data.domain.Pageable;
import raf.aleksabuncic.dto.VeterinarianDto;
import raf.aleksabuncic.dto.VeterinarianCreateDto;
import raf.aleksabuncic.dto.VeterinarianSearchDto;
import raf.aleksabuncic.dto.VeterinarianUpdateDto;

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
    Iterable<VeterinarianDto> findAllVeterinarians(VeterinarianSearchDto veterinarianSearchDto, Pageable pageable);

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
