package com.project.bll;

import com.project.be.*;
import com.project.bll.exceptions.UserException;
import com.project.dal.facadeDAO.DALController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class EventManager implements IEventManager{

    private static EventManager eventManager;

    DALController dalController;

    private EventManager() throws IOException {
        dalController = new DALController();
    }

    public static EventManager getInstance() throws IOException {
        if (eventManager == null){
            eventManager = new EventManager();
        }
        return eventManager;
    }

    @Override
    public List<Event> getAllEvents() throws SQLException {
        return dalController.getAllEvents();
    }

    @Override
    public Event createEvent(String title, Date dateAndTime, String location, String description, int seatsAvailable, List<TicketType> ticketTypes) throws SQLException {
        Event event = dalController.createEvent(title,dateAndTime,location,description,seatsAvailable);
        if (event!=null)
            dalController.createMultipleTicketTypes(ticketTypes,event.getId());
        return null;
    }

    @Override
    public void deleteEvent(Event event) throws SQLException {
        dalController.deleteEvent(event);
    }

    @Override
    public Event editEvent(Event event, String title, Date dateAndTime, String location, String description, int seatsAvailable) throws SQLException {
        if (event != null)
            return dalController.editEvent(event, title, dateAndTime, location, description, seatsAvailable);
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        return dalController.getAllCustomers();
    }

    @Override
    public Customer createCustomer(String firstName, String lastName, String userName, String passWord, String email, java.sql.Date birthDate) throws UserException {
        return dalController.createCustomer(firstName, lastName, userName, passWord, email, birthDate);
    }

    @Override
    public void deleteCustomer(Customer customer) throws SQLException {
        dalController.deleteCustomer(customer);
    }

    @Override
    public Customer editCustomer(Customer customer, String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, java.sql.Date birthDate) throws SQLException {
        if (customer != null)
            return dalController.editCustomer(customer, firstName, lastName, userName, passWord, email, address, phoneNumber, birthDate);
        return null;
    }

    @Override
    public List<Coordinator> getAllCoordinators() throws SQLException {
        return dalController.getAllCoordinators();
    }

    @Override
    public Coordinator createCoordinator(String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, java.sql.Date birthDate) throws SQLException {
        return dalController.createCoordinator(firstName, lastName, userName, passWord, email, address, phoneNumber, birthDate);
    }

    @Override
    public void deleteCoordinator(Coordinator coordinator) throws SQLException {

        dalController.deleteCoordinator(coordinator);
    }

    @Override
    public Coordinator editCoordinator(Coordinator coordinator, String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, java.sql.Date birthDate) throws SQLException {
        if (coordinator != null)
            return dalController.editCoordinator(coordinator, firstName, lastName, userName, passWord, email, address, phoneNumber, birthDate);
        return null;
    }

    @Override
    public List<Ticket> getAllTickets(Customer customer) throws SQLException {
        return dalController.getAllTickets(customer);
    }

    @Override
    public Ticket createTicket(Customer customer, Event event, String qr_code, int ticketCategory) throws SQLException {
        if (customer != null)
            dalController.createTicket(customer, event, qr_code, ticketCategory);
        return null;
    }

    @Override
    public void deleteTicket(Ticket ticket) throws SQLException {
        dalController.deleteTicket(ticket);
    }

    @Override
    public List<TicketType> getAllTicketTypes() throws SQLException {
        return dalController.getAllTicketTypes();
    }

    @Override
    public TicketType createTicketType(String title, double price, String benefits, int seatsAvailable) throws SQLException {
        return dalController.createTicketType(title, price, benefits, seatsAvailable);
    }

    @Override
    public TicketType editTicketType(TicketType ticketType, String title, double price, String benefits, int seatsAvailable) throws SQLException {
        if (ticketType != null)
            return dalController.editTicketType(ticketType, title, price, benefits, seatsAvailable);
        return null;
    }

    @Override
    public void deleteTicketType(TicketType ticketType) throws SQLException {
        dalController.deleteTicketType(ticketType);
    }

    @Override
    public void createMultipleTicketTypes(List<TicketType> ticketTypes, int id) throws SQLException {
        dalController.createMultipleTicketTypes(ticketTypes, id);
    }
}
