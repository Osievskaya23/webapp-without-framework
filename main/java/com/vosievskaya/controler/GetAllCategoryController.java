package com.vosievskaya.controler;

import com.vosievskaya.Request;
import com.vosievskaya.ViewModel;
import com.vosievskaya.service.CategoryService;

import javax.swing.text.View;

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
