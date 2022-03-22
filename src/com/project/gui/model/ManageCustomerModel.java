package com.project.gui.model;

import com.project.be.Customer;
import com.project.be.Event;
import com.project.dal.facadeDAO.DALController;
import com.project.dal.facadeDAO.IDALFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class ManageCustomerModel {

    IDALFacade EMFacade;
    ObservableList<Customer> allCustomers ;

    public ManageCustomerModel() throws IOException {
        EMFacade = new DALController();
    }

    public ObservableList<com.project.be.Customer> getAllCustomers() throws SQLException {
        allCustomers= FXCollections.observableArrayList();
        allCustomers.addAll(EMFacade.getAllCustomers());
        return allCustomers;
    }
}
