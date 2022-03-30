package com.project.dal.facadeDAO;

import com.project.be.*;
import com.project.bll.exceptions.UserException;
import com.project.dal.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DALController implements IDALFacade {
    CoordinatorDAO coordinatorDAO ;
    CustomerDAO customerDAO ;
    EventDAO eventDAO ;
    TicketDAO ticketDAO;
    UsersDAO adminDAO;
    TicketCategoryDAO ticketCategoryDAO;

    public DALController() throws IOException {
        eventDAO = new EventDAO();
        adminDAO= new UsersDAO();
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
    public Customer editCustomer(Customer customer) throws SQLException {
        return customerDAO.editCustomer(customer);
    }

    @Override
    public List<Customer> getAllCustomersFromSameEvent(int eventId) throws Exception {
        return customerDAO.getAllCustomersFromSameEvent(eventId);
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
    public Coordinator editCoordinator(Coordinator coordinator) throws SQLException {
        return coordinatorDAO.editCoordinator(coordinator);
    }

    @Override
    public List<Ticket> getAllTickets(Customer customer) throws SQLException {
        return ticketDAO.getAllTickets(customer);
    }

    @Override
    public void deleteTicket(Ticket ticket) throws SQLException {
        ticketDAO.deleteTicket(ticket);
    }

    @Override
    public Ticket createTicket(TicketType ticketType, User user, Event selectedEvent) throws SQLException {
        String uniqueID = UUID.randomUUID().toString();
        return ticketDAO.createTicket(((Customer) user),selectedEvent,uniqueID,ticketType);
    }

    @Override
    public List<TicketType> getAllTicketTypes() throws SQLException {
        return ticketCategoryDAO.getAllTicketTypes();
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

    @Override
    public Customer logInCustomerCredentials(String userName) throws SQLException, UserException {
        return customerDAO.logInCredentials(userName);
    }

    @Override
    public Coordinator logInCoordinatorCredentials(String userName) throws SQLException, UserException {
        return coordinatorDAO.logInCredentials(userName);
    }

    @Override
    public Admin logInAdminCredentials(String userName) throws SQLException, UserException {
        return adminDAO.logInAdminCredentials(userName);
    }

    @Override
    public List<TicketType> getAllTicketTypesForEvent(Event selectedEvent) throws SQLException {
        return ticketCategoryDAO.getAllTicketTypesForEvent(selectedEvent);
    }


}
