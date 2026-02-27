package raf.aleksabuncic.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import raf.aleksabuncic.domain.Owner;
import raf.aleksabuncic.dto.OwnerSearchDto;

import java.util.ArrayList;
import java.util.List;

public class OwnerSpecifications {
    public static Specification<Owner> emailEqual(String email) {
        return (root, query, cb) -> cb.equal(root.get("email"), email);
    }

    public static Specification<Owner> jmbgEqual(String jmbg) {
        return (root, query, cb) -> cb.equal(root.get("jmbg"), jmbg);
    }

    public static Specification<Owner> firstNameLike(String firstName) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<Owner> lastNameLike(String lastName) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    public static Specification<Owner> phoneLike(String phone) {
        return (root, query, cb) -> cb.like(root.get("phoneNumber"), "%" + phone + "%");
    }

    public static Specification<Owner> addressLike(String address) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%");
    }

    public static Specification<Owner> build(OwnerSearchDto ownerSearchDto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ownerSearchDto == null) {
                return cb.conjunction();
            }

            if (ownerSearchDto.getFirstName() != null && !ownerSearchDto.getFirstName().isBlank()) {
                predicates.add(firstNameLike(ownerSearchDto.getFirstName()).toPredicate(root, query, cb));
            }

            if (ownerSearchDto.getLastName() != null && !ownerSearchDto.getLastName().isBlank()) {
                predicates.add(lastNameLike(ownerSearchDto.getLastName()).toPredicate(root, query, cb));
            }

            if (ownerSearchDto.getEmail() != null && !ownerSearchDto.getEmail().isBlank()) {
                predicates.add(emailEqual(ownerSearchDto.getEmail()).toPredicate(root, query, cb));
            }

            if (ownerSearchDto.getJmbg() != null && !ownerSearchDto.getJmbg().isBlank()) {
                predicates.add(jmbgEqual(ownerSearchDto.getJmbg()).toPredicate(root, query, cb));
            }

            if (ownerSearchDto.getPhoneNumber() != null && !ownerSearchDto.getPhoneNumber().isBlank()) {
                predicates.add(phoneLike(ownerSearchDto.getPhoneNumber()).toPredicate(root, query, cb));
            }

            if (ownerSearchDto.getAddress() != null && !ownerSearchDto.getAddress().isBlank()) {
                predicates.add(addressLike(ownerSearchDto.getAddress()).toPredicate(root, query, cb));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}