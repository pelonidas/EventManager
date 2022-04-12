package com.project.gui.controller;

import com.project.be.Admin;
import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.be.Event;
import com.project.bll.exceptions.UserException;
import com.project.gui.model.AdminModel;
import com.project.gui.model.CoordinatorModel;
import com.project.gui.model.CustomerModel;
import com.project.gui.model.ManageEventsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    private CustomerModel customerModel;
    private CoordinatorModel coordinatorModel;
    private AdminModel adminModel;
    private ManageEventsModel manageEventsModel;

    private ObservableList<Customer> allCustomers;
    private ObservableList<Coordinator> allCoordinators;
    private ObservableList<Admin> allAdmins;
    private ObservableList<Event> allEvents;
    public MainController() throws SQLException, IOException {
        customerModel = new CustomerModel();
        coordinatorModel = new CoordinatorModel();
        adminModel = new AdminModel();
        manageEventsModel = new ManageEventsModel();
    }

    public void loadData(){
        allCustomers= FXCollections.observableArrayList();
        allCoordinators= FXCollections.observableArrayList();
        allAdmins= FXCollections.observableArrayList();
        allEvents=FXCollections.observableArrayList();

        Thread loadCustomerData= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    allCustomers.setAll(customerModel.getAllCustomers());
                } catch (SQLException  e) {
                    e.printStackTrace();
                }
            }
        });

        Thread loadCoordinatorData= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    allCoordinators.setAll(coordinatorModel.getAllCoordinators());
                } catch (SQLException  e) {
                    e.printStackTrace();
                }
            }
        });

        Thread loadAdminData= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    allAdmins.setAll(adminModel.getAllAdmins());
                } catch (SQLException | UserException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread loadEventsData= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    allEvents.setAll(manageEventsModel.getAllEvents());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        loadCustomerData.start();
        loadCoordinatorData.start();
        loadAdminData.start();
        loadEventsData.start();


        try {
            loadCustomerData.join();
            loadCoordinatorData.join();
            loadEventsData.join();
            loadAdminData.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public CustomerModel getCustomerModel() {
        return customerModel;
    }

    public void setCustomerModel(CustomerModel customerModel) {
        this.customerModel = customerModel;
    }

    public CoordinatorModel getCoordinatorModel() {
        return coordinatorModel;
    }

    public void setCoordinatorModel(CoordinatorModel coordinatorModel) {
        this.coordinatorModel = coordinatorModel;
    }

    public AdminModel getAdminModel() {
        return adminModel;
    }

    public void setAdminModel(AdminModel adminModel) {
        this.adminModel = adminModel;
    }

    public ManageEventsModel getManageEventsModel() {
        return manageEventsModel;
    }

    public void setManageEventsModel(ManageEventsModel manageEventsModel) {
        this.manageEventsModel = manageEventsModel;
    }

    public ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public void setAllCustomers(ObservableList<Customer> allCustomers) {
        this.allCustomers = allCustomers;
    }

    public ObservableList<Coordinator> getAllCoordinators() {
        return allCoordinators;
    }

    public void setAllCoordinators(ObservableList<Coordinator> allCoordinators) {
        this.allCoordinators = allCoordinators;
    }

    public ObservableList<Admin> getAllAdmins() {
        return allAdmins;
    }

    public void setAllAdmins(ObservableList<Admin> allAdmins) {
        this.allAdmins = allAdmins;
    }

    public ObservableList<Event> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(ObservableList<Event> allEvents) {
        this.allEvents = allEvents;
    }
}

