package com.project.gui.controller;

import com.project.be.Event;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private ObservableList<Event> allEvents = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Date d = new Date();
        Event e1 = new Event("test1", d, "poprad", "lorem lorem ipsum");
        Event e2 = new Event("test2", d, "Piestany", "lorem lorem ipsum");
        Event e3 = new Event("test3", d, "kosice", "lorem lorem ipsum");
        Event e4 = new Event("test4", d, "presov", "lorem lorem ipsum");
        Event e5 = new Event("test5", d, "Trnava", "lorem lorem ipsum");
        Event e6 = new Event("test6", d, "Trencin", "lorem lorem ipsum");

        allEvents.add(e1);
        allEvents.add(e2);
        allEvents.add(e3);
        allEvents.add(e4);
        allEvents.add(e5);
        allEvents.add(e6);

        refreshTable();

        handleNewDialog(createButton, "../view/CreateEventView.fxml", "Create a new Event");
        handleNewDialog(editButton, "../view/EditEventView.fxml", "Edit the event");

        manageButton.setOnAction(event -> {
            refreshTable();
        });
    }

    public void refreshTable() {
        name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTitle()));
        date.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDateAndTime().toString()));
        this.location.setCellValueFactory(new PropertyValueFactory<>("location"));
        attendance.setCellValueFactory(new PropertyValueFactory<>("description"));

        coordinatorTableView.setItems(allEvents);
    }

    private void handleNewDialog(Button btn, String path, String title) {
        btn.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle(title);
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public ObservableList<Event> getAllEvents() {
        return allEvents;
    }
}

