package raf.aleksabuncic.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Examination;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.domain.Veterinarian;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {
    Slice<Examination> findBy(Pageable pageable);

    Optional<Examination> findByDateAndPatientAndVeterinarian(LocalDateTime date, Patient patient, Veterinarian veterinarian);
}
