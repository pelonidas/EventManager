package com.project.gui.model;


import com.project.be.*;
import com.project.bll.EventManager;
import com.project.bll.IEventManager;
import com.project.bll.exceptions.UserException;
import com.project.bll.util.DateTimeConverter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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

    public Ticket buyTicketForUser(Event selectedEvent, TicketType selectedTicketType, Customer selectedCustomer) throws SQLException {
        return manager.createTicket(selectedTicketType,selectedCustomer,selectedEvent);
    }

    public com.project.be.Event createEvent(String eventTitle, Date dateAndTime, String location, String description, Integer capacity, List<TicketType> ticketTypes) throws SQLException {
        com.project.be.Event createdEvent;
        createdEvent = manager.createEvent(eventTitle, dateAndTime, location, description, capacity, ticketTypes);
        allEvents.add(createdEvent);
        /**Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                     createdEvent = manager.createEvent(eventTitle, dateAndTime, location, description, capacity, ticketTypes);
                     allEvents.add(createdEvent);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });*/
        return createdEvent;
    }

    /**public void refreshData() throws SQLException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    loadData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

    public void deleteEvent(Event e) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    manager.deleteEvent(e);
                } catch (Exception | UserException exception) {
                    System.out.println(exception);
                }
            }
        });
        allEvents.remove(e);

    }

    public void tryToDeleteEvent(Event selectedEvent) throws SQLException, UserException {
        if (!manager.tryToDeleteEvent(selectedEvent))
            allEvents.remove(selectedEvent);
        else
            throw new UserException("Ticket's to event have been sold \n" +
                    "Click continue to delete anyways",new Exception());

    }

    public Date parse_convertDateTime(String dateTime) {
        return DateTimeConverter.parse_convertDateTime(dateTime);
    }

    public LocalDate parseDate(String date){
        return DateTimeConverter.parseDate(date);
    }

    public void updateEvent(Event event, List<TicketType> ticketTypes) throws SQLException {
        manager.editEvent(event);
        manager.editTicketTypesForEvent(event,ticketTypes);
    }

    public int getTotalSeatCount(ObservableList<TicketType> items, int seatsAvailable) {
        int totalSeatCount = 0;
        for (TicketType item : items) {
            totalSeatCount+=item.getSeatsAvailable();
        }
        totalSeatCount+=seatsAvailable;

        return totalSeatCount;
    }

    public void editEvent(Event event) throws SQLException {
        manager.editEvent(event);
    }
}
