package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Owner;
import raf.aleksabuncic.domain.Patient;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByName(String name);

    Optional<Patient> findByPassportNumber(String passportNumber);

    Optional<Patient> findByMicrochipNumber(String microchipNumber);

    Optional<Patient> findByOwner(Owner owner);
}
