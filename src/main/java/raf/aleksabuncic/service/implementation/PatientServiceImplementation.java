package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.aleksabuncic.domain.Breed;
import raf.aleksabuncic.domain.Owner;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.dto.PatientDto;
import raf.aleksabuncic.dto.PatientCreateDto;
import raf.aleksabuncic.dto.PatientSearchDto;
import raf.aleksabuncic.dto.PatientUpdateDto;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.exception.UsedResourceException;
import raf.aleksabuncic.mapper.PatientMapper;
import raf.aleksabuncic.repository.BreedRepository;
import raf.aleksabuncic.repository.OwnerRepository;
import raf.aleksabuncic.repository.PatientRepository;
import raf.aleksabuncic.repository.specification.PatientSpecifications;
import raf.aleksabuncic.service.PatientService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PatientServiceImplementation implements PatientService {
    private final PatientMapper patientMapper;
    private final PatientRepository patientRepository;
    private final BreedRepository breedRepository;
    private final OwnerRepository ownerRepository;

    @Transactional(readOnly = true)
    @Override
    public PatientDto findPatientById(Long id) {
        log.info("Finding patient by id: {}", id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + id));

        return patientMapper.patientToPatientDto(patient);
    }

    private List<PatientDto> findAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::patientToPatientDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<PatientDto> findAllPatients(PatientSearchDto patientSearchDto, Pageable pageable) {
        log.info("Finding all patients with filters: {} and pagination: {}", patientSearchDto, pageable);

        Specification<Patient> specification = PatientSpecifications.build(patientSearchDto);

        return patientRepository.findAll(specification, pageable)
                .map(patientMapper::patientToPatientDto);
    }

    @Override
    public PatientDto createPatient(PatientCreateDto patientCreateDto) {
        log.info("Creating patient: {}", patientCreateDto);

        boolean existingPatient = patientRepository.existsByPassportNumberOrMicrochipNumberOrCartonNumber(patientCreateDto.getPassportNumber(), patientCreateDto.getMicrochipNumber(), patientCreateDto.getCartonNumber());
        if (existingPatient) {
            throw new DuplicateResourceException("Patient already exists for this passport number or microchip number");
        }

        Patient patient = patientMapper.patientCreateDtoToPatient(patientCreateDto);

        Breed breed = breedRepository.findById(patientCreateDto.getBreedId())
                .orElseThrow(() -> new ResourceNotFoundException("Breed not found for this id: " + patientCreateDto.getBreedId()));
        patient.setBreed(breed);

        Owner owner = ownerRepository.findById(patientCreateDto.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found for this id: " + patientCreateDto.getOwnerId()));
        patient.setOwner(owner);

        try {
            patientRepository.save(patient);
            return patientMapper.patientToPatientDto(patient);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Patient already exists for this ID: " + patient.getId());
        }
    }

    @Override
    public PatientDto updatePatient(Long id, PatientUpdateDto patientUpdateDto) {
        log.info("Updating patient: {}", patientUpdateDto);

        boolean existingPatient = patientRepository.existsByPassportNumberOrMicrochipNumberOrCartonNumber(patientUpdateDto.getPassportNumber(), patientUpdateDto.getMicrochipNumber(), patientUpdateDto.getCartonNumber());

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + id));

        if (patientUpdateDto.getBirthDate() != null && patient.getBirthDate().equals(patientUpdateDto.getBirthDate())) {
            throw new DuplicateResourceException("Patient birth date cannot be the same as the existing one");
        }

        if (patientUpdateDto.getName() != null && patient.getName().equals(patientUpdateDto.getName())) {
            throw new DuplicateResourceException("Patient name cannot be the same as the existing one");
        }

        if (patientUpdateDto.getGender() != null && patient.getGender().equals(patientUpdateDto.getGender())) {
            throw new DuplicateResourceException("Patient gender cannot be the same as the existing one");

        }

        if (patientUpdateDto.getPassportNumber() != null) {
            if (existingPatient) {
                throw new DuplicateResourceException("Patient with this passport number already exists");
            }

            if (patient.getPassportNumber().equals(patientUpdateDto.getPassportNumber())) {
                throw new DuplicateResourceException("Patient passport number cannot be the same as the existing one");
            }
        }

        if (patientUpdateDto.getMicrochipNumber() != null) {
            if (existingPatient) {
                throw new DuplicateResourceException("Patient with this microchip number already exists");
            }

            if (patient.getMicrochipNumber().equals(patientUpdateDto.getMicrochipNumber())) {
                throw new DuplicateResourceException("Patient microchip number cannot be the same as the existing one");
            }
        }

        if (patientUpdateDto.getCartonNumber() != null && patient.getCartonNumber().equals(patientUpdateDto.getCartonNumber())) {
            throw new DuplicateResourceException("Patient carton number cannot be the same as the existing one");
        }

        if (patientUpdateDto.getBreedId() != null) {
            if (patient.getBreed().getId().equals(patientUpdateDto.getBreedId())) {
                throw new DuplicateResourceException("Patient breed cannot be the same as the existing one");
            }

            Breed breed = breedRepository.findById(patientUpdateDto.getBreedId())
                    .orElseThrow(() -> new ResourceNotFoundException("Breed not found for this id: " + patientUpdateDto.getBreedId()));
            patient.setBreed(breed);
        }

        if (patientUpdateDto.getOwnerId() != null) {
            if (patient.getOwner().getId().equals(patientUpdateDto.getOwnerId())) {
                throw new DuplicateResourceException("Patient owner cannot be the same as the existing one");
            }

            Owner owner = ownerRepository.findById(patientUpdateDto.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Owner not found for this id: " + patientUpdateDto.getOwnerId()));
            patient.setOwner(owner);
        }

        patientMapper.patientUpdateDtoToPatient(patientUpdateDto, patient);

        try {
            patientRepository.save(patient);
            log.info("Patient updated: {}", patient);
            return patientMapper.patientToPatientDto(patient);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("There was a problem updating the patient");
        }
    }

    @Override
    public void deletePatient(Long id) {
        log.info("Deleting patient with id: {}", id);
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + id));

        try {
            patientRepository.delete(patient);
            log.info("Patient deleted: {}", patient);
        } catch (DataIntegrityViolationException e) {
            throw new UsedResourceException("Cannot delete patient with id: " + id + " because it is associated with other resources");
        }
    }
}
