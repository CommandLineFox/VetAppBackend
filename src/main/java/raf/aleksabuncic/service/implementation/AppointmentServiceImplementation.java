package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import raf.aleksabuncic.exception.UsedResourceException;
import raf.aleksabuncic.mapper.AppointmentMapper;
import raf.aleksabuncic.repository.AppointmentRepository;
import raf.aleksabuncic.repository.PatientRepository;
import raf.aleksabuncic.repository.VeterinarianRepository;
import raf.aleksabuncic.service.AppointmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppointmentServiceImplementation implements AppointmentService {
    private final AppointmentMapper appointmentMapper;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final VeterinarianRepository veterinarianRepository;

    @Transactional(readOnly = true)
    @Override
    public AppointmentDto findAppointmentById(Long id) {
        log.info("Finding appointment by id: {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found for this id: " + id));

        return appointmentMapper.appointmentToAppointmentDto(appointment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentDto> findAllAppointments() {
        log.info("Finding all appointments");

        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::appointmentToAppointmentDto)
                .toList();
    }

    @Override
    public AppointmentDto createAppointment(AppointmentCreateDto appointmentCreateDto) {
        log.info("Creating appointment: {}", appointmentCreateDto);

        Appointment appointment = appointmentMapper.appointmentCreateDtoToAppointment(appointmentCreateDto);

        Patient patient = patientRepository.findById(appointmentCreateDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + appointmentCreateDto.getPatientId()));
        appointment.setPatient(patient);

        Veterinarian veterinarian = veterinarianRepository.findById(appointmentCreateDto.getVeterinarianId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + appointmentCreateDto.getVeterinarianId()));
        appointment.setVeterinarian(veterinarian);


        try {
            appointmentRepository.save(appointment);
            log.info("Appointment created: {}", appointment);
            return appointmentMapper.appointmentToAppointmentDto(appointment);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Appointment already exists for this ID: " + appointmentCreateDto.getDate());
        }
    }

    @Override
    public AppointmentDto updateAppointment(Long id, AppointmentUpdateDto appointmentUpdateDto) {
        log.info("Updating appointment: {}", appointmentUpdateDto);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found for this id: " + id));

        if (appointmentUpdateDto.getDate() != null) {
            appointment.setDate(appointmentUpdateDto.getDate());
        }

        if (appointmentUpdateDto.getPatientId() != null) {
            Patient patient = patientRepository.findById(appointmentUpdateDto.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + appointmentUpdateDto.getPatientId()));
            appointment.setPatient(patient);
        }

        if (appointmentUpdateDto.getVeterinarianId() != null) {
            Veterinarian veterinarian = veterinarianRepository.findById(appointmentUpdateDto.getVeterinarianId())
                    .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found for this id: " + appointmentUpdateDto.getVeterinarianId()));
            appointment.setVeterinarian(veterinarian);
        }

        log.info("Appointment updated: {}", appointment);
        return appointmentMapper.appointmentToAppointmentDto(appointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        log.info("Deleting appointment with id: {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found for this id: " + id));

        try {
            appointmentRepository.delete(appointment);
            log.info("Appointment deleted: {}", appointment);
        } catch (DataIntegrityViolationException e) {
            throw new UsedResourceException("Cannot delete appointment with id: " + id + " because it is associated with other resources");
        }
    }
}
