package com.project.bll;

import com.project.be.Event;
import com.project.be.TicketType;
import com.project.dal.EventDAO;
import com.project.dal.TicketCategoryDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class EventManager implements IEventManager{

    private static IEventManager instance;

    private EventDAO eventDAO;
    private TicketCategoryDAO ticketCategoryDAO;

    private EventManager() throws IOException {
        eventDAO = new EventDAO();
        ticketCategoryDAO = new TicketCategoryDAO();
    }

    public static IEventManager getInstance() throws IOException {
        if (instance==null)
            instance = new EventManager();
        return instance;
    }

    @Override
    public void createEvent(String eventTitle, Date dateAndTime, String location, String description, Integer capacity, List<TicketType> ticketTypes) throws SQLException {
        Event event = eventDAO.createEvent(eventTitle,dateAndTime,location,description,capacity);
        if (event==null)
            return;
        ticketCategoryDAO.createMultipleTicketTypes(ticketTypes);

    }
}
