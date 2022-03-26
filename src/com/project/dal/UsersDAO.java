package com.project.dal;

import com.project.be.Admin;
import com.project.bll.exceptions.UserException;
import com.project.dal.connectorDAO.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {
    DBConnector dbConnector;
    public UsersDAO() throws IOException {
        dbConnector = new DBConnector();
    }
    public Admin logInAdminCredentials(String userName)throws SQLException, UserException {
        try (Connection connection = dbConnector.getConnection()){
            String sql= "SELECT * FROM Users WHERE user_name=? AND category= ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.setInt(2,1);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Admin(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getDate(7));
            }
        }
        return null;
    }
    }
