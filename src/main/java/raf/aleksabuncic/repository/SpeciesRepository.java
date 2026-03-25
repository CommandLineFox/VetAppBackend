package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Species;

import java.util.Optional;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long>, JpaSpecificationExecutor<Species> {
    Optional<Species> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
