package com.project.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.be.Event;
import com.project.dal.connectorDAO.DBConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventDAO {
    DBConnector dbConnector;

    public EventDAO() throws IOException {
        dbConnector = new DBConnector();
    }

    public List<Event> getAllEvents() throws SQLException {
        List<Event> allEvents = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM events e";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Event event = new Event(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getTimestamp("date"),
                        resultSet.getString("location"),
                        resultSet.getString("description"),
                        resultSet.getInt("seats_available"));
                event.setAllCoordinators(getAllCoordinatorsEvent(event));
                event.setParticipants(getAllCustomers(event));
                allEvents.add(event);

            }
        }
        return allEvents;
    }

    public Event createEvent(String title, Date dateAndTime, String location, String description, int seatsAvailable) throws SQLException {
        Event event = null;
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "INSERT INTO events VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setTimestamp(2,new Timestamp(dateAndTime.getTime()));
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, description);
            preparedStatement.setInt(5, seatsAvailable);
            //ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()){
                int generatedID = resultSet.getInt(1);
                event = getEventByID(generatedID);
            }

        }
        return event;
    }

    public Event getEventByID(int id) {
        Event event = null;
        try(Connection connection = dbConnector.getConnection()){
            String sql = "SELECT * FROM events WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                event = new Event(
                        id,
                        resultSet.getString("title"),
                        resultSet.getDate("date"),
                        resultSet.getString("location"),
                        resultSet.getString("description"),
                        resultSet.getInt("seats_available"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return event;
    }

    public void deleteEvent(Event event) throws SQLException {
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "DELETE FROM events WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, event.getId());
            preparedStatement.executeUpdate();
        }
    }

    public Event editEvent(Event event) throws SQLException {
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "UPDATE events SET title = ?, date = ?,location = ?, description= ? ,[seats_available] = ?  WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, event.getTitle());
            preparedStatement.setTimestamp(2,new Timestamp(event.getDateAndTime().getTime()));
            preparedStatement.setString(3, event.getLocation());
            preparedStatement.setString(4, event.getDescription());
            preparedStatement.setInt(5, event.getSeatsAvailable());
            preparedStatement.setInt(6, event.getId());

            preparedStatement.execute();

            event = getEventByID(event.getId());
        }
        return event;
    }
    private List<Coordinator>getAllCoordinatorsEvent(Event event)throws SQLException{
        List<Coordinator>allCoordinatorsEvent = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection()){
            String sql = "SELECT * FROM event_coordinator WHERE event_id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,event.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int coordinatorId=resultSet.getInt("coordinator_id");
                String sql0= "SELECT * FROM users WHERE id =? ";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql0);
                preparedStatement1.setInt(1,coordinatorId);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()){
                    allCoordinatorsEvent.add( new Coordinator(resultSet1.getInt("id"),
                            resultSet1.getString("first_name"),
                            resultSet1.getString("last_name"),
                            resultSet1.getString("user_name"),
                            resultSet1.getString("password"),
                            resultSet1.getString("email"),
                            resultSet1.getInt("phone_number")));
                }
            }
        }
        return allCoordinatorsEvent;
    }

    private List<Customer>getAllCustomers(Event event)throws SQLException{
        List<Customer>allCustomers= new ArrayList<>();
        try (Connection connection = dbConnector.getConnection()){
            String sql0= "SELECT * FROM tickets WHERE event_id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql0);
            preparedStatement.setInt(1,event.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int customerId = resultSet.getInt("customer_id");
                String sql="SELECT * FROM users WHERE id= ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
                preparedStatement1.setInt(1,customerId);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()){
                    allCustomers.add(new Customer(customerId,
                            resultSet1.getString("first_name"),
                            resultSet1.getString("last_name"),
                            resultSet1.getString("email"),
                            resultSet1.getInt("phone_number")));
                }
            }
        }
        return allCustomers;
    }


}
