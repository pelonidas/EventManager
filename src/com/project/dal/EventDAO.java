package com.project.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.project.be.Coordinator;
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
                        resultSet.getDate("date"),
                        resultSet.getString("location"),
                        resultSet.getString("description"),
                        resultSet.getInt("seats_available"));
                event.setAllCoordinators(getAllCoordinatorsEvent(event));
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
            preparedStatement.setDate(2, new java.sql.Date(dateAndTime.getTime()));
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, description);
            preparedStatement.setInt(5, seatsAvailable);
            //ResultSet resultSet = preparedStatement.executeQuery();
            int affectedRows = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()){
                event = getEventByID(resultSet.getInt(1));
            }

        }
        System.out.println(event);
        return event;
    }

    private Event getEventByID(int id) {
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

    public Event editEvent(Event event, String title, Date dateAndTime, String location, String description, int seatsAvailable) throws SQLException {
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "UPDATE events SET title = ?, date = ?,location = ?, description= ? ,[seats_available] = ?   WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setDate(2, (java.sql.Date) dateAndTime);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, description);
            preparedStatement.setInt(5, seatsAvailable);
            preparedStatement.setInt(6, event.getId());

            event.setTitle(title);
            event.setDateAndTime(dateAndTime);
            event.setLocation(location);
            event.setDescription(description);

            preparedStatement.executeUpdate();
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
                            resultSet1.getDate("birth_date")));
                }
            }
        }
        return allCoordinatorsEvent;
    }


}
