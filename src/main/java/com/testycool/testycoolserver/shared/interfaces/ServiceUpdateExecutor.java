package com.testycool.testycoolserver.shared.interfaces;

public interface ServiceUpdateExecutor<T, ID> {
    T update(ID id, T item);
}
