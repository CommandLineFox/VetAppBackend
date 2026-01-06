package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.aleksabuncic.domain.Breed;
import raf.aleksabuncic.domain.Owner;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.dto.PatientDto;
import raf.aleksabuncic.dto.PatientCreateDto;
import raf.aleksabuncic.dto.PatientUpdateDto;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.mapper.PatientMapper;
import raf.aleksabuncic.repository.BreedRepository;
import raf.aleksabuncic.repository.OwnerRepository;
import raf.aleksabuncic.repository.PatientRepository;
import raf.aleksabuncic.service.PatientService;

@Service
@RequiredArgsConstructor
public class PatientServiceImplementation implements PatientService {
    private final PatientMapper patientMapper;
    private final PatientRepository patientRepository;
    private final BreedRepository breedRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public PatientDto findPatientById(Long id) {
        Patient patient = patientRepository.getPatientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + id));

        return patientMapper.patientToPatientDto(patient);
    }

    @Transactional
    @Override
    public PatientDto createPatient(PatientCreateDto patientCreateDto) {
        Patient patient = patientMapper.patientCreateDtoToPatient(patientCreateDto);

        Breed breed = breedRepository.getBreedById(patientCreateDto.getBreedId())
                .orElseThrow(() -> new ResourceNotFoundException("Breed not found for this id: " + patientCreateDto.getBreedId()));
        patient.setBreed(breed);

        Owner owner = ownerRepository.getOwnerById(patientCreateDto.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found for this id: " + patientCreateDto.getOwnerId()));
        patient.setOwner(owner);

        try {
            patientRepository.save(patient);
            return patientMapper.patientToPatientDto(patientRepository.save(patient));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Patient already exists for this ID: " + patient.getId());
        }
    }

    @Transactional
    @Override
    public PatientDto updatePatient(Long id, PatientUpdateDto patientUpdateDto) {
        Patient patient = patientRepository.getPatientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + id));

        if (patientUpdateDto.getBirthDate() != null) {
            patient.setBirthDate(patientUpdateDto.getBirthDate());
        }

        if (patientUpdateDto.getName() != null) {
            patient.setName(patientUpdateDto.getName());
        }

        if (patientUpdateDto.getGender() != null) {
            patient.setGender(patientUpdateDto.getGender());
        }

        if (patientUpdateDto.getPassportNumber() != null) {
            patient.setPassportNumber(patientUpdateDto.getPassportNumber());
        }

        if (patientUpdateDto.getMicrochipNumber() != null) {
            patient.setMicrochipNumber(patientUpdateDto.getMicrochipNumber());
        }

        if (patientUpdateDto.getCartonNumber() != null) {
            patient.setCartonNumber(patientUpdateDto.getCartonNumber());
        }

        if (patientUpdateDto.getBreedId() != null) {
            Breed breed = breedRepository.getBreedById(patientUpdateDto.getBreedId())
                    .orElseThrow(() -> new ResourceNotFoundException("Breed not found for this id: " + patientUpdateDto.getBreedId()));
            patient.setBreed(breed);
        }

        if (patientUpdateDto.getOwnerId() != null) {
            Owner owner = ownerRepository.getOwnerById(patientUpdateDto.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Owner not found for this id: " + patientUpdateDto.getOwnerId()));
            patient.setOwner(owner);
        }

        try {
            patientRepository.save(patient);
            return patientMapper.patientToPatientDto(patient);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("No changes made to patient");
        }
    }

    @Transactional
    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found for this id: " + id);
        }

        patientRepository.deleteById(id);
    }
}
