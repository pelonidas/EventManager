package com.project.dal.facadeDAO;

import com.project.be.*;
import com.project.bll.exceptions.UserException;

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
     Event createEvent(String title, Date dateAndTime, String location, String description, int seatsAvailable, List<TicketType> ticketTypes) throws SQLException;
     void deleteEvent(Event event) throws SQLException, UserException;
     Event editEvent(Event event) throws SQLException;

    /**
     * Customer dao
     * @return
     * @throws SQLException
     */
    List<Customer> getAllCustomers () throws SQLException;
    Customer createCustomer (String firstName, String lastName, String email, int phoneNumber) throws SQLException, UserException;
    void deleteCustomer(Customer customer) throws SQLException;
    Customer editCustomer(Customer customer) throws SQLException;
    List<Customer> getAllCustomersFromSameEvent(int eventId) throws Exception;

    /**
     * Coordinator dao
     */
    List<Coordinator> getAllCoordinators () throws SQLException;
    Coordinator createCoordinator (String firstName, String lastName, String userName, String passWord, String email, int phoneNumber) throws UserException, SQLException;
    void deleteCoordinator(Coordinator coordinator) throws SQLException;
    Coordinator editCoordinator(Coordinator coordinator) throws SQLException;

    /**
     * Tickets Dao
     */
    List<Ticket>getAllTickets(Customer customer)throws SQLException;
    void deleteTicket(Ticket ticket)throws SQLException;
    Ticket createTicket(TicketType ticketType, User user,Event selectedEvent) throws SQLException;
    boolean tryToDeleteEvent(Event selectedEvent) throws SQLException, UserException;


    /**
     * TicketCategoryDao
     */
    List<TicketType> getAllTicketTypes ()throws SQLException;
    TicketType editTicketType(TicketType ticketType,String title,double price, String benefits,int seatsAvailable) throws SQLException;
    void deleteTicketType(TicketType ticketType) throws SQLException, UserException;
    void createMultipleTicketTypes(List<TicketType> ticketTypes, int id) throws SQLException;
    void editTicketTypesForEvent(Event event, List<TicketType> ticketTypes) throws SQLException, UserException;


    Coordinator logInCoordinatorCredentials(String userName)throws SQLException,UserException;
    Admin logInAdminCredentials(String userName)throws SQLException,UserException;
    List<TicketType> getAllTicketTypesForEvent(Event selectedEvent) throws SQLException;

    List<Admin>getAllAdmins() throws SQLException, UserException;

    Ticket getTicket(String qrCode) throws SQLException;

     void updateTicket(Ticket ticket) throws SQLException;
}
