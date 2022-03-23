package com.project.be;

public class Ticket {
    private Event event;
    Customer customer;
    int id ;
    private TicketType ticketType;
    private String code; //the code used for generating barcode and qr code

    //Default ticket constructor
    public Ticket(int id, Event event,Customer customer,String code){
        this.event=event;
        this.id=id;
        this.customer= customer;
        this.code = code;
        this.ticketType = new TicketType(0,"Entry","No benefits, just an entry ticket",100.00, Integer.MAX_VALUE);
    }

    public Ticket(Event event, Customer customer, int id, TicketType ticketType, String code) {
        this.event = event;
        this.customer = customer;
        this.id = id;
        this.ticketType = ticketType;
        this.code = code;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
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
}
