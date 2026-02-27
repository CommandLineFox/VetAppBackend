package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Appointment;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.domain.Veterinarian;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {
    Optional<Appointment> findByDateAndPatientAndVeterinarian(LocalDateTime date, Patient patient, Veterinarian veterinarian);
}
