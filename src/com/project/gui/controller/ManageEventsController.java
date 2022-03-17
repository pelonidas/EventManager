package com.project.gui.controller;

import com.project.gui.view.Main;
import javafx.event.ActionEvent;

public class ManageEventsController {
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
}
