package com.vosievskaya.service;

import com.vosievskaya.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAll();

    Optional<Product> getProductById(Long id);
}
