package com.project.gui.controller;

import com.project.gui.view.Main;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable{
    @FXML
    private GridPane gridPane;
    @FXML
    private HBox eventsBox;
    @FXML
    private HBox usersBox;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Text calendarIcon = GlyphsDude.createIcon(FontAwesomeIcons.CALENDAR);
        calendarIcon.setFill(Paint.valueOf("white"));
        eventsBox.getChildren().add(calendarIcon);

        Text usersIcon = GlyphsDude.createIcon(FontAwesomeIcons.USERS);
        usersIcon.setFill(Paint.valueOf("white"));
        usersBox.getChildren().add(usersIcon);

        gridPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.BACK_SPACE)){
                    try {
                        logOut(new ActionEvent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        gridPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ESCAPE)){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Are you sure you want to quit ?");
                    alert.showAndWait();
                    if (alert.showAndWait().get() == ButtonType.OK) {
                        System.exit(0);
                    }
                }
            }
        });
    }



    public void logOut(ActionEvent actionEvent) throws Exception {
        main.initLogin();
    }
}
