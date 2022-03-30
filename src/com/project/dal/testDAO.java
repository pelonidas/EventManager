package com.project.dal;

public class testDAO {

    public static void main(String[] args) throws Exception {
//        TicketCategoryDAO ticketCategoryDAO = new TicketCategoryDAO();
//        for (TicketType ticketType: ticketCategoryDAO.getAllTicketTypes()){
//            System.out.println(ticketType.getTitle());
//        }

        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.getAllCustomersFromSameEvent(2);
    }}
