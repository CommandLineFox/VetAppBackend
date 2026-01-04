package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.AppointmentRequestDto;
import raf.aleksabuncic.dto.AppointmentDto;

public interface AppointmentService {
    AppointmentDto findAppointmentById(Long id);

    AppointmentDto createAppointment(AppointmentRequestDto appointmentRequestDto);

    AppointmentDto updateAppointment(AppointmentRequestDto appointmentRequestDto);

    void deleteAppointment(Long id);
}
