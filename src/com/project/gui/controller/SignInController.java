package com.project.gui.controller;

import com.project.gui.view.Main;
import javafx.event.ActionEvent;


public class SignInController {
    private Main main;
    public void setMainApp(Main main) {
        this.main=main;
    }

    public void openAdminWindow(ActionEvent actionEvent) throws Exception {
        main.setLayoutChosen("admin");
        main.initRootLayout();
    }

    public void openCoordinatorWindow(ActionEvent actionEvent) throws Exception {
        main.setLayoutChosen("coordinator");
        main.initRootLayout();
    }

    public void openCustomerWindow(ActionEvent actionEvent) throws Exception {
        main.setLayoutChosen("customer");
        main.initRootLayout();
    }
}
