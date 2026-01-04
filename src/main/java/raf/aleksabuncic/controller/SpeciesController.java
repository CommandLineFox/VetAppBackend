package raf.aleksabuncic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.aleksabuncic.dto.SpeciesDto;
import raf.aleksabuncic.dto.SpeciesRequestDto;
import raf.aleksabuncic.service.SpeciesService;

@RestController
@RequestMapping("/species")
@RequiredArgsConstructor
public class SpeciesController {
    private final SpeciesService speciesService;

    @GetMapping("/{id}")
    public ResponseEntity<SpeciesDto> findSpeciesById(@PathVariable Long id) {
        return new ResponseEntity<>(speciesService.findSpeciesById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SpeciesDto> createSpecies(@RequestBody SpeciesRequestDto speciesRequestDto) {
        return new ResponseEntity<>(speciesService.createSpecies(speciesRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpeciesDto> updateSpecies(@PathVariable Long id, @RequestBody SpeciesRequestDto speciesRequestDto) {
        return new ResponseEntity<>(speciesService.updateSpecies(id, speciesRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteSpecies(@PathVariable Long id) {
        speciesService.deleteSpecies(id);
    }
}
