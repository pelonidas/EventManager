package com.project.gui.controller;

import com.project.be.Customer;
import com.project.be.User;
import com.project.gui.view.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ManageUsersViewController implements Initializable {
    @FXML
    private TableColumn<Customer,String> FNameColumn, LNameColumn, UNameColumn,PassWordColumn,AddressColumn,EmailColumn;
    @FXML
    private TableColumn<Customer,Integer> PNumberColumn;
    @FXML
    private TableColumn<Customer, LocalDate> BDateColumn;


    @FXML
    private TextField customersSearchFilter;
    @FXML
    private TextField eventCoordinatorSearchFilter;

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

    public void newCustomer(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/project/gui/view/NewUserView.fxml"));
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("New customer");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void newUser(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/project/gui/view/NewUserView.fxml"));
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("New user");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
