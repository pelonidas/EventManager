package com.project.dal;

import com.project.be.TicketType;

import java.io.IOException;
import java.sql.SQLException;

public class testDAO {

    public static void main(String[] args) throws IOException, SQLException {
//        TicketCategoryDAO ticketCategoryDAO = new TicketCategoryDAO();
//        for (TicketType ticketType: ticketCategoryDAO.getAllTicketTypes()){
//            System.out.println(ticketType.getTitle());
//        }

        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.getAllCustomersFromSameEvent(2);
    }}
