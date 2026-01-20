package raf.aleksabuncic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.aleksabuncic.dto.BreedCreateDto;
import raf.aleksabuncic.dto.BreedDto;
import raf.aleksabuncic.dto.BreedUpdateDto;
import raf.aleksabuncic.service.BreedService;

@RestController
@RequestMapping("/breed")
@RequiredArgsConstructor
public class BreedController {
    private final BreedService breedService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('BREED_LIST')")
    public ResponseEntity<BreedDto> findBreedById(@PathVariable Long id) {
        return new ResponseEntity<>(breedService.findBreedById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('BREED_LIST')")
    public ResponseEntity<Iterable<BreedDto>> findAllBreeds() {
        return new ResponseEntity<>(breedService.findAllBreeds(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('BREED_ADD')")
    public ResponseEntity<BreedDto> createBreed(@Valid @RequestBody BreedCreateDto breedCreateDto) {
        return new ResponseEntity<>(breedService.createBreed(breedCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('BREED_UPDATE')")
    public ResponseEntity<BreedDto> updateBreed(@PathVariable Long id, @Valid @RequestBody BreedUpdateDto breedUpdateDto) {
        return new ResponseEntity<>(breedService.updateBreed(id, breedUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('BREED_DELETE')")
    public ResponseEntity<BreedDto> deleteBreed(@PathVariable Long id) {
        breedService.deleteBreed(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
