package com.testycool.testycoolserver.shared.classes;

import com.testycool.testycoolserver.shared.constants.SearchOperation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.BiFunction;

public class GenericSpecificationBuilder<T> {

    public Specification<T> build(
            List<SearchCriteria> searchCriteria,
            BiFunction<Root<T>, SearchCriteria, Path<String>> pathBiFunction
    ) {
        Specification<T> specs = new GenericSpecification<>(searchCriteria.get(0), (pathBiFunction));

        for (int i = 1; i < searchCriteria.size(); i++) {
            SearchCriteria criterion = searchCriteria.get(i);
            GenericSpecification<T> newSpecs = new GenericSpecification<>(criterion, pathBiFunction);
            if (criterion.getSearchOperation().equals(SearchOperation.AND)) {
                specs = Specification.where(specs).and(newSpecs);
            } else {
                specs = Specification.where(specs).or(newSpecs);
            }
        }

        return specs;
    }

}
