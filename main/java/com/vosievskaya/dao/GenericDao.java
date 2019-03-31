package com.vosievskaya.dao;

import java.util.List;

public interface GenericDao<T, ID> { // TODO ADD ID

    T create(T t);

    T getById(ID id);

    List<T> getAll();

    T update(T t);

    void delete (ID id);
}
