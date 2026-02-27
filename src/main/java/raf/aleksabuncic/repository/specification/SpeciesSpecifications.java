package raf.aleksabuncic.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import raf.aleksabuncic.domain.Species;
import raf.aleksabuncic.dto.SpeciesSearchDto;

import java.util.ArrayList;
import java.util.List;

public class SpeciesSpecifications {
    public static Specification<Species> nameLike(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Species> build(SpeciesSearchDto speciesSearchDto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (speciesSearchDto == null) {
                return cb.conjunction();
            }

            if (speciesSearchDto.getName() != null && !speciesSearchDto.getName().isBlank()) {
                predicates.add(nameLike(speciesSearchDto.getName()).toPredicate(root, query, cb));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
