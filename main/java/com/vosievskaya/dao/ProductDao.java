package com.vosievskaya.dao;

import com.vosievskaya.annotation.Table;
import com.vosievskaya.model.Category;
import com.vosievskaya.model.Product;

import java.sql.Connection;
import java.util.List;

import static com.vosievskaya.ConnectionFactory.getCategoryDao;
import static com.vosievskaya.ConnectionFactory.getConnection;

public class ProductDao extends AbstractDao<Product, Long> {

    public ProductDao(Connection connection) {
        super(connection);
    }

    @Table(name = "PRODUCTS")
    public Product getById(Long id) {
        return getById(id, "PRODUCTS");
    }

    @Table(name = "PRODUCTS")
    public List<Product> getAll() {
        return getAll("PRODUCTS");
    }

    @Table(name = "CATEGORIES")
    public List<Product> getProductsByCategoryId(Long id) {
        Category category = getCategoryDao(getConnection()).getById(id, "CATEGORIES");
        return category.getProducts();
    }
}
