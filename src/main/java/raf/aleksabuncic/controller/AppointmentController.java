package raf.aleksabuncic.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.aleksabuncic.dto.AppointmentCreateDto;
import raf.aleksabuncic.dto.AppointmentDto;
import raf.aleksabuncic.dto.AppointmentUpdateDto;
import raf.aleksabuncic.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@Tag(name = "Appointment API", description = "API for managing appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('APPOINTMENT_LIST')")
    public ResponseEntity<AppointmentDto> findAppointmentById(@PathVariable Long id) {
        return new ResponseEntity<>(appointmentService.findAppointmentById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('APPOINTMENT_LIST')")
    public ResponseEntity<Iterable<AppointmentDto>> findAllAppointments() {
        return new ResponseEntity<>(appointmentService.findAllAppointments(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('APPOINTMENT_ADD')")
    public ResponseEntity<AppointmentDto> createAppointment(@Valid @RequestBody AppointmentCreateDto appointmentCreateDto) {
        return new ResponseEntity<>(appointmentService.createAppointment(appointmentCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('APPOINTMENT_UPDATE')")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentUpdateDto appointmentUpdateDto) {
        return new ResponseEntity<>(appointmentService.updateAppointment(id, appointmentUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('APPOINTMENT_DELETE')")
    public ResponseEntity<AppointmentDto> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
