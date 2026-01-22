package raf.aleksabuncic.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.aleksabuncic.dto.ExaminationCreateDto;
import raf.aleksabuncic.dto.ExaminationDto;
import raf.aleksabuncic.dto.ExaminationUpdateDto;
import raf.aleksabuncic.service.ExaminationService;

@RestController
@RequestMapping("/examination")
@RequiredArgsConstructor
@Tag(name = "Examination API", description = "API for managing examinations")
public class ExaminationController {
    private final ExaminationService examinationService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EXAMINATION_LIST')")
    public ResponseEntity<ExaminationDto> findExaminationById(@PathVariable Long id) {
        return new ResponseEntity<>(examinationService.findExaminationById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('EXAMINATION_LIST')")
    public ResponseEntity<Iterable<ExaminationDto>> findAllExaminations() {
        return new ResponseEntity<>(examinationService.findAllExaminations(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EXAMINATION_ADD')")
    public ResponseEntity<ExaminationDto> createExamination(@Valid @RequestBody ExaminationCreateDto examinationCreateDto) {
        return new ResponseEntity<>(examinationService.createExamination(examinationCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EXAMINATION_UPDATE')")
    public ResponseEntity<ExaminationDto> updateExamination(@PathVariable Long id, @Valid @RequestBody ExaminationUpdateDto examinationUpdateDto) {
        return new ResponseEntity<>(examinationService.updateExamination(id, examinationUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EXAMINATION_DELETE')")
    public ResponseEntity<ExaminationDto> deleteExamination(@PathVariable Long id) {
        examinationService.deleteExamination(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
