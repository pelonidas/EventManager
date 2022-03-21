package com.project.be;

public class Ticket {
    private int seat,row;
    private Event event;
    private int unitPrice;

    private String code; //the code used for generating barcode and qr code

    public Ticket(Event event,int seat, int row, int unitPrice,String code){
        this.event=event;
        this.seat=seat;
        this.row=row;
        this.unitPrice=unitPrice;
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

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCode() {
        return code;
    }
}
