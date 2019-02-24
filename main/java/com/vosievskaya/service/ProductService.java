package com.vosievskaya.service;

import com.vosievskaya.model.Product;

import java.util.Optional;

public interface ProductService {

    Optional<Product> getProductById(Long id);
}
