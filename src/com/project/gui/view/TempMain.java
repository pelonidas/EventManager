package com.project.gui.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TempMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(
                TempMain.class.getResource("fonts/Comfortaa-Regular.ttf").toExternalForm(), 10
        );
        Parent root = FXMLLoader.load(getClass().getResource("CoordinatorView.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        //setUserAgentStylesheet("com/project/MainCSS.css");
    }
}
