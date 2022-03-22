package com.project.dal;

import com.project.be.Customer;
import com.project.be.Event;
import com.project.be.Ticket;
import com.project.be.TicketType;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class testDAO {

    public static void main(String[] args) throws IOException, SQLException {
        TicketCategoryDAO ticketCategoryDAO = new TicketCategoryDAO();
        for (TicketType ticketType: ticketCategoryDAO.getAllTicketTypes()){
            System.out.println(ticketType.getTitle());
        }
    }}
