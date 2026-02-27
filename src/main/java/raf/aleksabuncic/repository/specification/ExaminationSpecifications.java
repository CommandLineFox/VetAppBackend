package raf.aleksabuncic.repository.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import raf.aleksabuncic.domain.Examination;
import raf.aleksabuncic.dto.ExaminationSearchDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ExaminationSpecifications {
    public static Specification<Examination> betweenDates(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) -> cb.between(root.get("date"), start, end);
    }

    public static Specification<Examination> onDate(LocalDate date) {
        return betweenDates(date.atStartOfDay(), date.atTime(LocalTime.MAX));
    }

    public static Specification<Examination> patientIdEqual(Long patientId) {
        return (root, query, cb) -> cb.equal(root.get("patient").get("id"), patientId);
    }

    public static Specification<Examination> veterinarianIdEqual(Long veterinarianId) {
        return (root, query, cb) -> cb.equal(root.get("veterinarian").get("id"), veterinarianId);
    }

    public static Specification<Examination> fieldLike(String fieldName, String text) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(fieldName)), "%" + text.toLowerCase() + "%");
    }

    public static Specification<Examination> build(ExaminationSearchDto examinationSearchDto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (examinationSearchDto == null) {
                return cb.conjunction();
            }

            if (examinationSearchDto.getDate() != null) {
                predicates.add(onDate(examinationSearchDto.getDate()).toPredicate(root, query, cb));
            } else {
                if (examinationSearchDto.getDateFrom() != null && examinationSearchDto.getDateTo() != null) {
                    predicates.add(betweenDates(examinationSearchDto.getDateFrom().atStartOfDay(), examinationSearchDto.getDateTo().atTime(LocalTime.MAX)).toPredicate(root, query, cb));
                } else if (examinationSearchDto.getDateFrom() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("date"), examinationSearchDto.getDateFrom().atStartOfDay()));
                } else if (examinationSearchDto.getDateTo() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("date"), examinationSearchDto.getDateTo().atTime(LocalTime.MAX)));
                }
            }

            addTextFieldIfPresent(predicates, "anamnesis", examinationSearchDto.getAnamnesis(), root, query, cb);
            addTextFieldIfPresent(predicates, "clinicalPresentation", examinationSearchDto.getClinicalPresentation(), root, query, cb);
            addTextFieldIfPresent(predicates, "diagnosis", examinationSearchDto.getDiagnosis(), root, query, cb);
            addTextFieldIfPresent(predicates, "treatment", examinationSearchDto.getTreatment(), root, query, cb);
            addTextFieldIfPresent(predicates, "laboratoryAnalysis", examinationSearchDto.getLaboratoryAnalysis(), root, query, cb);
            addTextFieldIfPresent(predicates, "specialistExamination", examinationSearchDto.getSpecialistExamination(), root, query, cb);
            addTextFieldIfPresent(predicates, "remarks", examinationSearchDto.getRemarks(), root, query, cb);

            if (examinationSearchDto.getPatientId() != null) {
                predicates.add(patientIdEqual(examinationSearchDto.getPatientId()).toPredicate(root, query, cb));
            }

            if (examinationSearchDto.getVeterinarianId() != null) {
                predicates.add(veterinarianIdEqual(examinationSearchDto.getVeterinarianId()).toPredicate(root, query, cb));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addTextFieldIfPresent(List<Predicate> predicates, String field, String value, Root<Examination> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (value != null && !value.isBlank()) {
            predicates.add(fieldLike(field, value).toPredicate(root, query, cb));
        }
    }
}