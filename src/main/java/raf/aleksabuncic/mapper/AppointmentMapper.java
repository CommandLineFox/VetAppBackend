package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Appointment;
import raf.aleksabuncic.dto.AppointmentCreateDto;
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

    public Appointment appointmentCreateDtoToAppointment(AppointmentCreateDto appointmentCreateDto) {
        Appointment appointment = new Appointment();

        appointment.setDate(appointmentCreateDto.getDate());

        return appointment;
    }
}
