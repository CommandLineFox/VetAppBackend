package raf.aleksabuncic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.aleksabuncic.dto.PatientCreateDto;
import raf.aleksabuncic.dto.PatientDto;
import raf.aleksabuncic.dto.PatientUpdateDto;
import raf.aleksabuncic.service.PatientService;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> findPatientById(@PathVariable Long id) {
        return new ResponseEntity<>(patientService.findPatientById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientCreateDto patientCreateDto) {
        return new ResponseEntity<>(patientService.createPatient(patientCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientUpdateDto patientUpdateDto) {
        return new ResponseEntity<>(patientService.updatePatient(id, patientUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PatientDto> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
