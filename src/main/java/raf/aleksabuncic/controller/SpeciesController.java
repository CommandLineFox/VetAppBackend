package raf.aleksabuncic.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Species API", description = "API for managing species")
@CrossOrigin(origins = "http://localhost:3000")
public class SpeciesController {
    private final SpeciesService speciesService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SPECIES_LIST')")
    public ResponseEntity<SpeciesDto> findSpeciesById(@PathVariable Long id) {
        return new ResponseEntity<>(speciesService.findSpeciesById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SPECIES_LIST')")
    public ResponseEntity<Iterable<SpeciesDto>> findAllSpecies() {
        return new ResponseEntity<>(speciesService.findAllSpecies(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SPECIES_ADD')")
    public ResponseEntity<SpeciesDto> createSpecies(@Valid @RequestBody SpeciesCreateDto speciesCreateDto) {
        return new ResponseEntity<>(speciesService.createSpecies(speciesCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SPECIES_UPDATE')")
    public ResponseEntity<SpeciesDto> updateSpecies(@PathVariable Long id, @Valid @RequestBody SpeciesUpdateDto speciesUpdateDto) {
        return new ResponseEntity<>(speciesService.updateSpecies(id, speciesUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SPECIES_DELETE')")
    public ResponseEntity<SpeciesDto> deleteSpecies(@PathVariable Long id) {
        speciesService.deleteSpecies(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
