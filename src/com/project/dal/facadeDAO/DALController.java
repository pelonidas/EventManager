package com.project.dal.facadeDAO;

import com.project.be.*;
import com.project.bll.exceptions.UserException;
import com.project.dal.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DALController implements IDALFacade {
    CoordinatorDAO coordinatorDAO ;
    CustomerDAO customerDAO ;
    EventDAO eventDAO ;
    TicketDAO ticketDAO;
    TicketCategoryDAO ticketCategoryDAO;

    public DALController() throws IOException {
        eventDAO = new EventDAO();
        customerDAO =  new CustomerDAO();
        coordinatorDAO = new CoordinatorDAO();
        ticketDAO = new TicketDAO();
        ticketCategoryDAO = new TicketCategoryDAO();
    }

    @Override
    public List<Event> getAllEvents() throws SQLException {
        return eventDAO.getAllEvents();
    }

    @Override
    public Event createEvent(String title, Date dateAndTime, String location, String description, int seatsAvailable) throws SQLException {
        return eventDAO.createEvent(title,dateAndTime,location,description,seatsAvailable);
    }

    @Override
    public void deleteEvent(Event event) throws SQLException {
        eventDAO.deleteEvent(event);
    }

    @Override
    public Event editEvent(Event event, String title, Date dateAndTime, String location, String description, int seatsAvailable) throws SQLException {
        return eventDAO.editEvent(event,title,dateAndTime,location,description,seatsAvailable);
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }

    @Override
    public Customer createCustomer(String firstName, String lastName, String userName, String passWord, String email,  java.sql.Date birthDate) throws  UserException {
        return customerDAO.createCustomer(firstName,lastName,userName,passWord,email,birthDate);
    }

    @Override
    public void deleteCustomer(Customer customer) throws SQLException {
        customerDAO.deleteCustomer(customer);
    }

    @Override
    public Customer editCustomer(Customer customer, String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, java.sql.Date birthDate) throws SQLException {
        return customerDAO.editCustomer(customer,firstName,lastName,userName,passWord,email,address,phoneNumber,birthDate);
    }

    @Override
    public List<Coordinator> getAllCoordinators() throws SQLException {
        return coordinatorDAO.getAllCoordinators();
    }

    @Override
    public Coordinator createCoordinator(String firstName, String lastName, String userName, String passWord, String email, java.sql.Date birthDate) throws UserException {
        return coordinatorDAO.createCoordinator(firstName,lastName,userName,passWord,email,birthDate);
    }

    @Override
    public void deleteCoordinator(Coordinator coordinator) throws SQLException {
        coordinatorDAO.deleteCoordinator(coordinator);

    }

    @Override
    public Coordinator editCoordinator(Coordinator coordinator, String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, java.sql.Date birthDate) throws SQLException {
        return coordinatorDAO.editCoordinator(coordinator,firstName,lastName,userName,passWord,email,address,phoneNumber,birthDate);
    }

    @Override
    public List<Ticket> getAllTickets(Customer customer) throws SQLException {
        return ticketDAO.getAllTickets(customer);
    }

    @Override
    public Ticket createTicket(Customer customer, Event event, String qr_code, int ticketCategory) throws SQLException {
        return ticketDAO.createTicket(customer,event,qr_code,ticketCategory);
    }

    @Override
    public void deleteTicket(Ticket ticket) throws SQLException {
        ticketDAO.deleteTicket(ticket);
    }

    @Override
    public List<TicketType> getAllTicketTypes() throws SQLException {
        return ticketCategoryDAO.getAllTicketTypes();
    }

    @Override
    public TicketType createTicketType(String title, double price, String benefits, int seatsAvailable) throws SQLException {
        return ticketCategoryDAO.createTicketType(title,price,benefits,seatsAvailable);
    }

    @Override
    public TicketType editTicketType(TicketType ticketType, String title, double price, String benefits, int seatsAvailable) throws SQLException{
        return ticketCategoryDAO.editTicketType(ticketType,title,price,benefits,seatsAvailable);
    }

    @Override
    public void deleteTicketType(TicketType ticketType) throws SQLException {
        ticketCategoryDAO.deleteTicketType(ticketType);
    }

    @Override
    public void createMultipleTicketTypes(List<TicketType> ticketTypes, int id) throws SQLException {
        ticketCategoryDAO.createMultipleTicketTypes(ticketTypes,id);
    }


}
