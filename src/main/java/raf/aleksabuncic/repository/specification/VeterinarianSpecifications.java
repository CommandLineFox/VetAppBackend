package raf.aleksabuncic.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import raf.aleksabuncic.domain.Veterinarian;

public class VeterinarianSpecifications {
    public static Specification<Veterinarian> licenseEqual(Integer license) {
        return (root, query, cb) -> cb.equal(root.get("licenseNumber"), license);
    }

    public static Specification<Veterinarian> lastNameLike(String lastName) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    public static Specification<Veterinarian> emailEqual(String email) {
        return (root, query, cb) -> cb.equal(root.get("email"), email);
    }
}
