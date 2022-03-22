package com.project.gui.model;

import com.project.be.TicketType;
import com.project.bll.EventManager;
import com.project.bll.IEventManager;
import com.project.bll.util.DateTimeConverter;

import java.io.IOException;
import java.sql.SQLException;
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

    public void createEvent(String eventTitle, Date dateAndTime, String location, String description, Integer capacity, List<TicketType> ticketTypes) throws SQLException {
        eventManager.createEvent(eventTitle,dateAndTime,location,description,capacity,ticketTypes);
    }
}
