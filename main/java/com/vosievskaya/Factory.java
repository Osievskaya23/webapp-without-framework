package com.vosievskaya;

import com.vosievskaya.controller.GetAllCategoryController;
import com.vosievskaya.controller.GetCategoryByIdController;
import com.vosievskaya.controller.GetProductByIdController;
import com.vosievskaya.controller.LoginUserController;
import com.vosievskaya.dao.CategoryDao;
import com.vosievskaya.dao.CategoryDaoImpl;
import com.vosievskaya.dao.ProductDao;
import com.vosievskaya.dao.ProductDaoImpl;
import com.vosievskaya.dao.UserDao;
import com.vosievskaya.dao.UserDaoImpl;
import com.vosievskaya.model.Product;
import com.vosievskaya.service.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Factory {

    private static Connection connection;

    static {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/vosievskaya ", "sa", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static LoginUserController getLoginUserController(UserService userService) {
        return new LoginUserController(userService);
    }

    public static UserService getUserServiceImpl(UserDao userDao) {
        return new UserServiceImpl(userDao);
    }

    public static UserDao getUserDaoImpl(Connection connection) {
        return new UserDaoImpl(connection);
    }

    public static CategoryService getCategoryService(CategoryDao categoryDao) {
        return new CategoryServiceImpl(categoryDao);
    }

    public static CategoryDao getCategoryDao(Connection connection) {
        return new CategoryDaoImpl(connection);
    }

    public static GetAllCategoryController getAllCategoryController(CategoryService categoryService) {
        return new GetAllCategoryController(categoryService);
    }

    public static GetCategoryByIdController getCategoryByIdController(CategoryService categoryService) {
        return new GetCategoryByIdController(categoryService);
    }

    public static ProductService getProductService(ProductDao productDao) { return new ProductServiceImpl(productDao); }

    public static ProductDao getProductDao(Connection connection) {
        return new ProductDaoImpl(connection);
    }

    public static GetProductByIdController getProductByIdController(ProductService productService) {
        return new GetProductByIdController(productService);
    }
}
