package com.project.gui.controller;

import com.project.be.Customer;
import com.project.be.User;
import com.project.gui.model.ManageCustomerModel;
import com.project.gui.model.ManageUsersModel;
import com.project.gui.view.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ManageUsersViewController implements Initializable {
    public TableView coordinatorsTable;
    public TableColumn FNameColumn1;
    public TableColumn LNameColumn1;
    public TableColumn UNameColumn1;
    public TableColumn PassWordColumn1;
    public TableColumn AddressColumn1;
    public TableColumn EmailColumn1;
    public TableColumn PNumberColumn1;
    public TableColumn BDateColumn1;
    @FXML
    private TableView<Customer> customersTable;
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
    ManageUsersModel manageUsersModel;

    public void backView(ActionEvent actionEvent) {
        main.setLayoutChosen("admin");
        try {
            main.initRootLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void populateCustomersTableView() throws SQLException {
        FNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        UNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        PassWordColumn.setCellValueFactory(new PropertyValueFactory<>("passWord"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        PNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        BDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));


        customersTable.setItems(manageUsersModel.getAllCustomers());

    }

    private void populateCoordinatorsTableView() throws SQLException {
        FNameColumn1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LNameColumn1.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        UNameColumn1.setCellValueFactory(new PropertyValueFactory<>("userName"));
        PassWordColumn1.setCellValueFactory(new PropertyValueFactory<>("passWord"));
        AddressColumn1.setCellValueFactory(new PropertyValueFactory<>("email"));
        EmailColumn1.setCellValueFactory(new PropertyValueFactory<>("address"));
        PNumberColumn1.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        BDateColumn1.setCellValueFactory(new PropertyValueFactory<>("birthDate"));


        coordinatorsTable.setItems(manageUsersModel.getAllCoordinators());

    }

    public void setMain(Main main) {
        this.main = main;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            manageUsersModel = new ManageUsersModel();
            populateCustomersTableView();
            populateCoordinatorsTableView();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

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
