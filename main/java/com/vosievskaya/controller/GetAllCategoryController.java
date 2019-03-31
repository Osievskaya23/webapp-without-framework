package com.vosievskaya.controller;

import com.vosievskaya.web.Request;
import com.vosievskaya.web.ViewModel;
import com.vosievskaya.service.CategoryService;

public class GetAllCategoryController implements Controller {

    private CategoryService categoryService;

    public GetAllCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ViewModel process(Request req) {
        return ViewModel.of("categories").withAttribute("categories", categoryService.getAll());
    }
}
