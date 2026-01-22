package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Breed;

import java.util.Optional;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {
    Optional<Breed> findByName(String name);
}
