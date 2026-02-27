package raf.aleksabuncic.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import raf.aleksabuncic.domain.Appointment;

import java.time.LocalDateTime;

public class AppointmentSpecifications {
    public static Specification<Appointment> betweenDates(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) -> cb.between(root.get("date"), start, end);
    }

    public static Specification<Appointment> onDay(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return (root, query, cb) -> cb.between(root.get("date"), startOfDay, endOfDay);
    }

    public static Specification<Appointment> dateGreaterThan(LocalDateTime date) {
        return (root, query, cb) -> cb.greaterThan(root.get("date"), date);
    }

    public static Specification<Appointment> veterinarianLicense(Integer license) {
        return (root, query, cb) -> cb.equal(root.get("veterinarian").get("licenseNumber"), license);
    }

    public static Specification<Appointment> patientId(Long patientId) {
        return (root, query, cb) -> cb.equal(root.get("patient").get("id"), patientId);
    }
}