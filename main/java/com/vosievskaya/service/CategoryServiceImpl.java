package com.vosievskaya.service;

import com.vosievskaya.model.Category;
import com.vosievskaya.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private static List<Category> categories = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();

    static {
        Product product = new Product(1L, "iPhone", "Apple product", 999.99);
        Product product2 = new Product(2L, "Samsung", "Korean product", 799.99);
        Product product3 = new Product(3L, "Xiomi", "Mi A1 product", 899.99);

        products.add(product);
        products.add(product2);
        products.add(product3);

        Category category = new Category(1L,"MobilePhone", "Best mobile phone in Ukraine");
        category.setProducts(products);

        categories.add(category);
        categories.add(new Category(2L, "Laptop", "Fast"));
        categories.add(new Category(3L, "Earphones", "Original iPhone"));
    }

    @Override
    public List<Category> getAll() {
        return categories;
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categories.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public List<Product> getAllProducts() {
        return products;
    }
}
