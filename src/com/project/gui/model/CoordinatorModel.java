package com.project.gui.model;

import com.project.be.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class CoordinatorModel {
    private ObservableList<Event> allEvents;

    public CoordinatorModel() {
        allEvents = FXCollections.observableArrayList();
        Date d = new Date();
        Event e1 = new Event("test1", d, "poprad", "lorem lorem ipsum");
        Event e2 = new Event("test2", d, "Piestany", "lorem lorem ipsum");
        Event e3 = new Event("test3", d, "kosice", "lorem lorem ipsum");
        Event e4 = new Event("test4", d, "presov", "lorem lorem ipsum");
        Event e5 = new Event("test5", d, "Trnava", "lorem lorem ipsum");
        Event e6 = new Event("test6", d, "Trencin", "lorem lorem ipsum");

        allEvents.add(e1);
        allEvents.add(e2);
        allEvents.add(e3);
        allEvents.add(e4);
        allEvents.add(e5);
        allEvents.add(e6);

    }

    public void addEvent(Event e) {
        allEvents.add(e);
    }
    public void deleteEvent(Event e) {
        allEvents.remove(e);
    }
    public ObservableList<Event> getAllEvents() {
        return allEvents;
    }
}
