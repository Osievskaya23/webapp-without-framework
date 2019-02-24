package com.vosievskaya.service;

import com.vosievskaya.model.Product;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private static final List<Product> products;

    static {
        CategoryService categoryService = new CategoryServiceImpl();
        products = ((CategoryServiceImpl) categoryService).getAllProducts();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
}
