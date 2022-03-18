package com.project.gui.model;

import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.be.Event;
import com.project.be.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerModel {
    private ObservableList<Event> eventObservableList;
    private ObservableList<User> userObservableList;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");







    public ObservableList<Event> getEventObservableList() {
        Event event1 = new Event("Event 1", LocalDate.now(), "Esbjerg", "note 1");
        Event event2 = new Event("Event 2", LocalDate.parse("04-03-2022", formatter), "Puchov", "note 2");
        Event event3 = new Event("Event 3", LocalDate.parse("12-08-2000", formatter), "Poprad", "note 3");
        Event event4 = new Event("Event 4", LocalDate.parse("03-01-2014", formatter), "Snina", "note 4");
        Event event5 = new Event("Event 5", LocalDate.parse("20-06-2020", formatter), "Michalovce", "note 5");
        Event event6 = new Event("Event 6", LocalDate.parse("12-11-2014", formatter), "Dolna Poton", "note 6");
        Event event7 = new Event("Event 7", LocalDate.parse("09-10-2018", formatter), "Nova Ves", "note 7");

        User user1 = new Customer("Sano", "lochnes444@gmail.dk");
        User user2 = new Coordinator("Samouel","lochnes444@gmail.dk");
        User user3 = new Customer("Loptos","lochnes444@gmail.dk");
        User user4 = new Customer("Geno","lochnes444@gmail.dk") ;

        event1.addUser(user1);
        event1.addUser(user2);
        event1.addUser(user3);

        event2.addUser(user4);

        eventObservableList = FXCollections.observableArrayList();
        //MOCK DATA
        eventObservableList.add(event1);
        eventObservableList.add(event2);
        eventObservableList.add(event3);
        eventObservableList.add(event4);
        eventObservableList.add(event5);
        eventObservableList.add(event6);
        eventObservableList.add(event7);



        return eventObservableList;
    }
}
