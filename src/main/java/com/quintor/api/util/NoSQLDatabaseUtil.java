package com.quintor.api.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class NoSQLDatabaseUtil {

    private static Connection connection;

    public NoSQLDatabaseUtil(){connection = null;}

    public static Connection getConnection() throws SQLException{
        if (connection != null) {
            return connection;
        } else {
            String driver ="org.springframework.boot";
            String url = "mongodb://localhost:27017";
            String user = "root";
            String password = "example";

            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

}

