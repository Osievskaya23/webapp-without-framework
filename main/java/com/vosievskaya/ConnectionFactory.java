package com.vosievskaya;

import com.vosievskaya.controller.GetAllCategoryController;
import com.vosievskaya.controller.GetCategoryByIdController;
import com.vosievskaya.controller.GetProductByIdController;
import com.vosievskaya.controller.LoginUserController;
import com.vosievskaya.dao.CategoryDao;
import com.vosievskaya.dao.ProductDao;
import com.vosievskaya.dao.UserDao;
import com.vosievskaya.service.CategoryService;
import com.vosievskaya.service.CategoryServiceImpl;
import com.vosievskaya.service.ProductService;
import com.vosievskaya.service.ProductServiceImpl;
import com.vosievskaya.service.UserService;
import com.vosievskaya.service.UserServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:tcp://localhost/~/vosievskaya ";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public static LoginUserController getLoginUserController(UserService userService) {
        return new LoginUserController(userService);
    }

    public static UserService getUserServiceImpl(UserDao userDao) {
        return new UserServiceImpl(userDao);
    }

    public static UserDao getUserDao(Connection connection) {
        return new UserDao(connection);
    }

    public static CategoryService getCategoryService(CategoryDao categoryDao) {
        return new CategoryServiceImpl(categoryDao);
    }

    public static CategoryDao getCategoryDao(Connection connection) {
        return new CategoryDao(connection);
    }

    public static GetAllCategoryController getAllCategoryController(CategoryService categoryService) {
        return new GetAllCategoryController(categoryService);
    }

    public static GetCategoryByIdController getCategoryByIdController(CategoryService categoryService) {
        return new GetCategoryByIdController(categoryService);
    }

    public static ProductService getProductService(ProductDao productDao) { return new ProductServiceImpl(productDao); }

    public static ProductDao getProductDao(Connection connection) {
        return new ProductDao(connection);
    }

    public static GetProductByIdController getProductByIdController(ProductService productService) {
        return new GetProductByIdController(productService);
    }
}
