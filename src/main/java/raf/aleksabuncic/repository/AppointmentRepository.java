package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}
