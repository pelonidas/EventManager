package com.project.gui.controller;


import com.project.be.Event;
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
    private final CoordinatorController coordinatorController;

    public CreateEventViewController() {
        coordinatorController = new CoordinatorController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        okButton.setOnAction(event -> {
            Event e = new Event(eventInput.getText(), new Date(), locationInput.getText(), descriptionInput.getText(), Integer.parseInt(seatsInput.getText()));
            coordinatorController.getAllEvents().add(e);
            System.out.println(coordinatorController.getAllEvents());
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.close();
        });

    }
}
