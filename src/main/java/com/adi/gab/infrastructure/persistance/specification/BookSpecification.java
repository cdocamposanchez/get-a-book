package com.adi.gab.infrastructure.persistance.specification;

import com.adi.gab.application.dto.request.BookFilterRequest;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;



public class BookSpecification {

    private BookSpecification() {}

    public static Specification<BookEntity> withFilters(BookFilterRequest filter) {
        return (root, _, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getCategory() != null && !filter.getCategory().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("categories")), "%" + filter.getCategory().toLowerCase() + "%"));
            }

            if (filter.getPublisher() != null && !filter.getPublisher().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("publisher")), "%" + filter.getPublisher().toLowerCase() + "%"));
            }

            if (filter.getYear() != null) {
                predicates.add(cb.equal(root.get("year"), filter.getYear()));
            }

            if (filter.getTitleRegex() != null && !filter.getTitleRegex().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + filter.getTitleRegex().toLowerCase() + "%"));
            }



            filterPrice(filter, predicates, cb, root);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void filterPrice(BookFilterRequest filter, List<Predicate> predicates, CriteriaBuilder cb, Root<BookEntity> root) {
        final String PRICE = "price";

        if (filter.getMinPrice() != null && filter.getMaxPrice() != null) {
            predicates.add(cb.between(root.get(PRICE), filter.getMinPrice(), filter.getMaxPrice()));
        } else if (filter.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(PRICE), filter.getMinPrice()));
        } else if (filter.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(PRICE), filter.getMaxPrice()));
        }
    }
}
