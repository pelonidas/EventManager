package com.project.gui.model;

import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.dal.facadeDAO.DALController;
import com.project.dal.facadeDAO.IDALFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class ManageUsersModel {
    IDALFacade EMFacade;
    ObservableList<Customer> allCustomers;
    ObservableList<Coordinator>allCoordinators;

    public ManageUsersModel() throws IOException {
        EMFacade = new DALController();
    }

    public ObservableList<com.project.be.Customer> getAllCustomers() throws SQLException {
        allCustomers= FXCollections.observableArrayList();
        allCustomers.addAll(EMFacade.getAllCustomers());
        return allCustomers;
    }

    public ObservableList<com.project.be.Coordinator> getAllCoordinators() throws SQLException {
        allCoordinators= FXCollections.observableArrayList();
        allCoordinators.addAll(EMFacade.getAllCoordinators());
        return allCoordinators;
    }
}
