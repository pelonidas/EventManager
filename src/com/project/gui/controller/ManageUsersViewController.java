package com.project.gui.controller;

import com.project.gui.view.Main;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageUsersViewController implements Initializable {
    Main main;

    public void backView(ActionEvent actionEvent) {
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
