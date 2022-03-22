package com.project.gui.controller;

import com.project.be.Event;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class CoordinatorController implements Initializable {
    @FXML
    private TableView<Event> coordinatorTableView;
    @FXML
    private TableColumn<Event, String> name, attendance, location, date;
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

        name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTitle()));
        date.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDateAndTime().toString()));
        this.location.setCellValueFactory(new PropertyValueFactory<>("location"));
        attendance.setCellValueFactory(new PropertyValueFactory<>("description"));

        coordinatorTableView.setItems(allEvents);
    }
}
