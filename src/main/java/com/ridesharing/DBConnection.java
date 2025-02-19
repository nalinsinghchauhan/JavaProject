package com.ridesharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ridesharing"; // Database URL
    private static final String USER = "root"; // Database username
    private static final String PASSWORD = "root"; // Database password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
