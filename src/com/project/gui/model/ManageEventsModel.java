package com.project.gui.model;


import com.project.be.Event;
import com.project.dal.facadeDAO.DALController;
import com.project.dal.facadeDAO.IDALFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class ManageEventsModel {
    IDALFacade EMFacade;
    ObservableList<com.project.be.Event> allEvents ;

    public ManageEventsModel() throws IOException {
        EMFacade = new DALController();
    }

    public ObservableList<com.project.be.Event> getAllEvents() throws SQLException {
        allEvents= FXCollections.observableArrayList();
        allEvents.addAll(EMFacade.getAllEvents());
        return allEvents;
    }
}
