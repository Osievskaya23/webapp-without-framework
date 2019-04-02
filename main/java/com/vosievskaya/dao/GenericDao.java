package com.vosievskaya.dao;

import java.util.List;

public interface GenericDao<T, ID> { // TODO ADD ID

    T create(T t, String tableName);

    T getById(ID id, String tableName);

    List<T> getAll(String tableName);

    T update(T t, String tableName);

    void delete (ID id, String tableName);
}
