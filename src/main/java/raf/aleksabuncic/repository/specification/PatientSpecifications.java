package raf.aleksabuncic.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.dto.PatientSearchDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientSpecifications {
    public static Specification<Patient> nameLike(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Patient> passportEqual(String passport) {
        return (root, query, cb) -> cb.equal(root.get("passportNumber"), passport);
    }

    public static Specification<Patient> microchipEqual(String microchip) {
        return (root, query, cb) -> cb.equal(root.get("microchipNumber"), microchip);
    }

    public static Specification<Patient> ownerIdEqual(Long ownerId) {
        return (root, query, cb) -> cb.equal(root.get("owner").get("id"), ownerId);
    }

    public static Specification<Patient> breedIdEqual(Long breedId) {
        return (root, query, cb) -> cb.equal(root.get("breed").get("id"), breedId);
    }

    public static Specification<Patient> birthDateEqual(LocalDate date) {
        return (root, query, cb) -> cb.equal(root.get("birthDate"), date);
    }

    public static Specification<Patient> birthDateBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> cb.between(root.get("birthDate"), start, end);
    }

    public static Specification<Patient> birthDateAfter(LocalDate date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("birthDate"), date);
    }

    public static Specification<Patient> birthDateBefore(LocalDate date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("birthDate"), date);
    }

    public static Specification<Patient> build(PatientSearchDto patientSearchDto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (patientSearchDto == null) {
                return cb.conjunction();
            }

            if (patientSearchDto.getBirthDate() != null) {
                predicates.add(birthDateEqual(patientSearchDto.getBirthDate()).toPredicate(root, query, cb));
            } else {
                if (patientSearchDto.getBirthDateFrom() != null && patientSearchDto.getBirthDateTo() != null) {
                    {
                        predicates.add(birthDateBetween(patientSearchDto.getBirthDateFrom(), patientSearchDto.getBirthDateTo()).toPredicate(root, query, cb));
                    }
                } else if (patientSearchDto.getBirthDateFrom() != null) {
                    {
                        predicates.add(birthDateAfter(patientSearchDto.getBirthDateFrom()).toPredicate(root, query, cb));
                    }
                } else if (patientSearchDto.getBirthDateTo() != null) {
                    {
                        predicates.add(birthDateBefore(patientSearchDto.getBirthDateTo()).toPredicate(root, query, cb));
                    }
                }
            }

            if (patientSearchDto.getName() != null && !patientSearchDto.getName().isBlank()) {
                predicates.add(nameLike(patientSearchDto.getName()).toPredicate(root, query, cb));
            }

            if (patientSearchDto.getPassportNumber() != null && !patientSearchDto.getPassportNumber().isBlank()) {
                predicates.add(passportEqual(patientSearchDto.getPassportNumber()).toPredicate(root, query, cb));
            }

            if (patientSearchDto.getMicrochipNumber() != null && !patientSearchDto.getMicrochipNumber().isBlank()) {
                predicates.add(microchipEqual(patientSearchDto.getMicrochipNumber()).toPredicate(root, query, cb));
            }

            if (patientSearchDto.getOwnerId() != null) {
                predicates.add(ownerIdEqual(patientSearchDto.getOwnerId()).toPredicate(root, query, cb));
            }

            if (patientSearchDto.getBreedId() != null) {
                predicates.add(breedIdEqual(patientSearchDto.getBreedId()).toPredicate(root, query, cb));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}