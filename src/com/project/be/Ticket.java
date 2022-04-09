package com.project.be;

public class Ticket {
    private Event event;
    private Customer customer;
    private int id ;
    private boolean isValid;
    private TicketType ticketType;

    private String code; //the code used for generating barcode and qr code

    public Ticket(int id, Event event,Customer customer,String code){
        this.event=event;
        this.id=id;
        this.customer= customer;
        this.code = code;
    }
    public Ticket(int id,Event  event,Customer customer,String code,Boolean isValid){
        this.event=event;
        this.id=id;
        this.customer= customer;
        this.code = code;
        this.isValid=isValid;
    }

    public Ticket(int id,Event  event,Customer customer,String code,Boolean isValid,TicketType ticketType){
        this.event=event;
        this.id=id;
        this.customer= customer;
        this.code = code;
        this.isValid=isValid;
        this.ticketType=ticketType;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
}
