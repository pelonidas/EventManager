package com.project.be;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Event {
    private int ID;
    private String name;
    private LocalDate dateAndTime;  //datatype that should work smoothly with SQL datatype DATE
    private String location;
    private User participants;      //maybe later create new class Participants and use that class besides User class
    private String notes;

    public Event(int ID,String name, LocalDate dateAndTime, String location, User participants, String notes) {
        this.ID = ID;
        this.name = name;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.participants = participants;
        this.notes = notes;
    }

    public Event(String name, LocalDate dateAndTime, String location, User participants, String notes) {
        this.name = name;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.participants = participants;
        this.notes = notes;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDate getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDate dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getParticipants() {
        return participants;
    }

    public void setParticipants(User participants) {
        this.participants = participants;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
