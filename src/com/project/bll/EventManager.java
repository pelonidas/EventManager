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
        return event;
    }

    @Override
    public void deleteEvent(Event event) throws SQLException, UserException {
        dalController.deleteEvent(event);
    }

    @Override
    public Event editEvent(Event event) throws SQLException {
        if (event != null)
            return dalController.editEvent(event);
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        return dalController.getAllCustomers();
    }

    @Override
    public Customer createCustomer(String firstName, String lastName, String email, int phoneNumber) throws UserException, SQLException {
        return dalController.createCustomer(firstName, lastName, email, phoneNumber);
    }

    @Override
    public void deleteCustomer(Customer customer) throws SQLException {
        dalController.deleteCustomer(customer);
    }

    @Override
    public Customer editCustomer(Customer customer) throws SQLException {
        if (customer != null)
            return dalController.editCustomer(customer);
        return null;
    }

    @Override
    public List<Customer> getAllCustomersFromSameEvent(int eventId) throws Exception {
        return dalController.getAllCustomersFromSameEvent(eventId);
    }

    @Override
    public List<Coordinator> getAllCoordinators() throws SQLException {
        return dalController.getAllCoordinators();
    }

    @Override
    public Coordinator createCoordinator(String firstName, String lastName, String userName, String passWord, String email, int phoneNumber) throws UserException, SQLException {
        return dalController.createCoordinator(firstName, lastName, userName, passWord, email, phoneNumber);
    }

    @Override
    public void deleteCoordinator(Coordinator coordinator) throws SQLException {

        dalController.deleteCoordinator(coordinator);
    }

    @Override
    public Coordinator editCoordinator(Coordinator coordinator) throws SQLException {
        if (coordinator != null)
            return dalController.editCoordinator(coordinator);
        return null;
    }

    @Override
    public List<Ticket> getAllTickets(Customer customer) throws SQLException {
        return dalController.getAllTickets(customer);
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

    @Override
    public List<TicketType> getAllTicketTypesForEvent(Event selectedEvent) throws SQLException {
        return dalController.getAllTicketTypesForEvent(selectedEvent);
    }

    @Override
    public List<Admin> getAllAdmins() throws SQLException, UserException {
        return dalController.getAllAdmins();
    }

    @Override
    public Ticket createTicket(TicketType ticketType, User user, Event selectedEvent) throws SQLException{
        return dalController.createTicket(ticketType,user,selectedEvent);
    }

    @Override
    public void editTicketTypesForEvent(Event event, List<TicketType> ticketTypes) throws SQLException {
        dalController.editTicketTypesForEvent(event,ticketTypes);
    }

    @Override
    public boolean tryToDeleteEvent(Event selectedEvent) throws SQLException, UserException {
        return dalController.tryToDeleteEvent(selectedEvent);
    }
}
