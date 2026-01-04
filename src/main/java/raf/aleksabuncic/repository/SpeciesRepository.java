package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Species;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Integer> {
}
