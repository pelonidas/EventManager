package com.project.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.project.be.*;
import com.project.bll.exceptions.UserException;
import com.project.dal.connectorDAO.DBConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    DBConnector dbConnector;
    public TicketDAO() throws IOException {
        dbConnector = new DBConnector();
    }
    /**
     * Retrieve the history of any customer
     * */
    public List<Ticket>getAllTickets(Customer customer)throws SQLException {
        List<Ticket>allTickets= new ArrayList<>();
        try (Connection connection = dbConnector.getConnection()){
            String sql = "SELECT * FROM tickets WHERE customer_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,customer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int eventId = resultSet.getInt("event_id");
                String sql0 = "SELECT * FROM events WHERE id = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql0);
                preparedStatement1.setInt(1,eventId);
                ResultSet resultSet1= preparedStatement1.executeQuery();
                while (resultSet1.next()){
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

    public Ticket createTicket (Customer customer, Event event,String qr_code,TicketType ticketType)throws SQLException{
        Ticket ticket=null;
        if(!ticketsStillAvailable(ticketType))
            return ticket;
        try (Connection connection = dbConnector.getConnection()){
          String sql = "INSERT INTO tickets VALUES (?,?,?,?)";
          PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
          preparedStatement.setInt(1,event.getId());
          preparedStatement.setInt(2,customer.getId());
          preparedStatement.setString(4,qr_code);
          preparedStatement.setInt(3,ticketType.getId());
          ResultSet resultSet = preparedStatement.executeQuery();
          while (resultSet.next()){
              ticket= new Ticket(resultSet.getInt(1),event,customer,qr_code);
          }
        }
        return ticket;
    }

    private boolean ticketsStillAvailable(TicketType ticketType) throws SQLException {
        int seatsSold = 0;
        try(Connection connection = dbConnector.getConnection()){
            String sql = "SELECT COUNT(*) as tickets_sold\n" +
                         "FROM tickets\n" +
                         "WHERE ticket_category_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,ticketType.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                seatsSold = resultSet.getInt("tickets_sold");


            if (seatsSold<ticketType.getSeatsAvailable())
                return true;
            return false;
        }
    }

    public void deleteTicket(Ticket ticket)throws SQLException{
        try (Connection connection = dbConnector.getConnection()){
            String sql = "DELETE * FROM tickets WHERE customer_id= ? AND event_id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,ticket.getCustomer().getId());
            preparedStatement.setInt(2,ticket.getEvent().getId());
            preparedStatement.executeUpdate();
        }
    }


    public boolean checkIfTicketsSold(Event event) throws SQLException, UserException {
        try(Connection connection = dbConnector.getConnection()){
            String sql = "SELECT * \n" +
                    "FROM tickets\n" +
                    "WHERE event_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,event.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return true;
            return false;


        }
    }

    public void deleteTicketsForEvent(Event event) throws SQLException {
        try(Connection connection = dbConnector.getConnection()){
            String sql = "DELETE FROM tickets\n" +
                    "WHERE event_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,event.getId());
            preparedStatement.executeUpdate();
        }
    }
}
