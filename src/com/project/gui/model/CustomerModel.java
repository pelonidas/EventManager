package com.project.gui.model;

import com.project.be.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerModel {
    private ObservableList<Event> eventObservableList;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");




    public ObservableList<Event> getEventObservableList() {
        eventObservableList = FXCollections.observableArrayList();
        eventObservableList.add(new Event("Event 1", LocalDate.now(), "Esbjerg", "notes and notes"));
        eventObservableList.add(new Event("Event 2", LocalDate.parse("04-03-2022", formatter), "Puchov", "notes and notes"));
        eventObservableList.add(new Event("Event 3", LocalDate.parse("12-08-2000", formatter), "Poprad", "notes and notes"));
        eventObservableList.add(new Event("Event 4", LocalDate.parse("03-01-2014", formatter), "Snina", "notes and notes"));
        eventObservableList.add(new Event("Event 5", LocalDate.parse("20-06-2020", formatter), "Michalovce", "notes and notes"));
        eventObservableList.add(new Event("Event 6", LocalDate.parse("12-11-2014", formatter), "Dolna Poton", "notes and notes"));
        eventObservableList.add(new Event("Event 7", LocalDate.parse("09-10-2018", formatter), "Nova Ves", "notes and notes"));

        return eventObservableList;
    }
}
