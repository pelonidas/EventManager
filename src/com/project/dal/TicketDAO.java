package com.project.dal;

import com.project.be.*;
import com.project.bll.exceptions.UserException;
import com.project.dal.connectorDAO.DBCPDataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    DBCPDataSource dataSource;
    EventDAO eventDAO;
    CustomerDAO customerDAO;
    TicketCategoryDAO ticketCategoryDAO;

    public TicketDAO() throws IOException {
        dataSource = DBCPDataSource.getInstance();
        eventDAO = new EventDAO();
        customerDAO = new CustomerDAO();
        ticketCategoryDAO = new TicketCategoryDAO();
    }

    /**
     * Retrieve the history of any customer
     */
    public List<Ticket> getAllTickets(Customer customer) throws SQLException {
        List<Ticket> allTickets = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM tickets WHERE customer_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int eventId = resultSet.getInt("event_id");
                String sql0 = "SELECT * FROM events WHERE id = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql0);
                preparedStatement1.setInt(1, eventId);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()) {
                    allTickets.add(new Ticket(resultSet1.getInt(1),
                            new Event(resultSet1.getInt(1),
                                    resultSet1.getString(2),
                                    resultSet1.getDate(3),
                                    resultSet1.getString(4),
                                    resultSet1.getString(5),
                                    resultSet1.getInt(6)),
                            customer,
                            resultSet.getString("qr_code")));
                }
            }
        }
        return allTickets;
    }

    public Ticket createTicket(Customer customer, Event event, String qr_code, TicketType ticketType) throws SQLException {
        Ticket ticket = null;
        if (!ticketsStillAvailable(ticketType))
            return ticket;
        try (Connection connection = dataSource.getConnection()){
        String sql = "INSERT INTO tickets VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, event.getId());
        preparedStatement.setInt(2, customer.getId());
        preparedStatement.setString(4, qr_code);
        preparedStatement.setInt(3, ticketType.getId());
        preparedStatement.setInt(5, 1);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ticket = new Ticket(resultSet.getInt(1), event, customer, qr_code);
        }}
        return ticket;
    }

    private boolean ticketsStillAvailable(TicketType ticketType) throws SQLException {
        int seatsSold = 0;
        try (Connection connection = dataSource.getConnection()){
        String sql = "SELECT COUNT(*) as tickets_sold\n" +
                "FROM tickets\n" +
                "WHERE ticket_category_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, ticketType.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next())
            seatsSold = resultSet.getInt("tickets_sold");


        if (seatsSold < ticketType.getSeatsAvailable())
            return true;}
        return false;
    }


    public void deleteTicket(Ticket ticket) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
        String sql = "DELETE * FROM tickets WHERE customer_id= ? AND event_id= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, ticket.getCustomer().getId());
        preparedStatement.setInt(2, ticket.getEvent().getId());
        preparedStatement.executeUpdate();}
    }


    public boolean checkIfTicketsSold(Event event) throws SQLException, UserException {
        try (Connection connection = dataSource.getConnection()){
        String sql = "SELECT * \n" +
                "FROM tickets\n" +
                "WHERE event_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, event.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next())
            return true;}
        return false;
    }


    public void deleteTicketsForEvent(Event event) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
        String sql = "DELETE FROM tickets\n" +
                "WHERE event_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, event.getId());
        preparedStatement.executeUpdate();}
    }

    public Ticket getTicket(String qrCode) throws SQLException {
        Ticket ticket = null;
        try (Connection connection = dataSource.getConnection()){
        String sql = "SELECT * FROM tickets WHERE qr_code= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, qrCode);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ticket = new Ticket(resultSet.getInt("id"),
                    eventDAO.getEvent(resultSet.getInt("event_id")),
                    customerDAO.getCustomer(resultSet.getInt("customer_id")),
                    qrCode
                    , resultSet.getBoolean("ticket_validity"),
                    ticketCategoryDAO.getTicketType(resultSet.getInt("ticket_category_id"))
            );
        }}
        return ticket;
    }

    public void updateTicket(Ticket ticket) throws SQLException{
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE tickets SET ticket_validity=? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2,ticket.getId());
            preparedStatement.executeUpdate();
        }
    }

}
