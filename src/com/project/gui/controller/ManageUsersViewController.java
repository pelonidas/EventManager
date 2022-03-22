package com.project.gui.controller;

import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.gui.model.CoordinatorModel;
import com.project.gui.model.CustomerModel;
import com.project.gui.view.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    @FXML
    private TextField filterSearchCoordinator;
    @FXML
    private TextField searchFilterCustomer;
    @FXML
    private TableView<Coordinator> coordinatorsTable;
    @FXML
    private TableColumn<Coordinator,String> FNameCoordinatorColumn,LNameCoordinatorColumn,UNameCoordinatorColumn,PassWordCoordinatorColumn,AddressCoordinatorColumn,EmailCoordinatorColumn;
    @FXML
    private TableColumn<Coordinator,Integer> PNumberCoordinatorColumn;
    @FXML
    private TableColumn<Customer,LocalDate> BDateCoordinatorColumn;
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
    private CoordinatorModel coordinatorModel;
    private CustomerModel customerModel;
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


        customersTable.setItems(customerModel.getAllCustomers());

    }

    private void populateCoordinatorsTableView() throws SQLException {
        FNameCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LNameCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        UNameCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        PassWordCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("passWord"));
        AddressCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        EmailCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        PNumberCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        BDateCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));


        coordinatorsTable.setItems(coordinatorModel.getAllCoordinators());

    }

    public void setMain(Main main) {
        this.main = main;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            coordinatorModel = new CoordinatorModel();
            customerModel = new CustomerModel();
            populateCustomersTableView();
            populateCoordinatorsTableView();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void newCustomer(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/project/gui/view/SignUp.fxml"));
        root = loader.load();
        SignUpController controller = loader.getController();
        controller.setHbLabel();
        Stage stage = new Stage();
        stage.setTitle("New customer");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteCustomer(ActionEvent actionEvent) {
    }

    public void logOut(ActionEvent actionEvent) {
    }

    public void newCoordinator(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/project/gui/view/SignUp.fxml"));
        root = loader.load();
        SignUpController controller = loader.getController();
        controller.setCustomer(false);
        controller.setHbLabel();
        Stage stage = new Stage();
        stage.setTitle("New Coordinator");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteCoordinator(ActionEvent actionEvent) {
    }
}
