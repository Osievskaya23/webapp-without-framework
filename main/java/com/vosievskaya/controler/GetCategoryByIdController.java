package com.vosievskaya.controler;

import com.vosievskaya.Request;
import com.vosievskaya.ViewModel;
import com.vosievskaya.service.CategoryService;

import java.util.Collections;

public class GetCategoryByIdController implements Controller {

    private final CategoryService categoryService;

    public GetCategoryByIdController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ViewModel process(Request req) {
        String param = req.getParam("c_id")[0];
        Long id = Long.parseLong(param);
        return categoryService.getCategoryById(id)
                .map(c -> ViewModel.of("category").withAttribute("category", c))
                .orElseGet(() -> ViewModel.of("category").withAttribute("category", Collections.emptyList()));
    }
}
