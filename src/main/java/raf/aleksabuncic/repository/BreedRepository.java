package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Breed;

import java.util.Optional;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long>, JpaSpecificationExecutor<Breed> {
    Optional<Breed> findByName(String name);

    boolean existsByName(String name);
}
