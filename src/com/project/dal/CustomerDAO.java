package com.project.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
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
            String sql0 = "SELECT * FROM categories_users WHERE category=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql0);
            preparedStatement.setString(1,"customer");
            ResultSet resultSet1 = preparedStatement.executeQuery();
            while (resultSet1.next()){
                int id = resultSet1.getInt("id");
                String sql="SELECT * FROM users WHERE category = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
                preparedStatement1.setInt(1,id);
                ResultSet resultSet = preparedStatement1.executeQuery();
                while (resultSet.next()){
                        allCustomers.add(new Customer(resultSet.getInt("id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("user_name"),
                                resultSet.getString("password"),
                                resultSet.getString("email"),
                                resultSet.getString("address"),
                                resultSet.getString("phone_number"),
                                resultSet.getDate("birth_date"),
                                id));
                }
            }
        }
        return allCustomers;
    }

    public Customer createCustomer(String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, Date birthDate) throws SQLException{
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
                customer = new Customer(id,firstName,lastName,userName,passWord,email,address,String.valueOf(phoneNumber),birthDate);
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
    public Customer editCustomer(Customer customer,String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, Date birthDate) throws SQLException{
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
            customer.setPhoneNumber(String.valueOf(phoneNumber));
            customer.setBirthDate(birthDate);

            preparedStatement.executeUpdate();
        }
        return customer;
    }

    public List<Customer> getAllCustomersFromSameEvent(int eventId) throws SQLServerException {
        List<Customer> allCustomersFromSameEvent = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection()){
            String sql = "SELECT * " +
                         "  FROM users " +
                         "  JOIN tickets ON users.id = tickets.customer_id " +
                         "  WHERE tickets.event_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, eventId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String userName = resultSet.getString("user_name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phone_Number");
                Date birthDay = resultSet.getDate("birth_date");
                int category = resultSet.getInt("category");

                Customer customer = new Customer(id, firstName, lastName, userName, password, email,
                                                address, phoneNumber, birthDay, category);
                allCustomersFromSameEvent.add(customer);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomersFromSameEvent;
    }
}
