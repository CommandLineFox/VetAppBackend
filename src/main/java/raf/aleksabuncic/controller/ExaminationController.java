package raf.aleksabuncic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.aleksabuncic.dto.ExaminationCreateDto;
import raf.aleksabuncic.dto.ExaminationDto;
import raf.aleksabuncic.dto.ExaminationUpdateDto;
import raf.aleksabuncic.service.ExaminationService;

@RestController
@RequestMapping("/examination")
@RequiredArgsConstructor
public class ExaminationController {
    private final ExaminationService examinationService;

    @GetMapping("/{id}")
    public ResponseEntity<ExaminationDto> findExaminationById(@PathVariable Long id) {
        return new ResponseEntity<>(examinationService.findExaminationById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<ExaminationDto>> findAllExaminations() {
        return new ResponseEntity<>(examinationService.findAllExaminations(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExaminationDto> createExamination(@Valid @RequestBody ExaminationCreateDto examinationCreateDto) {
        return new ResponseEntity<>(examinationService.createExamination(examinationCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExaminationDto> updateExamination(@PathVariable Long id, @Valid @RequestBody ExaminationUpdateDto examinationUpdateDto) {
        return new ResponseEntity<>(examinationService.updateExamination(id, examinationUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ExaminationDto> deleteExamination(@PathVariable Long id) {
        examinationService.deleteExamination(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
