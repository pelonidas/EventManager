package com.project.gui.controller;


import com.project.be.Event;
import com.project.gui.model.CoordinatorModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CreateEventViewController implements Initializable {
    @FXML
    private TextField eventInput, dateInput, descriptionInput, locationInput, seatsInput;
    @FXML
    private Button okButton, cancelButton;
    private final CoordinatorModel coordinatorModel;
    private CoordinatorController coordinatorController;
    public CreateEventViewController() {
        coordinatorModel = new CoordinatorModel();
    }
    public void setCoordinatorController(CoordinatorController coordinatorController){
        this.coordinatorController = coordinatorController;
        System.out.println("heh");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleOkButton(ActionEvent event) {
        Event e = new Event(eventInput.getText(), new Date(), locationInput.getText(), descriptionInput.getText(), Integer.parseInt(seatsInput.getText()));
        coordinatorModel.addEvent(e);
        System.out.println(coordinatorModel.getAllEvents());
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        coordinatorController.refreshTable();
        stage.close();
    }
}
