package raf.aleksabuncic.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.VeterinarianSearchDto;

import java.util.ArrayList;
import java.util.List;

public class VeterinarianSpecifications {
    public static Specification<Veterinarian> licenseEqual(Integer license) {
        return (root, query, cb) -> cb.equal(root.get("licenseNumber"), license);
    }

    public static Specification<Veterinarian> firstNameLike(String firstName) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<Veterinarian> lastNameLike(String lastName) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    public static Specification<Veterinarian> build(VeterinarianSearchDto veterinarianSearchDto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (veterinarianSearchDto == null) {
                return cb.conjunction();
            }

            if (veterinarianSearchDto.getFirstName() != null && !veterinarianSearchDto.getFirstName().isBlank()) {
                predicates.add(firstNameLike(veterinarianSearchDto.getFirstName()).toPredicate(root, query, cb));
            }

            if (veterinarianSearchDto.getLastName() != null && !veterinarianSearchDto.getLastName().isBlank()) {
                predicates.add(lastNameLike(veterinarianSearchDto.getLastName()).toPredicate(root, query, cb));
            }

            if (veterinarianSearchDto.getLicenseNumber() != null) {
                predicates.add(licenseEqual(veterinarianSearchDto.getLicenseNumber()).toPredicate(root, query, cb));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
