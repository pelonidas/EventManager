package com.project.gui.model;

import com.project.be.Event;
import com.project.be.TicketType;
import com.project.bll.EventManager;
import com.project.bll.IEventManager;
import com.project.bll.exceptions.UserException;
import com.project.bll.util.DateTimeConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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

    public void updateEvent(Event event, List<TicketType> ticketTypes) throws SQLException {
        eventManager.editEvent(event);
        eventManager.editTicketTypesForEvent(event,ticketTypes);
    }


    public ObservableList<TicketType> getTicketTypesForEvent(Event e) throws SQLException {
         List<TicketType> ticketTypeList = eventManager.getAllTicketTypesForEvent(e);
         return FXCollections.observableArrayList(ticketTypeList);
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
