package com.project.gui.model;

import com.project.be.Coordinator;
import com.project.bll.EventManager;
import com.project.bll.IEventManager;
import com.project.bll.exceptions.UserException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.sql.SQLException;


public class CoordinatorModel {
    IEventManager manager;
    ObservableList<com.project.be.Coordinator> allCoordinators ;
    private static CoordinatorModel coordinatorModel = null;


    public CoordinatorModel() throws IOException {
        manager = EventManager.getInstance();
    }

    public ObservableList<com.project.be.Coordinator> getAllCoordinators() throws SQLException {
        allCoordinators= FXCollections.observableArrayList();
        allCoordinators.addAll(manager.getAllCoordinators());
        return allCoordinators;
    }

    public Coordinator createCoordinator(String firstName, String lastName, String userName, String password, String email, int phoneNumber) throws SQLException, UserException {
       return manager.createCoordinator(firstName,lastName,userName,password,email,phoneNumber);
    }

    public void deleteCoordinator(Coordinator selectedCoordinator) throws SQLException {
        manager.deleteCoordinator(selectedCoordinator);
    }

    public void editCoordinator(Coordinator coordinator) throws SQLException {
        manager.editCoordinator(coordinator);
    }
    public static CoordinatorModel getInstance() throws IOException {
        if (coordinatorModel == null)
            coordinatorModel = new CoordinatorModel();

        return coordinatorModel;
    }
}
