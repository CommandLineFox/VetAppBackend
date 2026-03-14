package raf.aleksabuncic.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import raf.aleksabuncic.domain.Appointment;
import raf.aleksabuncic.dto.AppointmentSearchDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentSpecifications {
    public static Specification<Appointment> betweenDates(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) -> cb.between(root.get("date"), start, end);
    }

    public static Specification<Appointment> onDate(LocalDate date) {
        return betweenDates(date.atStartOfDay(), date.atTime(LocalTime.MAX));
    }

    public static Specification<Appointment> dateAfterOrEqual(LocalDateTime date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), date);
    }

    public static Specification<Appointment> dateBeforeOrEqual(LocalDateTime date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), date);
    }

    public static Specification<Appointment> descriptionLike(String description) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%");
    }

    public static Specification<Appointment> veterinarianIdEqual(Long veterinarianId) {
        return (root, query, cb) -> cb.equal(root.get("veterinarian").get("id"), veterinarianId);
    }

    public static Specification<Appointment> patientIdEqual(Long patientId) {
        return (root, query, cb) -> cb.equal(root.get("patient").get("id"), patientId);
    }

    public static Specification<Appointment> build(AppointmentSearchDto appointmentSearchDto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (appointmentSearchDto == null) {
                return cb.conjunction();
            }

            if (appointmentSearchDto.getDate() != null) {
                predicates.add(onDate(appointmentSearchDto.getDate()).toPredicate(root, query, cb));
            } else {
                if (appointmentSearchDto.getDateFrom() != null && appointmentSearchDto.getDateTo() != null) {
                    predicates.add(betweenDates(appointmentSearchDto.getDateFrom().atStartOfDay(), appointmentSearchDto.getDateTo().atTime(LocalTime.MAX)).toPredicate(root, query, cb));
                } else if (appointmentSearchDto.getDateFrom() != null) {
                    predicates.add(dateAfterOrEqual(appointmentSearchDto.getDateFrom().atStartOfDay()).toPredicate(root, query, cb));
                } else if (appointmentSearchDto.getDateTo() != null) {
                    predicates.add(dateBeforeOrEqual(appointmentSearchDto.getDateTo().atTime(LocalTime.MAX)).toPredicate(root, query, cb));
                }
            }

            if (appointmentSearchDto.getDescription() != null && !appointmentSearchDto.getDescription().isBlank()) {
                predicates.add(descriptionLike(appointmentSearchDto.getDescription()).toPredicate(root, query, cb));
            }

            if (appointmentSearchDto.getPatientId() != null) {
                predicates.add(patientIdEqual(appointmentSearchDto.getPatientId()).toPredicate(root, query, cb));
            }

            if (appointmentSearchDto.getVeterinarianId() != null) {
                predicates.add(veterinarianIdEqual(appointmentSearchDto.getVeterinarianId()).toPredicate(root, query, cb));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}