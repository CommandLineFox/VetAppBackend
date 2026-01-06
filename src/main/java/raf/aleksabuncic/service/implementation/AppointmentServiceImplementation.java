package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.aleksabuncic.domain.Appointment;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.AppointmentDto;
import raf.aleksabuncic.dto.AppointmentCreateDto;
import raf.aleksabuncic.dto.AppointmentUpdateDto;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.mapper.AppointmentMapper;
import raf.aleksabuncic.repository.AppointmentRepository;
import raf.aleksabuncic.repository.PatientRepository;
import raf.aleksabuncic.repository.VeterinarianRepository;
import raf.aleksabuncic.service.AppointmentService;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImplementation implements AppointmentService {
    private final AppointmentMapper appointmentMapper;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final VeterinarianRepository veterinarianRepository;

    @Override
    public AppointmentDto findAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.getAppointmentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found for this id: " + id));

        return appointmentMapper.appointmentToAppointmentDto(appointment);
    }

    @Transactional
    @Override
    public AppointmentDto createAppointment(AppointmentCreateDto appointmentCreateDto) {
        Appointment appointment = appointmentMapper.appointmentCreateDtoToAppointment(appointmentCreateDto);

        Patient patient = patientRepository.getPatientById(appointmentCreateDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + appointmentCreateDto.getPatientId()));
        appointment.setPatient(patient);

        Veterinarian veterinarian = veterinarianRepository.getVeterinarianById(appointmentCreateDto.getVeterinarianId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + appointmentCreateDto.getVeterinarianId()));
        appointment.setVeterinarian(veterinarian);


        try {
            appointmentRepository.save(appointment);
            return appointmentMapper.appointmentToAppointmentDto(appointment);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Appointment already exists for this ID: " + appointmentCreateDto.getDate());
        }
    }

    @Transactional
    @Override
    public AppointmentDto updateAppointment(Long id, AppointmentUpdateDto appointmentUpdateDto) {
        Appointment appointment = appointmentRepository.getAppointmentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found for this id: " + id));

        if (appointmentUpdateDto.getDate() != null) {
            appointment.setDate(appointmentUpdateDto.getDate());
        }

        if (appointmentUpdateDto.getPatientId() != null) {
            Patient patient = patientRepository.getPatientById(appointmentUpdateDto.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + appointmentUpdateDto.getPatientId()));
            appointment.setPatient(patient);
        }

        if (appointmentUpdateDto.getVeterinarianId() != null) {
            Veterinarian veterinarian = veterinarianRepository.getVeterinarianById(appointmentUpdateDto.getVeterinarianId())
                    .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + appointmentUpdateDto.getVeterinarianId()));
            appointment.setVeterinarian(veterinarian);
        }

        try {
            appointmentRepository.save(appointment);
            return appointmentMapper.appointmentToAppointmentDto(appointment);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("No changes made to appointment: " + appointmentUpdateDto.getDate());
        }
    }

    @Transactional
    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found for this id: " + id);
        }

        appointmentRepository.deleteById(id);
    }
}
