package com.project.gui.model;

import com.project.be.Event;
import com.project.be.TicketType;
import com.project.bll.EventManager;
import com.project.bll.IEventManager;
import com.project.bll.exceptions.UserException;
import com.project.bll.util.DateTimeConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class EditEventModel {

    IEventManager eventManager;

    public EditEventModel() throws IOException {
        eventManager = EventManager.getInstance();
    }

    public Date parse_convertDateTime(String dateTime) {
        return DateTimeConverter.parse_convertDateTime(dateTime);
    }

    public LocalDate parseDate(String date){
        return DateTimeConverter.parseDate(date);
    }

    public void createEvent(String eventTitle, Date dateAndTime, String location, String description, Integer capacity, List<TicketType> ticketTypes) throws SQLException {
        eventManager.createEvent(eventTitle, dateAndTime, location, description, capacity, ticketTypes);
    }
    public void updateEvent(Event event, String title, Date dateAndTime, String location, String description, int seatsAvailable, List<TicketType> ticketTypes) throws SQLException {
        eventManager.editEvent(event, title ,dateAndTime, location, description, seatsAvailable);
        eventManager.editTicketTypesForEvent(event,ticketTypes);
    }

    public void deleteEvent(Event e) {
        try {
            eventManager.deleteEvent(e);
        } catch (Exception | UserException exception) {
            System.out.println(exception);
        }

    }

    public ObservableList<TicketType> getTicketTypesForEvent(Event e) throws SQLException {
        return FXCollections.observableArrayList(eventManager.getAllTicketTypesForEvent(e));
    }

    public int getTotalSeatCount(ObservableList<TicketType> items, int seatsAvailable) {
        int totalSeatCount = 0;
        for (TicketType item : items) {
            totalSeatCount+=item.getSeatsAvailable();
        }
        totalSeatCount+=seatsAvailable;

        return totalSeatCount;
    }
}
