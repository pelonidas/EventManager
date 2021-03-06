package com.project.dal;

import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.be.Event;
import com.project.be.TicketType;
import com.project.dal.connectorDAO.DBCPDataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventDAO {
    DBCPDataSource dataSource;

    public EventDAO() throws IOException {
        dataSource = DBCPDataSource.getInstance();
    }

    public List<Event> getAllEvents() throws SQLException {
        List<Event> allEvents = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
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

            event.setAllCoordinators(getAllCoordinatorsEvent(event, connection));
            event.setParticipants(getAllCustomers(event, connection));
            allEvents.add(event);

            TicketType ticketType;
            List<TicketType> allTicketTypes = new ArrayList<>();
            String sql0 = "SELECT * FROM categories_ticket WHERE event_ID= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql0);
            preparedStatement.setInt(1, event.getId());
            ResultSet resultSet0 = preparedStatement.executeQuery();
            while (resultSet0.next()) {
                ticketType = new TicketType(resultSet0.getInt("id"),
                        resultSet0.getString("title"),
                        resultSet0.getString("benefits"),
                        resultSet0.getInt("price"),
                        resultSet0.getInt("seats_available"));
                allTicketTypes.add(ticketType);
            }
            event.setAllTicketTypes(allTicketTypes);
        }
        }
        return allEvents;
    }

    public Event createEvent(String title, Date dateAndTime, String location, String description, int seatsAvailable, List<TicketType> ticketTypes) throws SQLException {
        Event event = null;
        try (Connection connection = dataSource.getConnection()){
        String sql = "INSERT INTO events VALUES(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, title);
        preparedStatement.setTimestamp(2, new Timestamp(dateAndTime.getTime()));
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, description);
        preparedStatement.setInt(5, seatsAvailable);
        //ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        while (resultSet.next()) {
            int generatedID = resultSet.getInt(1);
            event = getEventByID(generatedID);
        }}
        createMultipleTicketTypes(ticketTypes,event.getId());
        event.setAllTicketTypes(getAllTicketTypesForEvent(event));
        return event;
    }

    public void createMultipleTicketTypes(List<TicketType> ticketTypes, int eventId) throws SQLException {
        if (ticketTypes==null)
            return;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO categories_ticket VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (TicketType ticketType : ticketTypes) {
                preparedStatement.setString(1, ticketType.getTitle());
                preparedStatement.setDouble(2, ticketType.getPrice());
                preparedStatement.setString(3, ticketType.getBenefits());
                preparedStatement.setInt(4, ticketType.getSeatsAvailable());
                preparedStatement.setInt(5, eventId);
                preparedStatement.executeUpdate();
            }
        }
    }

    public Event getEventByID(int id) throws SQLException {
        Event event = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM events WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                event = new Event(
                        id,
                        resultSet.getString("title"),
                        resultSet.getTimestamp("date"),
                        resultSet.getString("location"),
                        resultSet.getString("description"),
                        resultSet.getInt("seats_available"));
            }
        }
        return event;
    }

    public void deleteEvent(Event event) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
        String sql = "DELETE FROM events WHERE id= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, event.getId());
        preparedStatement.executeUpdate();
    }}

    public Event editEvent(Event event) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE events SET title = ?, date = ?,location = ?, description= ? ,[seats_available] = ?  WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, event.getTitle());
            preparedStatement.setTimestamp(2, new Timestamp(event.getDateAndTime().getTime()));
            preparedStatement.setString(3, event.getLocation());
            preparedStatement.setString(4, event.getDescription());
            preparedStatement.setInt(5, event.getSeatsAvailable());
            preparedStatement.setInt(6, event.getId());

            preparedStatement.executeUpdate();
        }
        return getEventByID(event.getId());
    }

    private List<Coordinator> getAllCoordinatorsEvent(Event event,Connection connection) throws SQLException {
        List<Coordinator> allCoordinatorsEvent = new ArrayList<>();
        String sql = "SELECT * FROM event_coordinator WHERE event_id= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, event.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int coordinatorId = resultSet.getInt("coordinator_id");
            String sql0 = "SELECT * FROM users WHERE id =? ";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql0);
            preparedStatement1.setInt(1, coordinatorId);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()) {
                allCoordinatorsEvent.add(new Coordinator(resultSet1.getInt("id"),
                        resultSet1.getString("first_name"),
                        resultSet1.getString("last_name"),
                        resultSet1.getString("user_name"),
                        resultSet1.getString("password"),
                        resultSet1.getString("email"),
                        resultSet1.getInt("phone_number")));
            }
        }

        return allCoordinatorsEvent;
    }

    private List<Customer> getAllCustomers(Event event,Connection connection) throws SQLException {
        List<Customer> allCustomers = new ArrayList<>();
        String sql0 = "SELECT * FROM tickets WHERE event_id= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql0);
        preparedStatement.setInt(1, event.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int customerId = resultSet.getInt("customer_id");
            String sql = "SELECT * FROM users WHERE id= ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
            preparedStatement1.setInt(1, customerId);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()) {
                allCustomers.add(new Customer(customerId,
                        resultSet1.getString("first_name"),
                        resultSet1.getString("last_name"),
                        resultSet1.getString("email"),
                        resultSet1.getInt("phone_number")));
            }
        }
        return allCustomers;
    }

    public Event getEvent(int id) throws SQLException {
        Event event = null;
        try (Connection connection = dataSource.getConnection()){
        String sql = "SELECT * FROM events WHERE id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            event = new Event(resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getTimestamp("date"),
                    resultSet.getString("location"),
                    resultSet.getString("description"),
                    resultSet.getInt("seats_available"));
        }}
        return event;
    }

    public List<TicketType> getAllTicketTypesForEvent(Event selectedEvent) throws SQLException {
        List<TicketType> ticketsForEvent = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            String sql = "SELECT * FROM categories_ticket\n" +
                    "WHERE event_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, selectedEvent.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String benefits = resultSet.getString("benefits");
                double price = resultSet.getDouble("price");
                int seatsAvaible = resultSet.getInt("seats_available");
                int id = resultSet.getInt("id");

                TicketType ticketType = new TicketType(id, title, benefits, price, seatsAvaible);
                ticketsForEvent.add(ticketType);
            }}
        return ticketsForEvent;
    }
}
