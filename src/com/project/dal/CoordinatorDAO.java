package com.project.dal;

import com.project.be.Coordinator;
import com.project.dal.connectorDAO.DBConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoordinatorDAO {
    DBConnector dbConnector;
    public CoordinatorDAO() throws IOException {
        dbConnector = new DBConnector();
    }
    public List<Coordinator> getAllCoordinators() throws SQLException {
        List<Coordinator>allCoordinators= new ArrayList<>();
        try (Connection connection = dbConnector.getConnection()){
            String sql0 = "SELECT * FROM categories_users WHERE category=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql0);
            preparedStatement.setString(1,"coordinator");
            ResultSet resultSet1 = preparedStatement.executeQuery();
            while (resultSet1.next()){
                int id = resultSet1.getInt("id");

                String sql="SELECT * FROM users WHERE category = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1,id);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    allCoordinators.add(new Coordinator(resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("user_name"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),

                            resultSet.getDate("birth_date")));
            }



            }
        }
        return allCoordinators;
    }
    public Coordinator createCoordinator(String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, Date birthDate) throws SQLException{
        Coordinator coordinator = null;
        int idCategory=0;
        try (Connection connection= dbConnector.getConnection()){
            String sql0="SELECT * FROM categories_users WHERE category=?";
            PreparedStatement preparedStatement0 = connection.prepareStatement(sql0);
            preparedStatement0.setString(1,"coordinator");
            ResultSet resultSet = preparedStatement0.executeQuery();
            while (resultSet.next()){
                idCategory = resultSet.getInt("id");
            }
            String sql= "INSERT INTO users VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,userName);
            preparedStatement.setString(4,passWord);
            preparedStatement.setString(5,email);
            preparedStatement.setString(6,address);
            preparedStatement.setString(7,String.valueOf(phoneNumber));
            preparedStatement.setDate(8,birthDate);
            preparedStatement.setInt(9,idCategory);
            preparedStatement.executeUpdate();
            ResultSet resultSet1= preparedStatement.getGeneratedKeys();
            while (resultSet1.next()){
                int id = resultSet1.getInt(1);
                coordinator = new Coordinator(id,firstName,lastName,userName,passWord,email,birthDate);
            }
        }
        return coordinator;
    }
    public void deleteCoordinator (Coordinator coordinator)throws SQLException{
        try (Connection connection= dbConnector.getConnection()){
            String sql = "DELETE FROM users WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,coordinator.getId());
            preparedStatement.executeUpdate();
        }
    }
    public Coordinator editCoordinator(Coordinator coordinator,String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, Date birthDate) throws SQLException{
        try (Connection connection = dbConnector.getConnection()){
            String sql = "UPDATE users SET first_name = ?, last_name = ?,user_name = ?, password= ? ,email = ?, address = ?, phone_number = ?, birth_date = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,userName);
            preparedStatement.setString(4,passWord);
            preparedStatement.setString(5,email);
            preparedStatement.setString(6,address);
            preparedStatement.setString(7, String.valueOf(phoneNumber));
            preparedStatement.setDate(8,birthDate);
            preparedStatement.setInt(9,coordinator.getId());

            coordinator.setFirstName(firstName);
            coordinator.setLastName(lastName);
            coordinator.setUserName(userName);
            coordinator.setPassWord(passWord);
            coordinator.setEmail(email);

            coordinator.setBirthDate(birthDate);

            preparedStatement.executeUpdate();
        }
        return coordinator;
    }
}
