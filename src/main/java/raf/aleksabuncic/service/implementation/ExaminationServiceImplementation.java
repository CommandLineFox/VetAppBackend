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

        Examination examination = examinationRepository.findById(id)
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

        if (examinationUpdateDto.getDate() != null) {
            if (examination.getDate().equals(examinationUpdateDto.getDate())) {
                throw new DuplicateResourceException("Examination date cannot be the same as the existing one");
            }

            examination.setDate(examinationUpdateDto.getDate());
        }

        if (examinationUpdateDto.getAnamnesis() != null) {
            if (examination.getAnamnesis().equals(examinationUpdateDto.getAnamnesis())) {
                throw new DuplicateResourceException("Examination anamnesis cannot be the same as the existing one");
            }

            examination.setAnamnesis(examinationUpdateDto.getAnamnesis());
        }

        if (examinationUpdateDto.getClinicalPresentation() != null) {
            if (examination.getClinicalPresentation().equals(examinationUpdateDto.getClinicalPresentation())) {
                throw new DuplicateResourceException("Examination clinical presentation cannot be the same as the existing one");
            }

            examination.setClinicalPresentation(examinationUpdateDto.getClinicalPresentation());
        }

        if (examinationUpdateDto.getDiagnosis() != null) {
            if (examination.getDiagnosis().equals(examinationUpdateDto.getDiagnosis())) {
                throw new DuplicateResourceException("Examination diagnosis cannot be the same as the existing one");
            }

            examination.setDiagnosis(examinationUpdateDto.getDiagnosis());
        }

        if (examinationUpdateDto.getTreatment() != null) {
            if (examination.getTreatment().equals(examinationUpdateDto.getTreatment())) {
                throw new DuplicateResourceException("Examination treatment cannot be the same as the existing one");
            }

            examination.setTreatment(examinationUpdateDto.getTreatment());
        }

        if (examinationUpdateDto.getLaboratoryAnalysis() != null) {
            if (examination.getLaboratoryAnalysis().equals(examinationUpdateDto.getLaboratoryAnalysis())) {
                throw new DuplicateResourceException("Examination laboratory analysis cannot be the same as the existing one");
            }

            examination.setLaboratoryAnalysis(examinationUpdateDto.getLaboratoryAnalysis());
        }

        if (examinationUpdateDto.getSpecialistExamination() != null) {
            if (examination.getSpecialistExamination().equals(examinationUpdateDto.getSpecialistExamination())) {
                throw new DuplicateResourceException("Examination specialist examination cannot be the same as the existing one");
            }

            examination.setSpecialistExamination(examinationUpdateDto.getSpecialistExamination());
        }

        if (examinationUpdateDto.getRemarks() != null) {
            if (examination.getRemarks().equals(examinationUpdateDto.getRemarks())) {
                throw new DuplicateResourceException("Examination remarks cannot be the same as the existing one");
            }

            examination.setRemarks(examinationUpdateDto.getRemarks());
        }

        if (examinationUpdateDto.getPatientId() != null) {
            if (examination.getPatient().getId().equals(examinationUpdateDto.getPatientId())) {
                throw new DuplicateResourceException("Examination patient cannot be the same as the existing one");
            }

            Patient patient = patientRepository.findById(examinationUpdateDto.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + examinationUpdateDto.getPatientId()));
            examination.setPatient(patient);
        }

        if (examinationUpdateDto.getVeterinarianId() != null) {
            if (examination.getVeterinarian().getId().equals(examinationUpdateDto.getVeterinarianId())) {
                throw new DuplicateResourceException("Examination veterinarian cannot be the same as the existing one");
            }

            Veterinarian veterinarian = veterinarianRepository.findById(examinationUpdateDto.getVeterinarianId())
                    .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + examinationUpdateDto.getVeterinarianId()));
            examination.setVeterinarian(veterinarian);
        }

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
