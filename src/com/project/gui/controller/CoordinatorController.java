package com.project.gui.controller;

import com.project.be.Event;
import com.project.gui.model.CoordinatorModel;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class CoordinatorController implements Initializable {
    @FXML
    private TableView<Event> coordinatorTableView;
    @FXML
    private TableColumn<Event, String> name, attendance, location, date;
    @FXML
    private Button createButton, editButton, deleteButton, manageButton;
    private final CoordinatorModel coordinatorModel;
    private CreateEventViewController createEventViewController;
    public CoordinatorController() {
        coordinatorModel = new CoordinatorModel();
        createEventViewController = new CreateEventViewController();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshTable();

//        handleNewDialog(createButton, , "Create a new Event");
//        handleNewDialog(editButton, "../view/EditEventView.fxml", "Edit the event");

        manageButton.setOnAction(event -> {
            refreshTable();
        });
        deleteButton.setOnAction(event -> {
            coordinatorModel.deleteEvent(coordinatorTableView.getSelectionModel().getSelectedItem());
            refreshTable();
            System.out.println(coordinatorModel.getAllEvents());
        });
    }

    public void refreshTable() {
        coordinatorTableView.getItems().clear();
        coordinatorTableView.refresh();
        name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTitle()));
        date.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDateAndTime().toString()));
        this.location.setCellValueFactory(new PropertyValueFactory<>("location"));
        attendance.setCellValueFactory(new PropertyValueFactory<>("description"));

        coordinatorTableView.getItems().setAll(coordinatorModel.getAllEvents());
    }

    public void handleCreateEvent(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("com/project/gui/view/CreateEventView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println(e);
        }
        CreateEventViewController alertDialogController = loader.getController();
        alertDialogController.setCoordinatorController(this);

        Stage stage = new Stage();
        stage.setTitle("New/Edit Song");
        stage.setScene(new Scene(root));
        stage.show();
    }

}

