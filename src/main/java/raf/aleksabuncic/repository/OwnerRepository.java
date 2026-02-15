package raf.aleksabuncic.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Owner;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByJmbg(String jmbg);

    Slice<Owner> findBy(Pageable pageable);

    boolean existsByJmbgOrEmailOrPhoneNumber(String jmbg, String email, String phoneNumber);
}
