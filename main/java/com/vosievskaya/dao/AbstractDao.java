package com.vosievskaya.dao;

import static java.lang.String.format;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractDao<T, ID> implements GenericDao<T, ID>{

    private final Class<T> clazz;
    private Connection connection;

    public AbstractDao(Connection connectionn) {
        this.connection = connectionn;
        this.clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T create(T t, String tableName) {
        String query = "INSERT INTO ? (?) VALUES(?)";

        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, tableName);
            statement.setString(2, getColumnNamesList(tableName));
            statement.setString(3, getValuesList(t));
            statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T getById(ID id, String tableName) {
        String query = "SELECT * FROM ? WHERE ID=?";

        PreparedStatement statement;
        ResultSet rs;
        //ResultSetMetaData rsmd = null;

        try {
            statement = connection.prepareStatement(query);
            //statement.setString(1, rsmd.getTableName(1));
            statement.setString(1, tableName);
            statement.setLong(2, Long.parseLong(String.valueOf(id)));

            rs = statement.executeQuery();
            //rsmd = rs.getMetaData();

            if (rs.next()) {
                return getObjectFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<T> getAll(String tableName) {
        List<T> objectList = new ArrayList<>();
        String query = "SELECT * FROM ?";

        PreparedStatement statement;
        ResultSet rs;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, tableName);

            rs = statement.executeQuery();

            while (rs.next()) {
                T t = getObjectFromResultSet(rs);
                objectList.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objectList;
    }

    @Override
    public T update(T t, String tableName) {
        String query = "UPDATE ? SET ? WHERE ID=?";

        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, tableName);
            statement.setString(2, getSetColumnValuesPartOfRequest(t, tableName));
            statement.setLong(3, (Long) getObjectId(t));
            statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        T updatedT = getById(getObjectId(t), tableName);
        return updatedT;
    }

    @Override
    public void delete(ID id, String tableName) {
        String query = "DELETE FROM ? WHERE id=?";

        PreparedStatement statement;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1, tableName);
            statement.setLong(2, Long.parseLong(String.valueOf(id)));
            statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private T getObjectFromResultSet(ResultSet rs) throws SQLException{
        T t = null;
        String[] columnNames = getColumnNamesFromResultSet(rs);
        Field[] fields = clazz.getDeclaredFields();

        try {
            t = clazz.newInstance();

            if (fields.length != columnNames.length) {
                throw new IndexOutOfBoundsException("Amount of object fields ant table column is different!");
            } else {

                //TODO WRITE STREAM
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    field.setAccessible(true);
                    field.set(rs.getObject(columnNames[i]), field.getName());
                }
            }

        } catch (InstantiationException
                | IllegalAccessException
                | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return t;
    }

    private ID getObjectId(T t) {
        ID id = null;
        try {

            Method method = clazz.getDeclaredMethod("getId");
            id = (ID) method.invoke(t); //TODO DONT KNOW IS CORRECT OR NOT

        } catch (NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        return id;
    }

    private String getSetColumnValuesPartOfRequest(T t, String tableName) {
        //? = value1, ? = value2, ...
        StringBuilder requestPart = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        String[] columnNames = getColumnNamesFromNewRequest(tableName);
        try {

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);

                String column = columnNames[i];
                String value = String.valueOf(field.get(t));

                requestPart.append(format("%s=%s ", column, value));
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // should delete unnecessary space in the end of requestPart
        return requestPart.toString().substring(0, requestPart.length() - 1);
    }

    private String[] getColumnNamesFromNewRequest(String tableName) {
        String[] columnNames = null;
        String query = "SELECT * FROM ?";

        PreparedStatement statement;
        ResultSet rs;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, tableName);

            rs = statement.executeQuery();
            columnNames = getColumnNamesFromResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columnNames;
    }

    private String[] getColumnNamesFromResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int amountOfColumns = rsmd.getColumnCount();
        String[] columnNames = new String[amountOfColumns];

        for (int i = 1; i <= amountOfColumns; i++) {
            columnNames[i -1] = rsmd.getColumnName(i);
        }

        return columnNames;
    }

    private String getColumnNamesList(String tableName) {
        String[] columnNames = getColumnNamesFromNewRequest(tableName);
        return Arrays.toString(columnNames).substring(1, columnNames.length -1);
    }

    private String getValuesList(T t) {
        Field[] fields = clazz.getDeclaredFields();
        String[] values = new String[fields.length];

        try {
            for (int i = 0; i < values.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                values[i] = field.get(t).toString();
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return Arrays.toString(values).substring(1, values.length - 1);
    }
}
