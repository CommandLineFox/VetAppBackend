package raf.aleksabuncic.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import raf.aleksabuncic.domain.Breed;
import raf.aleksabuncic.dto.BreedSearchDto;

import java.util.ArrayList;
import java.util.List;

public class BreedSpecifications {
    public static Specification<Breed> nameLike(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Breed> speciesId(Long speciesId) {
        return (root, query, cb) -> cb.equal(root.get("species").get("id"), speciesId);
    }

    public static Specification<Breed> build(BreedSearchDto breedSearchDto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (breedSearchDto == null) {
                return cb.conjunction();
            }

            if (breedSearchDto.getName() != null && !breedSearchDto.getName().isBlank()) {
                predicates.add(nameLike(breedSearchDto.getName()).toPredicate(root, query, cb));
            }

            if (breedSearchDto.getSpeciesId() != null) {
                predicates.add(speciesId(breedSearchDto.getSpeciesId()).toPredicate(root, query, cb));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}