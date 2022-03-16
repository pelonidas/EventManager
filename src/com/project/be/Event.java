package com.project.be;

import java.sql.Timestamp;

public class Event {
    private int ID;
    private Timestamp dateAndTime;
    private String location;
    private User participants;      //maybe later create new class Participants and use that class besides User class
    private String notes;

    public Event(int ID, Timestamp dateAndTime, String location, User participants, String notes) {
        this.ID = ID;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.participants = participants;
        this.notes = notes;
    }

    public Event(Timestamp dateAndTime, String location, User participants, String notes) {
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

    public Timestamp getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Timestamp dateAndTime) {
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
}
