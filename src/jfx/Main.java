package jfx;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application {

    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        showFirstPage();
    }

    public void showFirstPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FirstPage.fxml"));
        Parent root = loader.load();

        // Loading the controller
        Switch controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Image will be placed before this page");
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    public void showGamePage() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("game.fxml"));
        Parent root = loader.load();

        // Loading the controller
        Switch controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("8 Ball Pool");
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }


    public void showRulesPage() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("rules.fxml"));
        Parent root = loader.load();

        // Loading the controller
        Switch controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("This is the Game Page");
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    public void showTablePage() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("tables.fxml"));
        Parent root = loader.load();

        // Loading the controller
        Switch controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("8 Ball Pool");
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    public void showQuePage() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ques.fxml"));
        Parent root = loader.load();

        // Loading the controller
        Switch controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("8 Ball Pool");
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
