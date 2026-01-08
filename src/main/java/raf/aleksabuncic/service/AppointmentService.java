package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.AppointmentCreateDto;
import raf.aleksabuncic.dto.AppointmentDto;
import raf.aleksabuncic.dto.AppointmentUpdateDto;

import java.util.List;

public interface AppointmentService {
    AppointmentDto findAppointmentById(Long id);

    List<AppointmentDto> findAllAppointments();

    AppointmentDto createAppointment(AppointmentCreateDto appointmentCreateDto);

    AppointmentDto updateAppointment(Long id, AppointmentUpdateDto appointmentUpdateDto);

    void deleteAppointment(Long id);
}
