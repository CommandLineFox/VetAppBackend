package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
