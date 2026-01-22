package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Appointment;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.domain.Veterinarian;

import java.util.Date;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByDateAndPatientAndVeterinarian(Date date, Patient patient, Veterinarian veterinarian);

}
