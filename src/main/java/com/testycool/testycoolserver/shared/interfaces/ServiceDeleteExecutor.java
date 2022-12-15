package com.testycool.testycoolserver.shared.interfaces;

public interface ServiceDeleteExecutor<T, ID> {
    ID delete(T item);
    ID deleteById(ID id);
}
