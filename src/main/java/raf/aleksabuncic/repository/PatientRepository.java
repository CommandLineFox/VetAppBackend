package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Patient;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {
    Optional<Patient> findByName(String name);

    boolean existsByPassportNumberOrMicrochipNumberOrCartonNumber(String passportNumber, String microchipNumber, Integer cartonNumber);
}
