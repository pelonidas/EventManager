package com.project.gui.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private GridPane gridPane;
    @FXML
    private Label siLabel;
    @FXML
    private HBox hbLabel;
    @FXML
    private HBox userNameHBox;
    @FXML
    private HBox passwordHBox;
    @FXML
    private HBox emailHBox;
    @FXML
    private HBox addressHBox;
    @FXML
    private HBox pNHBox;
    @FXML
    private HBox genderHBox;

    private boolean customer = true;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userNameHBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcons.USER));
        passwordHBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcons.LOCK));
        emailHBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcons.ENVELOPE));
        addressHBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcons.HOME));
        pNHBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcons.PHONE_SQUARE));
        genderHBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcons.TRANSGENDER));
    }

    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

    public void setHbLabel (){
        siLabel.setFont(new javafx.scene.text.Font(12));
        if (customer)
            siLabel.setText("New customer");
        else{
            siLabel.setFont(new javafx.scene.text.Font(10));
            siLabel.setText("New coordinator");}

    }
}
