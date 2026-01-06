package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.AppointmentCreateDto;
import raf.aleksabuncic.dto.AppointmentDto;
import raf.aleksabuncic.dto.AppointmentUpdateDto;

public interface AppointmentService {
    AppointmentDto findAppointmentById(Long id);

    AppointmentDto createAppointment(AppointmentCreateDto appointmentCreateDto);

    AppointmentDto updateAppointment(Long id, AppointmentUpdateDto appointmentUpdateDto);

    void deleteAppointment(Long id);
}
