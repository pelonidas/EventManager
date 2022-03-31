package com.project.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.be.User;
import com.project.bll.exceptions.UserException;
import com.project.gui.model.CoordinatorModel;
import com.project.gui.model.CustomerModel;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.mapping.Value;

import java.awt.event.KeyAdapter;
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
    private ManageUsersViewController manageUsersViewController;

    public void setManageUsersViewController(ManageUsersViewController manageUsersViewController) {
        this.manageUsersViewController = manageUsersViewController;
    }

    private boolean coordinator = true;

    private static final String[] items ={"Coordinator", "Customer"};
    private CoordinatorModel coordinatorModel;
    private CustomerModel customerModel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        phoneNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    phoneNumber.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (phoneNumber.getText().length() > 8) {
                    String s = phoneNumber.getText().substring(0, 8);
                    phoneNumber.setText(s);
                }
            }
        });

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

    public void signUp(ActionEvent actionEvent) throws SQLException {
        Coordinator coordinator= null;
        Customer customer= null;
        if (isCoordinator()){
             try {
                 coordinator= coordinatorModel.createCoordinator(firstName.getText(),lastName.getText(),userName.getText(),password.getText(),email.getText(),0);
             }catch (UserException ue){
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Alert");
                 alert.setHeaderText(ue.getExceptionMessage());
                 alert.setContentText(ue.getInstructions());
                 alert.showAndWait();
             }
            if (coordinator!=null){
                try {
                    coordinator.setPhoneNumber(Integer.parseInt(phoneNumber.getText()));
                }catch (NumberFormatException nfe){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Please find a number for coordinator");
                    alert.showAndWait();
                }
                coordinatorModel.editCoordinator(coordinator);
                manageUsersViewController.getAllCoordinators().add(coordinator);
                manageUsersViewController.setUpCoordinatorsTable();
             }
        }
        else {
            try {
                customer = customerModel.createCustomer(firstName.getText(),lastName.getText(),email.getText(), 0);
            }catch (UserException ue){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText(ue.getExceptionMessage());
                alert.setContentText(ue.getInstructions());
                alert.showAndWait();
            }
            if(customer!=null){
                try {
                    customer.setPhoneNumber(Integer.parseInt(phoneNumber.getText()));
                }catch (NumberFormatException nfe){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Please find a number for customer");
                    alert.showAndWait();
                }
                customerModel.editCustomer(customer);
                manageUsersViewController.getAllCustomers().add(customer);
                manageUsersViewController.setUpCustomersTable();
        }
}
        if(coordinator!=null || customer!=null){
            Stage stage = (Stage) closeWindow.getScene().getWindow();
            stage.close();
        }
    }

    public boolean isCoordinator() {
        return coordinator;
    }

    public void setCoordinator(boolean coordinator) {
        this.coordinator = coordinator;
    }

    public void disableUsernamePassword(){
        userName.setDisable(true);
        password.setDisable(true);
        siLabel.setText("New customer");
    }




}
