package com.project.be;

public class Ticket {
    private int seat,row;
    private Event event;

    private String code; //the code used for generating barcode and qr code

    public Ticket(Event event,int seat, int row,String code){
        this.event=event;
        this.seat=seat;
        this.row=row;
        this.code = code;
    }

    public Ticket(Event event, String code) {
        this.event = event;
        this.code = code;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getCode() {
        return code;
    }
}
