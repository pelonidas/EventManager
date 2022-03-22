package com.project.gui.model;

import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.be.Event;
import com.project.be.User;
import com.project.dal.facadeDAO.DALController;
import com.project.dal.facadeDAO.IDALFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class CustomerModel {
    IDALFacade EMFacade;
    ObservableList<com.project.be.Customer> allCustomers ;

    public CustomerModel() throws IOException {
        EMFacade = new DALController();
    }

    public ObservableList<com.project.be.Customer> getAllCustomers() throws SQLException {
        allCustomers= FXCollections.observableArrayList();
        allCustomers.addAll(EMFacade.getAllCustomers());
        return allCustomers;
    }

    public void createCustomer(String firstName, String lastName, String userName, String password, String email, String address, int phoneNumber, LocalDate birthDate) throws SQLException {
        EMFacade.createCustomer(firstName,lastName,userName,password,email,address,phoneNumber, java.sql.Date.valueOf(birthDate));
    }
}
