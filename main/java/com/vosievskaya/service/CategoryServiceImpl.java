package com.vosievskaya.service;

import com.vosievskaya.dao.CategoryDao;
import com.vosievskaya.model.Category;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return Optional.ofNullable(categoryDao.getById(id));
    }

}
