package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import raf.aleksabuncic.domain.Appointment;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.AppointmentDto;
import raf.aleksabuncic.dto.AppointmentRequestDto;
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

    @Override
    public AppointmentDto createAppointment(AppointmentRequestDto appointmentRequestDto) {
        Appointment appointment = appointmentMapper.appointmentRequestDtoToAppointment(appointmentRequestDto);

        Patient patient = patientRepository.getPatientById(appointmentRequestDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + appointmentRequestDto.getPatientId()));
        appointment.setPatient(patient);

        Veterinarian veterinarian = veterinarianRepository.getVeterinarianById(appointmentRequestDto.getVeterinarianId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + appointmentRequestDto.getVeterinarianId()));
        appointment.setVeterinarian(veterinarian);


        try {
            appointmentRepository.save(appointment);
            return appointmentMapper.appointmentToAppointmentDto(appointment);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Appointment already exists for this ID: " + appointmentRequestDto.getDate());
        }
    }

    @Override
    public AppointmentDto updateAppointment(AppointmentRequestDto appointmentRequestDto) {
        Appointment appointment = appointmentMapper.appointmentRequestDtoToAppointment(appointmentRequestDto);

        if (appointmentRequestDto.getDate() != null) {
            appointment.setDate(appointmentRequestDto.getDate());
        }

        if (appointmentRequestDto.getPatientId() != null) {
            Patient patient = patientRepository.getPatientById(appointmentRequestDto.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + appointmentRequestDto.getPatientId()));
            appointment.setPatient(patient);
        }

        if (appointmentRequestDto.getVeterinarianId() != null) {
            Veterinarian veterinarian = veterinarianRepository.getVeterinarianById(appointmentRequestDto.getVeterinarianId())
                    .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + appointmentRequestDto.getVeterinarianId()));
            appointment.setVeterinarian(veterinarian);
        }

        try {
            appointmentRepository.save(appointment);
            return appointmentMapper.appointmentToAppointmentDto(appointment);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("No changes made to appointment: " + appointmentRequestDto.getDate());
        }
    }

    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found for this id: " + id);
        }

        appointmentRepository.deleteById(id);
    }
}
