package com.project.dal;

import com.project.be.Customer;
import com.project.be.Event;
import com.project.be.Ticket;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class testDAO {

    public static void main(String[] args) throws IOException, SQLException {
    EventDAO eventDAO = new EventDAO();
        Customer customer= new Customer(6,"sildLasyed","darbouka","nomza","kesoul","ogogo@silamon.dk","space","9999978",Date.valueOf("2020-10-10"),2);
    Event event = new Event(1,"weed party",Date.valueOf("2020-10-10"),"Esbjerg","zevi",18);
        TicketDAO ticketDAO = new TicketDAO();
for (Ticket ticket : ticketDAO.getAllTickets(customer))
    System.out.println(ticket.getEvent().getTitle());
    }}
