package com.project.bll;

import com.project.be.*;
import com.project.dal.facadeDAO.DALController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class EventManager implements IEventManager{
    private static EventManager eventManager;

    private EventManager() throws IOException {
        DALController dalController = new DALController();
    }

    public static EventManager getInstance() throws IOException {
        if (eventManager == null){
            eventManager = new EventManager();
        }
        return eventManager;
    }

    @Override
    public List<Event> getAllEvents() throws SQLException {
        return null;
    }

    @Override
    public Event createEvent(String title, Date dateAndTime, String location, String description, int seatsAvailable) throws SQLException {
        return null;
    }

    @Override
    public void deleteEvent(Event event) throws SQLException {

    }

    @Override
    public Event editEvent(Event event, String title, Date dateAndTime, String location, String description, int seatsAvailable) throws SQLException {
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        return null;
    }

    @Override
    public Customer createCustomer(String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, java.sql.Date birthDate) throws SQLException {
        return null;
    }

    @Override
    public void deleteCustomer(Customer customer) throws SQLException {

    }

    @Override
    public Customer editCustomer(Customer customer, String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, java.sql.Date birthDate) throws SQLException {
        return null;
    }

    @Override
    public List<Coordinator> getAllCoordinators() throws SQLException {
        return null;
    }

    @Override
    public Coordinator createCoordinator(String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, java.sql.Date birthDate) throws SQLException {
        return null;
    }

    @Override
    public void deleteCoordinator(Coordinator coordinator) throws SQLException {

    }

    @Override
    public Coordinator editCoordinator(Coordinator coordinator, String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, java.sql.Date birthDate) throws SQLException {
        return null;
    }

    @Override
    public List<Ticket> getAllTickets(Customer customer) throws SQLException {
        return null;
    }

    @Override
    public Ticket createTicket(Customer customer, Event event, String qr_code, int ticketCategory) throws SQLException {
        return null;
    }

    @Override
    public void deleteTicket(Ticket ticket) throws SQLException {

    }

    @Override
    public List<TicketType> getAllTicketTypes() throws SQLException {
        return null;
    }

    @Override
    public TicketType createTicketType(String title, double price, String benefits, int seatsAvailable) throws SQLException {
        return null;
    }

    @Override
    public TicketType editTicketType(TicketType ticketType, String title, double price, String benefits, int seatsAvailable) throws SQLException {
        return null;
    }

    @Override
    public void deleteTicketType(TicketType ticketType) throws SQLException {

    }
}
