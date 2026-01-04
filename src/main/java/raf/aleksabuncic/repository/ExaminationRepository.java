package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.aleksabuncic.domain.Examination;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {
}
