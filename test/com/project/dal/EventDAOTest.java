package com.project.dal;

import com.project.be.Event;
import com.project.bll.util.DateTimeConverter;
import com.project.dal.connectorDAO.DBConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EventDAOTest {




    @Test
    void crud() throws IOException {
        //Triple A pattern

        //Arrangement - setup of necessary objects for test
        EventDAO eventDAO = new EventDAO();

        String eventName = "A test event :)";
        Date date = DateTimeConverter.convertToDate(LocalDate.now());
        String location = "Beverly hills 101";
        String description = "A little event just for testing purposes";
        int seatCount = 10;

        Event event = new Event(1,eventName,date,location,description,seatCount);

        //No idea wheere to store expected values


        //Act - the method run



        //Assertion - check if expected values match actual values

        assertAll("Test the CRUD operations of the event data access object",
                () -> assertEquals(event,eventDAO.createEvent(eventName,date,location,description,seatCount)));

    }
}