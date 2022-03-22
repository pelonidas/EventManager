package com.project.gui.model;

import com.project.be.Coordinator;
import com.project.be.Event;
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

public class CoordinatorModel {
    IDALFacade EMFacade;
    ObservableList<com.project.be.Coordinator> allCoordinators ;

    public CoordinatorModel() throws IOException {
        EMFacade = new DALController();
    }

    public ObservableList<com.project.be.Coordinator> getAllCoordinators() throws SQLException {
        allCoordinators= FXCollections.observableArrayList();
        allCoordinators.addAll(EMFacade.getAllCoordinators());
        return allCoordinators;
    }

    public void createCoordinator(String firstName, String lastName, String userName, String password, String email, String address, int phoneNumber, LocalDate birthDate) throws SQLException {
        EMFacade.createCoordinator(firstName,lastName,userName,password,email,address,phoneNumber,java.sql.Date.valueOf(birthDate));
    }
}
