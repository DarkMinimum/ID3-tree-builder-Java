package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class HelloApplication extends Application {

    public static ResourceBundle bundle;
    public static String lang = "ru_RU";


    @Override
    public void start(Stage stage) throws IOException {


        bundle = ResourceBundle.getBundle("lang", new Locale(lang));

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"), bundle);

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        scene.setOnDragOver(event -> {
                    event.acceptTransferModes(TransferMode.MOVE);
                });

        stage.setTitle(bundle.getString("window.main"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}