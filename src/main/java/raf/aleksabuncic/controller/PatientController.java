package raf.aleksabuncic.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.aleksabuncic.dto.PatientCreateDto;
import raf.aleksabuncic.dto.PatientDto;
import raf.aleksabuncic.dto.PatientUpdateDto;
import raf.aleksabuncic.service.PatientService;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
@Tag(name = "Patient API", description = "API for managing patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PATIENT_LIST')")
    public ResponseEntity<PatientDto> findPatientById(@PathVariable Long id) {
        return new ResponseEntity<>(patientService.findPatientById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PATIENT_LIST')")
    public ResponseEntity<Iterable<PatientDto>> findAllPatients() {
        return new ResponseEntity<>(patientService.findAllPatients(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PATIENT_ADD')")
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientCreateDto patientCreateDto) {
        return new ResponseEntity<>(patientService.createPatient(patientCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PATIENT_UPDATE')")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientUpdateDto patientUpdateDto) {
        return new ResponseEntity<>(patientService.updatePatient(id, patientUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PATIENT_DELETE')")
    public ResponseEntity<PatientDto> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
