package com.vosievskaya.dao;

import com.vosievskaya.model.Category;

import java.sql.Connection;

public class CategoryDao extends AbstractDao<Category, Long> {

    public CategoryDao(Connection connection) {
        super(connection);
    }
}
