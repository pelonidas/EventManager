package com.project.be;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {

    private int id;
    private String title;
    private Date dateAndTime;
    private String location;
    private String description;
    private List<User> participants;
    private int maxCapacity;
    private int seatsAvailable;


    public Event(int id, String title, Date dateAndTime, String location, String description) {
        this.id = id;
        this.title = title;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.description = description;

    }

    public Event(String name, Date dateAndTime, String location, String des) {
        this.title = name;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.description = des;
        this.participants = new ArrayList<>();
    }

    public Event(int id, String title, Date dateAndTime, String location, String description,int seatsAvailable) {
        this.id = id;
        this.title = title;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.description = description;
        this.seatsAvailable= seatsAvailable;
    }

    //sulinel messi
    public Event(String title, Date dateAndTime, String location, String description, int seatsAvailable) {
        this.title = title;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.description = description;
        this.seatsAvailable= seatsAvailable;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }
}
