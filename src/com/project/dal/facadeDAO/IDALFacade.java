package com.project.dal.facadeDAO;

import com.project.be.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IDALFacade {
    /**
     * Events dao
     * @return
     * @throws SQLException
     */
     List<Event> getAllEvents () throws SQLException;
     Event createEvent (String title, Date dateAndTime, String location, String description, int seatsAvailable) throws SQLException;
     void deleteEvent(Event event) throws SQLException;
     Event editEvent(Event event,String title, Date dateAndTime, String location, String description, int seatsAvailable) throws SQLException;

    /**
     * Customer dao
     * @return
     * @throws SQLException
     */
    List<Customer> getAllCustomers () throws SQLException;
    Customer createCustomer (String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, java.sql.Date birthDate) throws SQLException;
    void deleteCustomer(Customer customer) throws SQLException;
    Customer editCustomer(Customer customer,String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, java.sql.Date birthDate) throws SQLException;

    /**
     * Coordinator dao
     */
    List<Coordinator> getAllCoordinators () throws SQLException;
    Coordinator createCoordinator (String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, java.sql.Date birthDate) throws SQLException;
    void deleteCoordinator(Coordinator coordinator) throws SQLException;
    Coordinator editCoordinator(Coordinator coordinator,String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, java.sql.Date birthDate) throws SQLException;

    /**
     * Tickets Dao
     */
    List<Ticket>getAllTickets(Customer customer)throws SQLException;
    Ticket createTicket(Customer customer, Event event,String qr_code, int ticketCategory) throws SQLException;
    void deleteTicket(Ticket ticket)throws SQLException;

    /**
     * TicketCategoryDao
     */
    List<TicketType> getAllTicketTypes ()throws SQLException;
    TicketType createTicketType (String title,double price, String benefits,int seatsAvailable )throws SQLException;
    TicketType editTicketType(TicketType ticketType,String title,double price, String benefits,int seatsAvailable) throws SQLException;
    void deleteTicketType(TicketType ticketType)throws SQLException;

}
