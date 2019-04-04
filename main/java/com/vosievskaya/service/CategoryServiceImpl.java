package com.vosievskaya.service;

import com.vosievskaya.annotation.TableAnnotation;
import com.vosievskaya.dao.CategoryDao;
import com.vosievskaya.model.Category;

import java.util.List;
import java.util.Optional;

@TableAnnotation(tableName = "CATEGORIES")
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll("CATEGORIES");
    }

    @Override

    public Optional<Category> getById(Long id) {
        return Optional.ofNullable(categoryDao.getById(id, "CATEGORIES"));
    }
}
