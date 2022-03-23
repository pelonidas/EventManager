package com.project.gui.controller;


import com.project.be.Event;
import com.project.be.Ticket;
import com.project.be.TicketType;
import com.project.bll.util.DateTimeConverter;
import com.project.gui.model.CoordinatorModel;
import com.project.gui.model.EditEventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CreateEventViewController implements Initializable {
    @FXML
    private TextField eventTitleTxt, eventCapacityTxt, eventLocationTxt, eventNotesTxt;
    @FXML
    private DatePicker eventPicker;


    private CoordinatorController coordinatorController;
    private EditEventModel editEventModel;

    public CreateEventViewController() throws IOException {
        editEventModel = new EditEventModel();
    }

    public void setCoordinatorController(CoordinatorController coordinatorController) {
        this.coordinatorController = coordinatorController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

//    public void handleOkButton(ActionEvent event) throws SQLException {
//        List<TicketType> ticketList = new ArrayList<>();
//
//        editEventModel.createEvent(eventInput.getText(), new Date(), locationInput.getText(), descriptionInput.getText(), Integer.parseInt(seatsInput.getText()), ticketList);
//        coordinatorController.refreshTable();
//        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
//        stage.close();
//    }


    public void backView(ActionEvent event) {
    }

    //
    public void handleSaveEvent(ActionEvent event) throws SQLException {
        List<TicketType> ticketTypes = new ArrayList<>();

        editEventModel.createEvent(eventTitleTxt.getText(),DateTimeConverter.convertToDate(eventPicker.getValue()), eventLocationTxt.getText(), eventNotesTxt.getText(), Integer.parseInt(eventCapacityTxt.getText()), ticketTypes);
        coordinatorController.refreshTable();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
