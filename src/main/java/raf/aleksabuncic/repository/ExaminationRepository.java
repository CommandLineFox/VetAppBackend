package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Examination;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.domain.Veterinarian;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {
    Optional<Examination> findByDateAndPatientAndVeterinarian(Date date, Patient patient, Veterinarian veterinarian);
}
