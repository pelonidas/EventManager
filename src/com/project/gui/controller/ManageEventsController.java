package com.project.gui.controller;

import com.project.gui.view.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ManageEventsController {

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
}
