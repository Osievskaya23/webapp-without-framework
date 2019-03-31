package com.vosievskaya;

import com.vosievskaya.model.Category;
import com.vosievskaya.model.Product;
import com.vosievskaya.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBEmulator {

    private static List<Category> categories = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();

    public static List<User> getUsers() {
        return users;
    }

    private static List<User> users = new ArrayList<>();

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

        /*users.add(new User(
                1L,
                "osievskaya23",
                "96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e",
                "80d210ec-901c-48b7-9d78-184387cb714e",
                "Valentina",
                "Osievskaya"));*/
    }

    public static List<Category> getCategories() {
        return categories;
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static void addUser(User user) {
        users.add(user);
    }
}
