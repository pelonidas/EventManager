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

public class ManageEventsModel {

    IEventManager manager;
    ObservableList<com.project.be.Event> allEvents ;
    ObservableList<User> allUsers;

    public ManageEventsModel() throws IOException {
        manager = EventManager.getInstance();
        loadData();
    }

    private void loadData() {
        allEvents = FXCollections.observableArrayList();
        allUsers = FXCollections.observableArrayList();
    }

    public ObservableList<com.project.be.Event> getAllEvents() throws SQLException {
        allEvents.addAll(manager.getAllEvents());
        return allEvents;
    }

    public ObservableList getAllUsers() throws SQLException {
        allUsers.addAll(manager.getAllCustomers());
        return allUsers;
    }

    public ObservableList getTicketsForEvent(Event e) throws SQLException {
        return FXCollections.observableArrayList(manager.getAllTicketTypesForEvent(e));
    }

    public Ticket buyTicketForUser(Event selectedEvent, TicketType selectedTicketType, Customer selectedCustomer) throws SQLException {
        return manager.createTicket(selectedTicketType,selectedCustomer,selectedEvent);
    }
}
