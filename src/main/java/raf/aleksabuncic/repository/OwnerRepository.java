package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Owner;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long>, JpaSpecificationExecutor<Owner> {
    Optional<Owner> findByJmbg(String jmbg);

    boolean existsByJmbgOrEmailOrPhoneNumber(String jmbg, String email, String phoneNumber);
}
