package com.quintor.api.service;

import com.quintor.api.model.UserModel;
import com.quintor.api.util.RelationalDatabaseUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final Connection connection;

    public UserService() throws SQLException {
        this.connection = RelationalDatabaseUtil.getConnection();
    }

    /**
     * Function to get all users listed in the mariaDB
     * @return List of all users
     */
    public List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();
        ResultSet result;

        try (PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM USER")) {
            /* Execute statement */
            result = stmt.executeQuery();

            /* Loop over results */
            while (result.next()) {
                /* Get all data from result */
                int id = result.getInt(1); // result[1] = id
                int role_id = result.getInt(2); // result[2] = role_id
                String first_name = result.getString(3); // result[3] = first_name
                String last_name = result.getString(4); // result[4] = last_name
                String email = result.getString(5); // result[5] = email
                String password = result.getString(6); // result[6] = password

                /* Add user to list of users */
                users.add(new UserModel(id, role_id, first_name, last_name, email, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /* Return list of all users from DB. Could be null */
        return users;
    }

    public UserModel getUser (int id) {
        return new UserModel(id, 1, "Stefan", "Meier", "stefan@meijer.nl", "welcome10");
    }

    /**
     * Get database connection
     * @return Database connection
     */
    public Connection getConnection() {
        return connection;
    }
}
