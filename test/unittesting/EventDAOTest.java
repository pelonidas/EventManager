package unittesting;

import com.project.be.Event;
import com.project.bll.util.DateTimeConverter;
import com.project.dal.EventDAO;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EventDAOTest {
    
    @Test
    void crud() throws IOException, SQLException {
        //Triple A pattern

        //Arrangement - setup of necessary objects for test
        EventDAO eventDAO = new EventDAO();

        //Creation of event
        String eventName = "A test event :)";
        Date date = DateTimeConverter.convertToDate(LocalDate.now());
        String location = "Beverly hills 101";
        String description = "A little event just for testing purposes";
        int seatCount = 10;
        Event eventExpected = new Event(1,eventName,date,location,description,seatCount);

        //Updating of event
        String updatedTitle = "A different test event name";
        String updatedLocation = "Esbjerg";
        String updatedDescription = "updated description";

        Event updatedEventExpected = new Event(1,updatedTitle,date,updatedLocation,updatedDescription,seatCount);

        //Deletion of event
        Event deletedEventExpected = null;


        //Act - the method run
        Event eventActual = eventDAO.createEvent(eventName,date,location,description,seatCount, null);

        updatedEventExpected.setId(eventActual.getId());
        Event updatedEventActual = eventDAO.editEvent(updatedEventExpected);
        eventDAO.deleteEvent(updatedEventActual);
        Event deletedEventActual = eventDAO.getEventByID(eventActual.getId());

        //Assertion - check if expected values match actual values

        assertAll("Test the CRUD operations of the event data access object",
                () -> assertEquals(eventExpected,eventActual),
                () -> assertEquals(updatedEventExpected,updatedEventActual),
                () -> assertEquals(deletedEventExpected,deletedEventActual));

    }
}