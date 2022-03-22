package com.project.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.project.be.Ticket;
import com.project.be.TicketType;
import com.project.dal.connectorDAO.DBConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketCategoryDAO {
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

    public void createMultipleTicketTypes(List<TicketType> ticketTypes) throws SQLException {
        try (Connection connection = dbConnector.getConnection()){
            String sql = "INSERT INTO categories_ticket VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (TicketType ticketType : ticketTypes) {
                preparedStatement.setString(1,ticketType.getTitle());
                preparedStatement.setDouble(2,ticketType.getPrice());
                preparedStatement.setString(3,ticketType.getBenefits());
                preparedStatement.setInt(4,ticketType.getSeatsAvailable());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

        }
    }


}
