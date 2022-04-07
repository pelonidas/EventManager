package com.project.dal;

import com.project.be.Event;
import com.project.be.TicketType;
import com.project.dal.connectorDAO.DBConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketCategoryDAO  {
    DBConnector dbConnector;
    public TicketCategoryDAO() throws IOException {
        dbConnector = new DBConnector();
    }
    public List<TicketType> getAllTicketTypes ()throws SQLException{
       List<TicketType>allTicketTypes = new ArrayList<>();
        try (Connection connection= dbConnector.getConnection()){
            String sql ="SELECT * FROM categories_ticket";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()){
                allTicketTypes.add(new TicketType(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("benefits"),
                        resultSet.getInt("price"),
                        resultSet.getInt("seats_available")));
            }
        }
        return allTicketTypes;
    }
    public TicketType createTicketType (String title,double price, String benefits,int seatsAvailable )throws SQLException{
        TicketType ticketType = null;
        try (Connection connection= dbConnector.getConnection()){
            String sql = "INSERT INTO categories_ticket VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,title);
            preparedStatement.setDouble(2,price);
            preparedStatement.setString(3,benefits);
            preparedStatement.setInt(4,seatsAvailable);
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
              ticketType = new TicketType(resultSet.getInt(1),title,benefits,price,seatsAvailable);
            }
        }
        return  ticketType;
    }

    public TicketType editTicketType(TicketType ticketType,String title,double price, String benefits,int seatsAvailable)throws SQLException{
        try (Connection connection = dbConnector.getConnection()){
            String sql = "UPDATE categories_ticket SET title=?, price=?, benefits=?, seats_available=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,title);
            preparedStatement.setDouble(2,price);
            preparedStatement.setString(3,benefits);
            preparedStatement.setInt(4,seatsAvailable);
            preparedStatement.setInt(5,ticketType.getId());

            ticketType.setTitle(title);
            ticketType.setPrice(price);
            ticketType.setBenefits(benefits);
            ticketType.setSeatsAvailable(seatsAvailable);

            preparedStatement.executeUpdate();

        }
        return ticketType;
    }

    public void deleteTicketType(TicketType ticketType)throws SQLException{
        try (Connection connection = dbConnector.getConnection()){
            String sql = "DELETE FROM categories_ticket WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,ticketType.getId());
            preparedStatement.executeUpdate();
        }
    }


    /**
     * TODO make it create these ticket types for this specific event only
     * @param ticketTypes
     * @param id
     * @throws SQLException
     */
    public void createMultipleTicketTypes(List<TicketType> ticketTypes, int id) throws SQLException {
        try (Connection connection = dbConnector.getConnection()){
            String sql = "INSERT INTO categories_ticket VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (TicketType ticketType : ticketTypes) {
                preparedStatement.setString(1,ticketType.getTitle());
                preparedStatement.setDouble(2,ticketType.getPrice());
                preparedStatement.setString(3,ticketType.getBenefits());
                preparedStatement.setInt(4,ticketType.getSeatsAvailable());
                preparedStatement.setInt(5,id);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

        }
    }


    public List<TicketType> getAllTicketTypesForEvent(Event selectedEvent) throws SQLException {
        List<TicketType> ticketsForEvent = new ArrayList<>();
        try(Connection connection = dbConnector.getConnection()){
            String sql = "SELECT * FROM categories_ticket\n" +
                    "WHERE event_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,selectedEvent.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
            String title = resultSet.getString("title");
            String benefits = resultSet.getString("benefits");
            double price = resultSet.getDouble("price");
            int seatsAvaible = resultSet.getInt("seats_available");
            int id = resultSet.getInt("id");

            TicketType ticketType = new TicketType(id,title,benefits,price,seatsAvaible);
            ticketsForEvent.add(ticketType);
            }
            return ticketsForEvent;
        }
    }

    public void editTicketTypesForEvent(Event event, List<TicketType> ticketTypes) throws SQLException {
        List<TicketType> newTicketTypes = new ArrayList<>();
        List<TicketType> outDatedTicketTypes = getAllTicketTypesForEvent(event);
        List<TicketType> toBeDeleted = new ArrayList<>();
        List<TicketType> toBeUpdated = new ArrayList<>();

        for (TicketType ticketType : ticketTypes) {
            if (ticketType.getId()==0)
                newTicketTypes.add(ticketType);
            else if(ticketAlreadyExists(ticketType,outDatedTicketTypes)){
                TicketType checkedTicketType = checkIfViableSeatCount(ticketType);
                System.out.println(checkedTicketType.getSeatsAvailable());
                toBeUpdated.add(checkedTicketType);
            }

        }

        for (TicketType outDatedTicketType : outDatedTicketTypes) {
            if (!ticketAlreadyExists(outDatedTicketType,ticketTypes))
                toBeDeleted.add(outDatedTicketType);
        }

        for (TicketType ticketType : toBeDeleted) {
            deleteTicketType(ticketType);
        }

        for (TicketType ticketType : toBeUpdated) {
            updateTicketType(ticketType);
        }

        createMultipleTicketTypes(newTicketTypes,event.getId());


    }

    private TicketType checkIfViableSeatCount(TicketType ticketType) throws SQLException {
        int oldTypeSeatCount = getOldTypeSeatCount(ticketType.getId());

        TicketType checkedTicket = ticketType;

        if (oldTypeSeatCount == ticketType.getSeatsAvailable()){
            return checkedTicket;
        }

        try(Connection connection = dbConnector.getConnection()){
            String sql = "SELECT COUNT(*) as [count]\n" +
                    "FROM tickets\n" +
                    "WHERE ticket_category_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,ticketType.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            int soldTicketsOfType = 0;
            if (resultSet.next())
                soldTicketsOfType = resultSet.getInt("count");
            System.out.println(soldTicketsOfType);
            if (checkedTicket.getSeatsAvailable()<=soldTicketsOfType)
                checkedTicket.setSeatsAvailable(soldTicketsOfType);
            return checkedTicket;
        }
    }

    private int getOldTypeSeatCount(int id) throws SQLException {
        int oldSeatCount = 0;
        try(Connection connection = dbConnector.getConnection()){
            String sql = "SELECT seats_available\n" +
                    "FROM categories_ticket\n" +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                oldSeatCount = resultSet.getInt("seats_available");
        }
        return oldSeatCount;
    }

    private void updateTicketType(TicketType ticketType) throws SQLException {
        try(Connection connection = dbConnector.getConnection()){
            String sql = "UPDATE categories_ticket SET title = ?, price = ?,benefits = ?,seats_available = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,ticketType.getTitle());
            preparedStatement.setDouble(2,ticketType.getPrice());
            preparedStatement.setString(3, ticketType.getBenefits());
            preparedStatement.setInt(4,ticketType.getSeatsAvailable());
            preparedStatement.setInt(5,ticketType.getId());

            preparedStatement.executeUpdate();
        }
    }

    private boolean ticketAlreadyExists(TicketType ticketType, List<TicketType> outDatedTicketTypes) {
        for (TicketType outDatedTicketType : outDatedTicketTypes) {
            if (ticketType.getId()==outDatedTicketType.getId())
                return true;
        }
        return false;
    }

    public void deleteTicketTypesForEvent(Event event) throws SQLException {
        try(Connection connection = dbConnector.getConnection()){
            String sql = "DELETE FROM categories_ticket\n" +
                    "WHERE event_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,event.getId());
            preparedStatement.executeUpdate();
        }
    }

}
