package com.project.gui.model;


import com.project.be.*;
import com.project.bll.EventManager;
import com.project.bll.IEventManager;
import com.project.bll.exceptions.UserException;
import com.project.dal.facadeDAO.DALController;
import com.project.dal.facadeDAO.IDALFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ManageEventsModel {

    IEventManager manager;
    ObservableList<com.project.be.Event> allEvents ;
    ObservableList<Customer> allUsers;

    public ManageEventsModel() throws IOException, SQLException {
        manager = EventManager.getInstance();
        allEvents = FXCollections.observableArrayList();
        allUsers = FXCollections.observableArrayList();

        loadData();
    }

    private void loadData() throws SQLException {
        allEvents.setAll(manager.getAllEvents());

        allUsers.setAll(manager.getAllCustomers());
    }

    public ObservableList<Event> getAllEvents() throws SQLException {
        return allEvents;
    }

    public ObservableList<Customer> getAllUsers() throws SQLException {
        return allUsers;
    }

    public ObservableList getTicketsForEvent(Event e) throws SQLException {
        return FXCollections.observableArrayList(manager.getAllTicketTypesForEvent(e));
    }

    public Ticket buyTicketForUser(Event selectedEvent, TicketType selectedTicketType, Customer selectedCustomer) throws SQLException {
        return manager.createTicket(selectedTicketType,selectedCustomer,selectedEvent);
    }

    public void createEvent(String eventTitle, Date dateAndTime, String location, String description, Integer capacity, List<TicketType> ticketTypes) throws SQLException {
        Event createdEvent = manager.createEvent(eventTitle, dateAndTime, location, description, capacity, ticketTypes);
        allEvents.add(createdEvent);

    }

    public void refreshData() throws SQLException {
        loadData();
    }

    public void deleteEvent(Event e) {
        try {
            manager.deleteEvent(e);
        } catch (Exception | UserException exception) {
            System.out.println(exception);
        }
        allEvents.remove(e);

    }

    public void tryToDeleteEvent(Event selectedEvent) throws SQLException, UserException {
        if (!manager.tryToDeleteEvent(selectedEvent))
            allEvents.remove(selectedEvent);
        else
            throw new UserException("Ticket's to event have been sold \n" +
                    "Click continue to delete anyways",new Exception());
    }
}
