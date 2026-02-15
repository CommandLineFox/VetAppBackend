package raf.aleksabuncic.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Veterinarian;

import java.util.Optional;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {
    Optional<Veterinarian> findByLicenseNumber(Integer licenseNumber);

    Optional<Veterinarian> findByEmail(String email);

    Slice<Veterinarian> findBy(Pageable pageable);

    boolean existsByLicenseNumber(Integer licenseNumber);
}
