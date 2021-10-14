package com.example.gamefx;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // which scene we want to be initially displayed.. load that .fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CARDS_FOR_HUMANITY.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Card Game"); //the title of the window
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}


