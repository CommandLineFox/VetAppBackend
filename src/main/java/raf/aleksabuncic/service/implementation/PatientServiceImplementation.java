package raf.aleksabuncic.service.implementation;

import org.springframework.stereotype.Service;
import raf.aleksabuncic.dto.PatientDto;
import raf.aleksabuncic.dto.PatientCreateDto;
import raf.aleksabuncic.dto.PatientUpdateDto;
import raf.aleksabuncic.service.PatientService;

@Service
public class PatientServiceImplementation implements PatientService {
    @Override
    public PatientDto findPatientById(Long id) {
        return null;
    }

    @Override
    public PatientDto createPatient(PatientCreateDto patientCreateDto) {
        return null;
    }

    @Override
    public PatientDto updatePatient(PatientUpdateDto patientUpdateDto) {
        return null;
    }

    @Override
    public void deletePatient(Long id) {

    }
}
