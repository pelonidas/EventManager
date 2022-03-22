package com.project.bll;

import com.project.be.TicketType;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IEventManager {

    public void createEvent(String eventTitle, Date dateAndTime, String location, String description, Integer capacity, List<TicketType> ticketTypes) throws SQLException;
}
