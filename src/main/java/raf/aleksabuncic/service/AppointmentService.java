package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.AppointmentCreateDto;
import raf.aleksabuncic.dto.AppointmentDto;
import raf.aleksabuncic.dto.AppointmentUpdateDto;

import java.util.List;

public interface AppointmentService {
    /**
     * Find appointment by id
     *
     * @param id Appointment id
     * @return AppointmentDto
     */
    AppointmentDto findAppointmentById(Long id);

    /**
     * Find all appointments
     *
     * @return List of AppointmentDto
     */
    List<AppointmentDto> findAllAppointments();

    /**
     * Create new appointment
     *
     * @param appointmentCreateDto Appointment create object
     * @return AppointmentDto
     */
    AppointmentDto createAppointment(AppointmentCreateDto appointmentCreateDto);

    /**
     * Update appointment
     *
     * @param id                   Appointment id
     * @param appointmentUpdateDto Appointment update object
     * @return AppointmentDto
     */
    AppointmentDto updateAppointment(Long id, AppointmentUpdateDto appointmentUpdateDto);

    /**
     * Delete appointment by id
     *
     * @param id Appointment id
     */
    void deleteAppointment(Long id);
}
