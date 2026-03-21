package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.aleksabuncic.domain.Examination;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.*;
import raf.aleksabuncic.exception.BadRequestException;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.exception.UsedResourceException;
import raf.aleksabuncic.mapper.ExaminationMapper;
import raf.aleksabuncic.repository.ExaminationRepository;
import raf.aleksabuncic.repository.PatientRepository;
import raf.aleksabuncic.repository.VeterinarianRepository;
import raf.aleksabuncic.repository.specification.ExaminationSpecifications;
import raf.aleksabuncic.service.ExaminationService;
import raf.aleksabuncic.utils.SortUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExaminationServiceImplementation implements ExaminationService {
    private final ExaminationMapper examinationMapper;
    private final ExaminationRepository examinationRepository;
    private final PatientRepository patientRepository;
    private final VeterinarianRepository veterinarianRepository;

    @Transactional(readOnly = true)
    @Override
    public ExaminationDto findExaminationById(Long id) {
        log.info("Finding examination by id: {}", id);

        Examination examination = examinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Examination not found for this id: " + id));

        return examinationMapper.examinationToExaminationDto(examination);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExaminationDto> findAllExaminations(ExaminationSearchDto examinationSearchDto) {
        log.info("Finding all examinations with filters: {}", examinationSearchDto);

        Specification<Examination> specification = ExaminationSpecifications.build(examinationSearchDto);

        return examinationRepository.findAll(specification)
                .stream()
                .map(examinationMapper::examinationToExaminationDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<ExaminationDto> findAllExaminations(ExaminationSearchDto examinationSearchDto, Pageable pageable) {
        log.info("Finding all examinations with filters: {} and pagination: {}", examinationSearchDto, pageable);

        Specification<Examination> specification = ExaminationSpecifications.build(examinationSearchDto);

        return examinationRepository.findAll(specification, pageable)
                .map(examinationMapper::examinationToExaminationDto);
    }

    @Override
    public ExaminationDto createExamination(ExaminationCreateDto examinationCreateDto) {
        log.info("Creating examination: {}", examinationCreateDto);

        Examination examination = examinationMapper.examinationCreateDtoToExamination(examinationCreateDto);

        Patient patient = patientRepository.findById(examinationCreateDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + examinationCreateDto.getPatientId()));
        examination.setPatient(patient);

        Veterinarian veterinarian = veterinarianRepository.findById(examinationCreateDto.getVeterinarianId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + examinationCreateDto.getVeterinarianId()));
        examination.setVeterinarian(veterinarian);

        try {
            examinationRepository.save(examination);
            log.info("Examination created: {}", examination);
            return examinationMapper.examinationToExaminationDto(examination);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Examination already exists for this ID: " + examination.getId());
        }
    }

    @Override
    public ExaminationDto updateExamination(Long id, ExaminationUpdateDto examinationUpdateDto) {
        log.info("Updating examination: {}", examinationUpdateDto);

        Examination examination = examinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Examination not found for this id: " + id));

        if (examinationUpdateDto.getPatientId() != null) {
            Patient patient = patientRepository.findById(examinationUpdateDto.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + examinationUpdateDto.getPatientId()));
            examination.setPatient(patient);
        }

        if (examinationUpdateDto.getVeterinarianId() != null) {
            Veterinarian veterinarian = veterinarianRepository.findById(examinationUpdateDto.getVeterinarianId())
                    .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + examinationUpdateDto.getVeterinarianId()));
            examination.setVeterinarian(veterinarian);
        }

        examinationMapper.examinationUpdateDtoToExamination(examinationUpdateDto, examination);

        try {
            examinationRepository.save(examination);
            log.info("Examination updated: {}", examination);
            return examinationMapper.examinationToExaminationDto(examination);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("There was a problem updating the examination");
        }
    }

    @Override
    public void deleteExamination(Long id) {
        log.info("Deleting examination with id: {}", id);

        Examination examination = examinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Examination not found for this id: " + id));

        try {
            examinationRepository.delete(examination);
            log.info("Examination deleted: {}", examination);
        } catch (DataIntegrityViolationException e) {
            throw new UsedResourceException("Cannot delete examination with id: " + id + " because it is associated with other resources");
        }
    }
}
