package com.project.dal;

import com.project.be.Coordinator;
import com.project.dal.connectorDAO.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    DBConnector dbConnector;
    public UserDAO() throws IOException {
        dbConnector = new DBConnector();
    }
    public List<Coordinator> getAllCoordinators() throws SQLException {
        List<Coordinator> allCoordinators = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection()) {
            String sql0 = "SELECT * FROM categories_users WHERE category=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql0);
            preparedStatement.setString(1, "coordinator");
            ResultSet resultSet1 = preparedStatement.executeQuery();
            while (resultSet1.next()) {
                int id = resultSet1.getInt("id");

                String sql = "SELECT * FROM users WHERE category = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    allCoordinators.add(new Coordinator(resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("user_name"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("address"),
                            resultSet.getInt("phone_number"),
                            resultSet.getDate("birth_date")));
                }


            }
        }
        return allCoordinators;

    }

}
