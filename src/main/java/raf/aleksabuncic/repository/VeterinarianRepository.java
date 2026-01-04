package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Veterinarian;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {
}
