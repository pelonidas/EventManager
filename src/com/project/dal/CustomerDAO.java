package com.project.dal;

import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.bll.exceptions.UserException;
import com.project.bll.util.CheckInput;
import com.project.dal.connectorDAO.DBConnector;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;


import java.io.IOException;
import java.io.PipedReader;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerDAO {
    DBConnector dbConnector;
    CheckInput checkInput ;
    public CustomerDAO() throws IOException {
        dbConnector = new DBConnector();
        checkInput = new CheckInput();
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
                                resultSet.getDate("birth_date"),
                                id));
                }
            }
        }
        return allCustomers;
    }

    public Customer createCustomer(String firstName, String lastName, String userName, String passWord, String email, Date birthDate) throws UserException {
        exceptionCreation(firstName,lastName,userName,passWord,email,birthDate);
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
            preparedStatement.setDate(8,birthDate);
            preparedStatement.setInt(9,idCategory);
            preparedStatement.executeUpdate();
            ResultSet resultSet1= preparedStatement.getGeneratedKeys();
            while (resultSet1.next()){
                int id = resultSet1.getInt(1);
                customer = new Customer(id,firstName,lastName,userName,passWord,email,birthDate);
            }
        }catch (SQLException sqlException){
            throw new UserException("something went wrong in the database",new Exception());
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
            customer.setBirthDate(birthDate);

            preparedStatement.executeUpdate();
        }
        return customer;
    }

    private void exceptionCreation(String firstName, String lastName, String userName, String passWord, String email, Date birthDate) throws UserException {
        if (firstName.isEmpty())
            throw new UserException("Please enter your first name.",new Exception());
        if (lastName.isEmpty())
            throw new UserException("Please enter your last name.",new Exception());
        if (!checkInput.isValidName(firstName)){
            UserException userException = new UserException("Please find a valid first name",new Exception());
            userException.setInstructions("A valid name is only composed of Alphabet characters");
            throw userException;
        }
        if (!checkInput.isValidName(lastName)){
            UserException userException = new UserException("Please find a valid last name",new Exception());
            userException.setInstructions("A correct name is only composed of Alphabet characters");
            throw userException;
        }
        if (userName.isEmpty())
            throw new UserException("Please find a username.",new Exception());

        if (userNameTaken(userName)){
            UserException userException = new UserException("user name already exists.",new Exception());
            userException.setInstructions("Please find another one and try again.");
            throw userException;
        }

        if (CheckInput.isPasswordValid(passWord)) {
                UserException userException = new UserException("Please find a correct password.",new Exception());
                userException.setInstructions("A password is composed of an 9-length string containing only characters and digits, at least two of the digits");
                throw userException;
            }
        if(email.isEmpty())
            throw new UserException("Please enter your email.",new Exception());

        if (!CheckInput.isValidEmailAddress(email))
            throw new UserException("Please enter a valid email.",new Exception());
        }

    private Boolean userNameTaken(String userName) throws UserException {
        try (Connection connection = dbConnector.getConnection()){
            String sql = "SELECT * FROM Users WHERE user_name=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        }catch (SQLException sqlException){
            throw  new UserException("Something went wrong in the database",new Exception());
        }
        return false;
    }
    public Customer logInCredentials(String userName)throws SQLException,UserException{
        for (Customer customer: getAllCustomers()){
            if (userName.equals(customer.getUserName()))
                return customer;
        }
        return null;
    }

    public List<Customer> getAllCustomersFromSameEvent(int eventId) throws Exception {
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
                Date birthDay = resultSet.getDate("birth_date");
                int category = resultSet.getInt("category");

                Customer customer = new Customer(id, firstName, lastName, userName, password, email, birthDay, category);
                allCustomersFromSameEvent.add(customer);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomersFromSameEvent;
    }
}
