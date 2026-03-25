package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Veterinarian;

import java.util.Optional;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long>, JpaSpecificationExecutor<Veterinarian> {
    Optional<Veterinarian> findByLicenseNumber(Integer licenseNumber);

    Optional<Veterinarian> findByEmail(String email);

    boolean existsByLicenseNumber(Integer licenseNumber);

    boolean existsByLicenseNumberAndIdNot(Integer licenseNumber, Long id);
}
