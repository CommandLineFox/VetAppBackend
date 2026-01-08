package raf.aleksabuncic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.aleksabuncic.dto.VeterinarianCreateDto;
import raf.aleksabuncic.dto.VeterinarianDto;
import raf.aleksabuncic.dto.VeterinarianUpdateDto;
import raf.aleksabuncic.service.VeterinarianService;

@RestController
@RequestMapping("/veterinarian")
@RequiredArgsConstructor
public class VeterinarianController {
    private final VeterinarianService veterinarianService;

    @GetMapping("/{id}")
    public ResponseEntity<VeterinarianDto> findVeterinarianById(@PathVariable Long id) {
        return new ResponseEntity<>(veterinarianService.findVeterinarianById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<VeterinarianDto>> findAllVeterinarians() {
        return new ResponseEntity<>(veterinarianService.findAllVeterinarians(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VeterinarianDto> createVeterinarian(@Valid @RequestBody VeterinarianCreateDto veterinarianCreateDto) {
        return new ResponseEntity<>(veterinarianService.createVeterinarian(veterinarianCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeterinarianDto> updateVeterinarian(@PathVariable Long id, @Valid @RequestBody VeterinarianUpdateDto veterinarianUpdateDto) {
        return new ResponseEntity<>(veterinarianService.updateVeterinarian(id, veterinarianUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VeterinarianDto> deleteVeterinarian(@PathVariable Long id) {
        veterinarianService.deleteVeterinarian(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
