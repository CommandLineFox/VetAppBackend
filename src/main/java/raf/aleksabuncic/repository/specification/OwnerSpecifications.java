package raf.aleksabuncic.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import raf.aleksabuncic.domain.Owner;

public class OwnerSpecifications {
    public static Specification<Owner> emailEqual(String email) {
        return (root, query, cb) -> cb.equal(root.get("email"), email);
    }

    public static Specification<Owner> jmbgEqual(String jmbg) {
        return (root, query, cb) -> cb.equal(root.get("jmbg"), jmbg);
    }

    public static Specification<Owner> fullNameLike(String firstName, String lastName) {
        return (root, query, cb) -> {
            Predicate p1 = cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
            Predicate p2 = cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
            return cb.and(p1, p2);
        };
    }

    public static Specification<Owner> phoneLike(String phone) {
        return (root, query, cb) -> cb.like(root.get("phoneNumber"), "%" + phone + "%");
    }
}
