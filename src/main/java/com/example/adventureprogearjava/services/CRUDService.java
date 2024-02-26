package com.example.adventureprogearjava.services;

import java.util.List;

public interface CRUDService <T> {
    List<T> getAll();

    T getById(Long id);

    T create(T t);

    void update(T t, Long id);

    void delete (Long id);
}
