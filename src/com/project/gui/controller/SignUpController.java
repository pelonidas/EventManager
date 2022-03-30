package com.project.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.project.be.User;
import com.project.bll.exceptions.UserException;
import com.project.gui.model.CoordinatorModel;
import com.project.gui.model.CustomerModel;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.mapping.Value;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private JFXButton closeWindow;
    @FXML
    private HBox phoneBox;
    @FXML
    private TextField firstName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField address;
    @FXML
    private TextField userName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private JFXCheckBox termsConditions;
    @FXML
    private JFXComboBox<String> categoryComboBox;
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
    private HBox genderHBox;

    private boolean customer = true;

    private static final String[] items ={"Coordinator", "Customer"};
    private CoordinatorModel coordinatorModel;
    private CustomerModel customerModel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userNameHBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcons.USER));
        passwordHBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcons.LOCK));
        emailHBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcons.ENVELOPE));
        phoneBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcons.PHONE));

        try {
            coordinatorModel = new CoordinatorModel();
            customerModel = new CustomerModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

    public void closeWindow(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Are you sure you want to close this window ?");
        alert.setContentText("All your infos will be lost in this case.");
        alert.showAndWait();
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) closeWindow.getScene().getWindow();
            stage.close();
        }
    }

    public void signUp(ActionEvent actionEvent) throws SQLException, UserException {
        coordinatorModel.createCoordinator(firstName.getText(),lastName.getText(),userName.getText(),password.getText(),email.getText(), Integer.parseInt(phoneNumber.getText()));
        Stage stage = (Stage) closeWindow.getScene().getWindow();
        stage.close();
}
}
