package com.project.gui.model;

import com.project.be.Coordinator;
import com.project.be.Event;
import com.project.dal.facadeDAO.DALController;
import com.project.dal.facadeDAO.IDALFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

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


}
