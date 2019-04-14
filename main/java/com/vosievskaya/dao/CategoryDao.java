package com.vosievskaya.dao;

import static com.vosievskaya.ConnectionFactory.getConnection;
import static com.vosievskaya.ConnectionFactory.getProductDao;

import com.vosievskaya.annotation.Table;
import com.vosievskaya.model.Category;
import com.vosievskaya.model.Product;

import java.sql.Connection;
import java.util.List;


public class CategoryDao extends AbstractDao<Category, Long> {

    public CategoryDao(Connection connection) {
        super(connection);
    }

    @Table(name = "CATEGORIES")
    public  Category getById(Long id) {
        Category category = getById(id, "CATEGORIES");
        List<Product> products = getProductDao(getConnection()).getProductsByCategoryId(id);
        category.setProducts(products);
        return category;
    }
}
