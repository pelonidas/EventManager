package com.project.gui.view;

import com.project.be.Admin;
import com.project.gui.controller.AdminController;
import com.project.gui.controller.RootLayoutController;
import com.project.gui.controller.SignInController;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main extends Application {
    private Stage primaryStage;
    private StringProperty layoutChosen;

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*EntityManagerFactory emf = Persistence.createEntityManagerFactory("EventManager");
        EntityManager em = emf.createEntityManager();

        Admin a = em.find(Admin.class,4);*/

        this.primaryStage = primaryStage;
        layoutChosen = new SimpleStringProperty("");
        initLogin();
    }
    public void initLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SingInView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        SignInController controller = loader.getController();
        controller.setMainApp(this);

        primaryStage.setTitle("Main window");
        primaryStage.show();
    }
    public void initRootLayout() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class
                .getResource("RootLayout.fxml"));
        BorderPane rootLayout = loader.load();

        if(layoutChosen.get().equals("admin")) {
            FXMLLoader loaderAdmin = new FXMLLoader();
            loaderAdmin.setLocation(getClass().getResource("AdminView.fxml"));
            AnchorPane adminDisplay = loaderAdmin.load();
            AdminController controller = loaderAdmin.getController();
            controller.setMainApp(this);
            rootLayout.getChildren().add(adminDisplay);
            primaryStage.setTitle("Admin window");

        }
        if(layoutChosen.get().equals("coordinator")) {
            FXMLLoader loaderCoordinator = new FXMLLoader();
            loaderCoordinator.setLocation(getClass().getResource("CoordinatorView.fxml"));
            VBox coordinatorDisplay = loaderCoordinator.load();
            rootLayout.getChildren().add(coordinatorDisplay);
            primaryStage.setTitle("coordinator window");
        }

        if(layoutChosen.get().equals("customer")) {
            FXMLLoader loaderCustomer = new FXMLLoader();
            loaderCustomer.setLocation(getClass().getResource("CustomerView.fxml"));
            AnchorPane customerDisplay = loaderCustomer.load();
            rootLayout.getChildren().add(customerDisplay);
            primaryStage.setTitle("Customer window");
        }

        if(layoutChosen.get().equals("events")) {
            FXMLLoader loaderEvent = new FXMLLoader();
            loaderEvent.setLocation(getClass().getResource("ManageEvents.fxml"));
            AnchorPane eventsDisplay = loaderEvent.load();
            rootLayout.getChildren().add(eventsDisplay);
            primaryStage.setTitle("Manage events window");
        }

        if(layoutChosen.get().equals("users")) {
            FXMLLoader loaderUser = new FXMLLoader();
            loaderUser.setLocation(getClass().getResource("ManageUsers.fxml"));
            AnchorPane usersDisplay = loaderUser.load();
            rootLayout.getChildren().add(usersDisplay);
            primaryStage.setTitle("Manage users window");
        }

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);

        // Give the controller access to the main app.
        RootLayoutController controller = loader.getController();
        controller.setMainApp(this);
        primaryStage.show();
    }
    public static void main(String[] args) {
        Application.launch();
    }

    public void setLayoutChosen(String layoutChosen) {
        this.layoutChosen.set(layoutChosen);
    }
}
