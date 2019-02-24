package com.vosievskaya;

import com.vosievskaya.controler.GetAllCategoryController;
import com.vosievskaya.controler.GetCategoryByIdController;
import com.vosievskaya.controler.GetProductByIdController;
import com.vosievskaya.controler.LoginUserController;
import com.vosievskaya.service.*;

public class Factory {

    public static LoginUserController getLoginUserController(UserService userService) {
        return new LoginUserController(userService);
    }

    public static UserService getUserServiceImpl() {
        return new UserServiceImpl();
    }

    public static CategoryService getCategoryService() {
        return new CategoryServiceImpl();
    }

    public static GetAllCategoryController getAllCategoryController(CategoryService categoryService) {
        return new GetAllCategoryController(categoryService);
    }

    public static GetCategoryByIdController getCategoryByIdController(CategoryService categoryService) {
        return new GetCategoryByIdController(categoryService);
    }

    public static ProductService getProductService() { return new ProductServiceImpl(); }

    public static GetProductByIdController getProductByIdController(ProductService productService) {
        return new GetProductByIdController(productService);
    }
}
