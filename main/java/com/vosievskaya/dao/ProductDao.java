package com.vosievskaya.dao;

import com.vosievskaya.model.Product;

import java.sql.Connection;

public class ProductDao extends AbstractDao<Product, Long> {

    public ProductDao(Connection connection) {
        super(connection);
    }
}
