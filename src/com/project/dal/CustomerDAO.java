package com.project.dal;

import com.project.be.Customer;
import com.project.be.Event;
import com.project.bll.exceptions.UserException;
import com.project.bll.util.CheckInput;
import com.project.dal.connectorDAO.DBConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    DBConnector dbConnector;
    CheckInput checkInput;
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
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1,id);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    Customer customer = new Customer(resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("email"),
                            resultSet.getInt("phone_number"));
                    customer.setMyEvents((ArrayList<Event>) getCustomerEventHistory(customer));
                    allCustomers.add(customer);

            }
            }
        }
        return allCustomers;
    }

    public Customer createCustomer(String firstName, String lastName, String email, int phoneNumber) throws  UserException,SQLException {
        exceptionCreation(firstName,lastName,email,phoneNumber);
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
            String sql= "INSERT INTO users VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,email);
            preparedStatement.setInt(5,phoneNumber);
            preparedStatement.setInt(4,idCategory);
            preparedStatement.executeUpdate();
            ResultSet resultSet1= preparedStatement.getGeneratedKeys();
            while (resultSet1.next()){
                int id = resultSet1.getInt(1);
                customer = new Customer(id,firstName,lastName,email,phoneNumber);
            }
        }
        return customer;
    }
    public void deleteCustomer(Customer customer)throws SQLException{
            try (Connection connection= dbConnector.getConnection()){;
                String sql1 = "DELETE FROM tickets WHERE customer_id = ?";
                PreparedStatement preparedStatement1= connection.prepareStatement(sql1);
                preparedStatement1.setInt(1,customer.getId());
                preparedStatement1.executeUpdate();
            String sql = "DELETE FROM users WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,customer.getId());
            preparedStatement.executeUpdate();
        }
    }
    public Customer editCustomer(Customer customer) throws SQLException{
        try (Connection connection = dbConnector.getConnection()){
            String sql = "UPDATE users SET first_name = ?, last_name = ? ,email = ?, phone_number = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setInt(4, customer.getPhoneNumber());
            preparedStatement.setInt(5,customer.getId());

            preparedStatement.executeUpdate();
        }
        return customer;
    }

    private void exceptionCreation(String firstName, String lastName, String email, int phoneNumber) throws UserException {
        if (firstName.isEmpty())
            throw new UserException("Please enter your first name.",new Exception());
        if (!(firstName.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"))){
            UserException userException = new UserException("Please find a valid first name",new Exception());
            userException.setInstructions("A valid name is only composed of Alphabet characters");
            throw userException;}

        if (lastName.isEmpty())
            throw new UserException("Please enter your last name.",new Exception());

        if (!(lastName.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"))){
            UserException userException = new UserException("Please find a valid last name",new Exception());
            userException.setInstructions("A correct name is only composed of Alphabet characters");
            throw userException;
        }

        if(email.isEmpty())
            throw new UserException("Please enter your email.",new Exception());

        if (!CheckInput.isValidEmailAddress(email))
            throw new UserException("Please enter a valid email.",new Exception());
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
                String email = resultSet.getString("email");
                int phoneNumber = resultSet.getInt("phone_number");

                Customer customer = new Customer(id,firstName,lastName,email,phoneNumber);
                allCustomersFromSameEvent.add(customer);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomersFromSameEvent;
    }
    private List<Event> getCustomerEventHistory(Customer customer)throws SQLException{
        List<Event>customerEventHistory= new ArrayList<>();
        try (Connection connection = dbConnector.getConnection()){
            String sql = "SELECT * FROM tickets WHERE customer_id = ?";
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setInt(1,customer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int eventId =resultSet.getInt("event_id");
                String sql1= "SELECT * FROM events WHERE id= ?";
                PreparedStatement preparedStatement1= connection.prepareStatement(sql1);
                preparedStatement1.setInt(1,eventId);
                ResultSet resultSet1= preparedStatement1.executeQuery();
                while (resultSet1.next()){
                    customerEventHistory.add(new Event(resultSet1.getInt(1),
                            resultSet1.getString(2),
                            resultSet1.getDate(3),
                            resultSet1.getString(4),
                            resultSet1.getString(5),
                            resultSet1.getInt(6)));
                }
            }
        }
        return customerEventHistory;
    }
    public Customer getCustomer(int id) throws SQLException{
        Customer customer=null;
        try (Connection connection = dbConnector.getConnection()){
            String sql = "SELECT * FROM users WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                 customer = new Customer(resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getInt("phone_number"));
            }
        }
        return customer;
    }
}
