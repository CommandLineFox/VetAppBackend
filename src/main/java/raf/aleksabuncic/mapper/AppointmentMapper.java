package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Appointment;
import raf.aleksabuncic.dto.AppointmentDto;

@Component
public class AppointmentMapper {
    public AppointmentDto appointmentToAppointmentDto(Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();

        appointmentDto.setId(appointment.getId());
        appointmentDto.setDate(appointment.getDate());
        appointmentDto.setPatientId(appointment.getPatient().getId());
        appointmentDto.setVeterinarianId(appointment.getVeterinarian().getId());

        return appointmentDto;
    }
}
