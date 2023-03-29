package com.quintor.api.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RelationalDatabaseUtil {

    private static Connection connection;

    public RelationalDatabaseUtil() {
        connection = null;
    }

    public static Connection getConnection() throws SQLException {
        if (connection != null) {
            return connection;
        } else {
            String driver = "org.mariadb.jdbc.Driver";
            String url = "jdbc:mariadb://localhost:3306/quintor";
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
