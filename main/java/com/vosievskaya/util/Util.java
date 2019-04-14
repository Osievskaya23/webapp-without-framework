package com.vosievskaya.util;

import com.vosievskaya.annotation.Table;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

public class Util {

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte hash1 : hash) {
                String hex = Integer.toHexString(0xff & hash1);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static String getTableAnnotation(Class clazz, String methodName) {
        String tableName = "";
        try {
            Method m = clazz.getMethod(methodName);
            Table annotation = m.getAnnotation(Table.class);
            tableName = annotation.name();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return tableName;
    }
}
