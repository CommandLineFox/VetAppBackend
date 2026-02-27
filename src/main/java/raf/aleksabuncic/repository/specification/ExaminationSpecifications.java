package raf.aleksabuncic.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import raf.aleksabuncic.domain.Examination;

import java.time.LocalDateTime;

public class ExaminationSpecifications {
    public static Specification<Examination> betweenDates(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) -> cb.between(root.get("date"), start, end);
    }

    public static Specification<Examination> patientId(Long patientId) {
        return (root, query, cb) -> cb.equal(root.get("patient").get("id"), patientId);
    }

    public static Specification<Examination> diagnosisContains(String text) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("diagnosis")), "%" + text.toLowerCase() + "%");
    }
}