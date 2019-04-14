package com.vosievskaya.service;

import com.vosievskaya.annotation.Table;
import com.vosievskaya.dao.ProductDao;
import com.vosievskaya.model.Product;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    @Table(name = "PRODUCTS")
    public List<Product> getAll() {
        return productDao.getAll("PRODUCTS");
    }

    @Override
    @Table(name = "PRODUCTS")
    public Optional<Product> getById(Long id) {
        return Optional.ofNullable(productDao.getById(id, "PRODUCTS"));
    }
}
