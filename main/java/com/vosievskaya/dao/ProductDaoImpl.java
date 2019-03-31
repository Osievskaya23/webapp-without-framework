package com.vosievskaya.dao;

import com.vosievskaya.model.Category;
import com.vosievskaya.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*public class ProductDaoImpl implements ProductDao {

    private final Connection connection;

    public ProductDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Product getProductById(Long id) {
        String query =
                "SELECT P.ID AS P_ID, " +
                        "P.PRODUCTS_NAME AS P_NAME, " +
                        "P.PRODUCTS_DESCRIPTION AS P_DESC, " +
                        "P.PRICE AS P_PRICE FROM CATEGORIES C " +
                        "JOIN PRODUCTS P ON C.ID = P.FK_CATEGORY_ID " +
                        "WHERE C.ID = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Product result = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            result = getProductFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private Product getProductFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("P_ID");
        String productName = rs.getString("P_NAME");
        String productDesc = rs.getString("P_DESC");
        double price = rs.getDouble("P_PRICE");
        return new Product(id, productName, productDesc, price);
    }
}*/
