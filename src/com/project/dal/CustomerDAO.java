package com.project.dal;

import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.dal.connectorDAO.DBConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    DBConnector dbConnector;
    public CustomerDAO() throws IOException {
        dbConnector = new DBConnector();
    }
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer>allCustomers= new ArrayList<>();
        try (Connection connection = dbConnector.getConnection()){
            String sql="SELECT * FROM users";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()){
                int id = resultSet.getInt("category");
                String sql0 = "SELECT * FROM categories_users WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql0);
                preparedStatement.setInt(1,id);
                ResultSet resultSet1 = preparedStatement.getResultSet();
                while (resultSet1.next()){
                    String category = resultSet1.getString("category");
                    if (category.equals("customer"))
                        allCustomers.add(new Customer(resultSet.getInt("id"),
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
        return allCustomers;
    }

    public Customer createCoordinator(String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, Date birthDate) throws SQLException{
        Customer customer = null;
        int idCategory=0;
        try (Connection connection= dbConnector.getConnection()){
            String sql0="SELECT * FROM categories_users WHERE category=?";
            PreparedStatement preparedStatement0 = connection.prepareStatement(sql0);
            preparedStatement0.setString(1,"customer");
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
                customer = new Customer(id,firstName,lastName,userName,passWord,email,address,phoneNumber,birthDate);
            }
        }
        return customer;
    }
    public void deleteCustomer (Customer customer)throws SQLException{
        try (Connection connection= dbConnector.getConnection()){
            String sql = "DELETE FROM users WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,customer.getId());
            preparedStatement.executeUpdate();
        }
    }
    public Customer editCoordinator(Customer customer,String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, Date birthDate) throws SQLException{
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
            preparedStatement.setInt(9,customer.getId());

            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setUserName(userName);
            customer.setPassWord(passWord);
            customer.setEmail(email);
            customer.setAddress(address);
            customer.setPhoneNumber(phoneNumber);
            customer.setBirthDate(birthDate);

            preparedStatement.executeUpdate();
        }
        return customer;
    }
}
