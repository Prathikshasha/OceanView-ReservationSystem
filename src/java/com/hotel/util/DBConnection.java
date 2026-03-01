package com.hotel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3306/hotel_reservation_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "prathiksha_84567";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // ✅ load MySQL driver
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found. Put mysql-connector-j jar inside WEB-INF/lib.", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}