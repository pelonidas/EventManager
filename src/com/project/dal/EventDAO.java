package com.project.dal;

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
    public List<Event>getAllEvents () throws SQLException{
        List<Event>allEvents = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection()){
            String sql= "SELECT * FROM events";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()){
                allEvents.add(new Event(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getDate("date"),
                        resultSet.getString("location"),
                        resultSet.getString("description")));
            }
        }
        return allEvents;
    }
    public Event createEvent(String title, Date dateAndTime, String location, String description, int seatsAvailable) throws SQLException{
        Event event = null;
        try (Connection connection = dbConnector.getConnection()){
            String sql = "INSERT INTO events VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,title);
            preparedStatement.setDate(2, (java.sql.Date) dateAndTime);
            preparedStatement.setString(3,location);
            preparedStatement.setString(4,description);
            preparedStatement.setInt(5,seatsAvailable);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
              event = new Event(resultSet.getInt(1),title,dateAndTime,location,description)  ;
            }
        }
        return event;
    }
    public void deleteEvent (Event event) throws SQLException{
        try (Connection connection= dbConnector.getConnection()){
            String sql = "DELETE FROM events WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,event.getId());
            preparedStatement.executeUpdate();
        }
    }
    public Event editEvent(Event event,String title, Date dateAndTime, String location, String description, int seatsAvailable) throws SQLException{
        try (Connection connection = dbConnector.getConnection()){
            String sql = "UPDATE events SET title = ?, date = ?,location = ?, description= ? ,[seats availble] = ?   WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,title);
            preparedStatement.setDate(2, (java.sql.Date) dateAndTime);
            preparedStatement.setString(3,location);
            preparedStatement.setString(4,description);
            preparedStatement.setInt(5,seatsAvailable);
            preparedStatement.setInt(6,event.getId());

            event.setTitle(title);
            event.setDateAndTime(dateAndTime);
            event.setLocation(location);
            event.setDescription(description);

            preparedStatement.executeUpdate();
        }
        return event;
    }
    }
