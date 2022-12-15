package com.testycool.testycoolserver.shared.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public interface ServiceGetExecutor<T, ID> {
    T getById(ID id);
    Collection<T> getAll();
    Page<T> getAll(Pageable pageable);
    Page<T> getAll(Specification<T> specification, Pageable pageable);
}
