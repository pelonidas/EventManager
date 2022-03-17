package com.project.gui.controller;

import com.project.gui.view.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController {
    private Main main;


    public void setMainApp(Main main) {
        this.main=main;
    }

    public void manageEvents(ActionEvent actionEvent) {
        main.setLayoutChosen("events");
        try {
            main.initRootLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void manageUsers(ActionEvent actionEvent) {
        main.setLayoutChosen("users");
        try {
            main.initRootLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
