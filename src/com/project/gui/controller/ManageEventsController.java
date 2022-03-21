package com.project.gui.controller;

import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.gui.view.Main;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ManageEventsController implements Initializable {
    @FXML
    private TableColumn<Event,String> titleColumn,locationColumn,descriptionColumn;
    @FXML
    private TableColumn<Event, LocalDate> dateColumn;
    @FXML
    private TableColumn<Event,Integer> ticketsAvailableColumn;
    @FXML
    private TableColumn<Event, List<Coordinator>> coordinatorColumn;
    @FXML
    private TableView<Event> eventsTable;
    Main main;

    @FXML
    private void backView(ActionEvent actionEvent) {
        main.setLayoutChosen("admin");
        try {
            main.initRootLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void newEvent(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/project/gui/view/NewEditEvent.fxml"));
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("New Event");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void logOut(ActionEvent actionEvent) throws Exception {
        main.initLogin();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
