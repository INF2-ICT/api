package com.quintor.api.service;

import com.quintor.api.model.UserModel;
import com.quintor.api.util.RelationalDatabaseUtil;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<UserModel> userList;
    private Connection connection;

    public UserService() throws SQLException {
        this.userList = new ArrayList<>();
        this.connection = RelationalDatabaseUtil.getConnection();
    }

    /**
     * Function to return prepared sql statement with try/catch
     * @param query Sql query
     * @return Prepared statement with given query
     * @throws SQLException if error
     */
    private PreparedStatement createPreparedStatement(String query) throws SQLException {
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            return statement;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public List<UserModel> getAllUsers() throws SQLException {
        List<UserModel> users = new ArrayList<>();
//        PreparedStatement stmt = createPreparedStatement("SELECT * FROM user");

        try {
            PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM USER");

//            if (stmt != null) {
                /* Execute statement */
                ResultSet result = stmt.executeQuery();

                /* Loop over results */
                while (result.next()) {
                    /* Get all data from result */
                    int id = result.getInt(1); // result[0] = id
                    int role_id = result.getInt(2); // result[1] = role_id
                    String first_name = result.getString(3); // result[2] = first_name
                    String last_name = result.getString(4); // result[3] = last_name
                    String email = result.getString(5); // result[4] = email
                    String password = result.getString(6); // result[5] = password

                    /* Add user to list of users */
                    users.add(new UserModel(id, role_id, first_name, last_name, email, password));
                }
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /* Return list of all users from DB. Could be null */
        return users;
    }

    public UserModel getUser (int id) {
        return new UserModel(id, 1, "Stefan", "Meijer", "stefan@meijer.nl", "welkom10");
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
