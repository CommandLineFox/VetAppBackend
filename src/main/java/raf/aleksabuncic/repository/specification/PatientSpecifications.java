package raf.aleksabuncic.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import raf.aleksabuncic.domain.Patient;

public class PatientSpecifications {
    public static Specification<Patient> passportEqual(String passport) {
        return (root, query, cb) -> cb.equal(root.get("passportNumber"), passport);
    }

    public static Specification<Patient> microchipEqual(String microchip) {
        return (root, query, cb) -> cb.equal(root.get("microchipNumber"), microchip);
    }

    public static Specification<Patient> ownerId(Long ownerId) {
        return (root, query, cb) -> cb.equal(root.get("owner").get("id"), ownerId);
    }

    public static Specification<Patient> nameLike(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
