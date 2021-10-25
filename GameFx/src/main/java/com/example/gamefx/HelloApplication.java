package com.example.gamefx;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        // which scene we want to be initially displayed.. load that .fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CAM_1.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Card Game"); //the title of the window
        stage.setScene(scene);
        stage.show();

        // TextField inputName = new TextField();
        // InputName.setOnAction(e −> text.setText(inputName.getText()));
        // Pressing the Enter key on the text field triggers an action event.

        stage.setResizable(false);

    }

    public static void main(String[] args) {
        launch();
    }

}


