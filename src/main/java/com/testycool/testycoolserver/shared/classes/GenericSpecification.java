package com.testycool.testycoolserver.shared.classes;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.function.BiFunction;

public class GenericSpecification<T> implements Specification<T> {
    SearchCriteria searchCriteria;
    BiFunction<Root<T>, SearchCriteria, Path<String>> pathBiFunction;

    public GenericSpecification(
            SearchCriteria searchCriteria,
            BiFunction<Root<T>, SearchCriteria, Path<String>> pathBiFunction
    ) {
        this.searchCriteria = searchCriteria;
        this.pathBiFunction = pathBiFunction;
    }
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Path<String> path = pathBiFunction.apply(root, searchCriteria);
        switch (searchCriteria.getQueryOperator()) {
            default:
            case EQUALS:
                if (searchCriteria.getValue() instanceof String) {
                    return cb.equal(path, "%" + searchCriteria.getValue() + "%");
                }
                return cb.equal(path, searchCriteria.getValue());
            case DOES_NOT_EQUAL:
                if (searchCriteria.getValue() instanceof String) {
                    return cb.notEqual(path, "%" + searchCriteria.getValue() + "%");
                }
                return cb.notEqual(path, searchCriteria.getValue());
            case CONTAINS:
                return cb.like(cb.lower(path), "%" + ((String) searchCriteria.getValue()).toLowerCase() + "%");
            case DOES_NOT_CONTAIN:
                return cb.notLike(cb.lower(path), "%" + ((String) searchCriteria.getValue()).toLowerCase() + "%");
            case LESS_THAN:
                return cb.lessThan(path, searchCriteria.getValue().toString());
            case LESS_THAN_EQUAL:
                return cb.lessThanOrEqualTo(path, searchCriteria.getValue().toString());
            case GREATER_THAN:
                return cb.greaterThan(path, searchCriteria.getValue().toString());
            case GREATER_THAN_EQUAL:
                return cb.greaterThanOrEqualTo(path, searchCriteria.getValue().toString());
        }
    }
}
