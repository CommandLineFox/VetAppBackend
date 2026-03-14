package raf.aleksabuncic.mapper;

import org.mapstruct.*;
import raf.aleksabuncic.domain.Appointment;
import raf.aleksabuncic.dto.AppointmentCreateDto;
import raf.aleksabuncic.dto.AppointmentDto;
import raf.aleksabuncic.dto.AppointmentUpdateDto;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentDto appointmentToAppointmentDto(Appointment appointment);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "patientId", target = "patient.id")
    @Mapping(source = "veterinarianId", target = "veterinarian.id")
    Appointment appointmentCreateDtoToAppointment(AppointmentCreateDto appointmentCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "veterinarian", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void appointmentUpdateDtoToAppointment(AppointmentUpdateDto appointmentUpdateDto, @MappingTarget Appointment appointment);
}
