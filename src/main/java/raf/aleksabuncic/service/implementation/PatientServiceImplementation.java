package raf.aleksabuncic.service.implementation;

import org.springframework.stereotype.Service;
import raf.aleksabuncic.dto.PatientDto;
import raf.aleksabuncic.dto.PatientRequestDto;
import raf.aleksabuncic.service.PatientService;

@Service
public class PatientServiceImplementation implements PatientService {
    @Override
    public PatientDto findPatientById(Long id) {
        return null;
    }

    @Override
    public PatientDto createPatient(PatientRequestDto patientRequestDto) {
        return null;
    }

    @Override
    public PatientDto updatePatient(PatientRequestDto patientRequestDto) {
        return null;
    }

    @Override
    public void deletePatient(Long id) {

    }
}
