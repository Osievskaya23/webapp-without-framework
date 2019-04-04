package com.vosievskaya.service;

import com.vosievskaya.annotation.TableAnnotation;
import com.vosievskaya.dao.ProductDao;
import com.vosievskaya.model.Product;

import java.util.List;
import java.util.Optional;

@TableAnnotation(tableName = "PRODUCTS")
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll("PRODUCTS");
    }

    @Override
    public Optional<Product> getById(Long id) {
        return Optional.ofNullable(productDao.getById(id, "PRODUCTS"));
    }
}
