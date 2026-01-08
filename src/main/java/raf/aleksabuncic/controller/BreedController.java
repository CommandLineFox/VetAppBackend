package raf.aleksabuncic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BreedDto> findBreedById(@PathVariable Long id) {
        return new ResponseEntity<>(breedService.findBreedById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<BreedDto>> findAllBreeds() {
        return new ResponseEntity<>(breedService.findAllBreeds(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BreedDto> createBreed(@Valid @RequestBody BreedCreateDto breedCreateDto) {
        return new ResponseEntity<>(breedService.createBreed(breedCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BreedDto> updateBreed(@PathVariable Long id, @Valid @RequestBody BreedUpdateDto breedUpdateDto) {
        return new ResponseEntity<>(breedService.updateBreed(id, breedUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BreedDto> deleteBreed(@PathVariable Long id) {
        breedService.deleteBreed(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
