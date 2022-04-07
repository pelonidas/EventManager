package com.project.gui.model;


import com.project.be.*;
import com.project.bll.EventManager;
import com.project.bll.IEventManager;
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
        loadData();
    }

    private void loadData() throws SQLException {
        allEvents = FXCollections.observableArrayList();
        allEvents.setAll(manager.getAllEvents());

        allUsers = FXCollections.observableArrayList();
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
        allEvents.add(manager.createEvent(eventTitle, dateAndTime, location, description, capacity, ticketTypes));
    }
}
