package com.example.gamefx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    @FXML
    private Label welcomeText;
    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoader;

    //https://www.youtube.com/watch?v=hcM-R-YOKkQ&ab_channel=BroCode
    // method to switch scenes
    @FXML
    protected void onButtonClick(ActionEvent event) throws IOException {
        welcomeText.setText("Welcome to JavaFX Application!");
        //load the correct .fxml file
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CARDS_FOR_HUMANITY.fxml"));
        //cast the source of the event to Node, cast the entire stage to Stage
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        // set the scene to the stage
        stage.setScene(scene);
        stage.show(); //display it
    }

    @FXML
    protected void switchToScene2(ActionEvent event) throws IOException {
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("second-page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 560, 440);
        stage.setScene(scene);
        stage.show();

    }


}