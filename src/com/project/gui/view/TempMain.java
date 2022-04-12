package com.project.gui.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        Parent root = FXMLLoader.load(getClass().getResource("CoordinatorView.fxml"));
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("../resources/fonts/Comfortaa-Regular.ttf")));
        } catch (IOException | FontFormatException e) {
            //Handle exception
            System.out.println("Font couldn't be loaded");
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        //setUserAgentStylesheet("com/project/MainCSS.css");
    }
}
