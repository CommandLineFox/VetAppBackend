package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.aleksabuncic.domain.Examination;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.ExaminationDto;
import raf.aleksabuncic.dto.ExaminationCreateDto;
import raf.aleksabuncic.dto.ExaminationUpdateDto;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.exception.UsedResourceException;
import raf.aleksabuncic.mapper.ExaminationMapper;
import raf.aleksabuncic.repository.ExaminationRepository;
import raf.aleksabuncic.repository.PatientRepository;
import raf.aleksabuncic.repository.VeterinarianRepository;
import raf.aleksabuncic.service.ExaminationService;

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

        Examination examination = examinationRepository.getExaminationById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Examination not found for this id: " + id));

        return examinationMapper.examinationToExaminationDto(examination);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExaminationDto> findAllExaminations() {
        log.info("Finding all examinations");

        return examinationRepository.findAll()
                .stream()
                .map(examinationMapper::examinationToExaminationDto)
                .toList();
    }

    @Override
    public ExaminationDto createExamination(ExaminationCreateDto examinationCreateDto) {
        log.info("Creating examination: {}", examinationCreateDto);

        Examination examination = examinationMapper.examinationCreateDtoToExamination(examinationCreateDto);

        Patient patient = patientRepository.getPatientById(examinationCreateDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + examinationCreateDto.getPatientId()));
        examination.setPatient(patient);

        Veterinarian veterinarian = veterinarianRepository.getVeterinarianById(examinationCreateDto.getVeterinarianId())
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

        Examination examination = examinationRepository.getExaminationById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Examination not found for this id: " + id));

        if (examinationUpdateDto.getDate() != null) {
            examination.setDate(examinationUpdateDto.getDate());
        }

        if (examinationUpdateDto.getAnamnesis() != null) {
            examination.setAnamnesis(examinationUpdateDto.getAnamnesis());
        }

        if (examinationUpdateDto.getClinicalPresentation() != null) {
            examination.setClinicalPresentation(examinationUpdateDto.getClinicalPresentation());
        }

        if (examinationUpdateDto.getDiagnosis() != null) {
            examination.setDiagnosis(examinationUpdateDto.getDiagnosis());
        }

        if (examinationUpdateDto.getTreatment() != null) {
            examination.setTreatment(examinationUpdateDto.getTreatment());
        }

        if (examinationUpdateDto.getLaboratoryAnalysis() != null) {
            examination.setLaboratoryAnalysis(examinationUpdateDto.getLaboratoryAnalysis());
        }

        if (examinationUpdateDto.getSpecialistExamination() != null) {
            examination.setSpecialistExamination(examinationUpdateDto.getSpecialistExamination());
        }

        if (examinationUpdateDto.getRemarks() != null) {
            examination.setRemarks(examinationUpdateDto.getRemarks());
        }

        if (examinationUpdateDto.getPatientId() != null) {
            Patient patient = patientRepository.getPatientById(examinationUpdateDto.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + examinationUpdateDto.getPatientId()));
            examination.setPatient(patient);
        }

        if (examinationUpdateDto.getVeterinarianId() != null) {
            Veterinarian veterinarian = veterinarianRepository.getVeterinarianById(examinationUpdateDto.getVeterinarianId())
                    .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + examinationUpdateDto.getVeterinarianId()));
            examination.setVeterinarian(veterinarian);
        }

        log.info("Examination updated: {}", examination);
        return examinationMapper.examinationToExaminationDto(examination);
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
