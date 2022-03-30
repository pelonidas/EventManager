package com.project.dal;

import com.project.be.Admin;
import com.project.bll.exceptions.UserException;
import com.project.dal.connectorDAO.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    DBConnector dbConnector;
    public AdminDAO() throws IOException {
        dbConnector = new DBConnector();
    }
    public List<Admin> getAllAdmins()throws SQLException, UserException {
        List<Admin>allAdmins = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection()){
            String sql = "SELECT * FROM categories_users WHERE category=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"admin");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String sql1 = "SELECT * FROM users WHERE category =? ";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                preparedStatement1.setInt(1,id);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()){
                    int userId = resultSet1.getInt(1);
                    String sql2= "SELECT * FROM credentials WHERE user_id= ?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setInt(1,userId);
                    ResultSet resultSet2 = preparedStatement2.executeQuery();
                    while (resultSet2.next()){
                        allAdmins.add(new Admin(resultSet1.getInt("id"),
                                resultSet1.getString("first_name"),
                                resultSet1.getString("last_name"),
                                resultSet2.getString("user_name"),
                                resultSet2.getString("password"),
                                resultSet1.getString("email"),
                                resultSet1.getInt("phone_number")));
                    }
                }
            }
        }
        return allAdmins;
    }
}
