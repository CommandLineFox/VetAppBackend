package raf.aleksabuncic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.aleksabuncic.dto.SpeciesDto;
import raf.aleksabuncic.dto.SpeciesCreateDto;
import raf.aleksabuncic.dto.SpeciesUpdateDto;
import raf.aleksabuncic.service.SpeciesService;

@RestController
@RequestMapping("/species")
@RequiredArgsConstructor
public class SpeciesController {
    private final SpeciesService speciesService;

    @PreAuthorize("hasPermission(#id, 'SPECIES_LIST')")
    @GetMapping("/{id}")
    public ResponseEntity<SpeciesDto> findSpeciesById(@PathVariable Long id) {
        return new ResponseEntity<>(speciesService.findSpeciesById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(null, 'SPECIES_LIST')")
    @GetMapping
    public ResponseEntity<Iterable<SpeciesDto>> findAllSpecies() {
        return new ResponseEntity<>(speciesService.findAllSpecies(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SpeciesDto> createSpecies(@Valid @RequestBody SpeciesCreateDto speciesCreateDto) {
        return new ResponseEntity<>(speciesService.createSpecies(speciesCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpeciesDto> updateSpecies(@PathVariable Long id, @Valid @RequestBody SpeciesUpdateDto speciesUpdateDto) {
        return new ResponseEntity<>(speciesService.updateSpecies(id, speciesUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SpeciesDto> deleteSpecies(@PathVariable Long id) {
        speciesService.deleteSpecies(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
