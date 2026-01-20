package raf.aleksabuncic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.aleksabuncic.dto.OwnerCreateDto;
import raf.aleksabuncic.dto.OwnerDto;
import raf.aleksabuncic.dto.OwnerUpdateDto;
import raf.aleksabuncic.service.OwnerService;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('OWNER_LIST')")
    public ResponseEntity<OwnerDto> findOwnerById(@PathVariable Long id) {
        return new ResponseEntity<>(ownerService.findOwnerById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('OWNER_LIST')")
    public ResponseEntity<Iterable<OwnerDto>> findAllOwners() {
        return new ResponseEntity<>(ownerService.findAllOwners(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('OWNER_ADD')")
    public ResponseEntity<OwnerDto> createOwner(@Valid @RequestBody OwnerCreateDto ownerCreateDto) {
        return new ResponseEntity<>(ownerService.createOwner(ownerCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('OWNER_UPDATE')")
    public ResponseEntity<OwnerDto> updateOwner(@PathVariable Long id, @Valid @RequestBody OwnerUpdateDto ownerUpdateDto) {
        return new ResponseEntity<>(ownerService.updateOwner(id, ownerUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('OWNER_DELETE')")
    public ResponseEntity<OwnerDto> deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
