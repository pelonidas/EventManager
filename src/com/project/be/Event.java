package com.project.be;

import java.sql.Date;
import java.sql.Timestamp;

public class Event {
    private int ID;
    private Date dateAndTime;  //datatype that should work smoothly with SQL datatype DATE
    private String location;
    private User participants;      //maybe later create new class Participants and use that class besides User class
    private String notes;

    public Event(int ID, Date dateAndTime, String location, User participants, String notes) {
        this.ID = ID;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.participants = participants;
        this.notes = notes;
    }

    public Event(Date dateAndTime, String location, User participants, String notes) {
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

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
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
