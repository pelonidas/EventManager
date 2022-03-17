package com.project.gui.controller;

import com.project.be.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerPurchasedEventsController implements Initializable {
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> nameColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
