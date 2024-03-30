package com.mapoverlay;

import com.mapoverlay.model.MapOverlay;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("MapOverlay project of Odan and Steve");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        MapOverlay Mo = new MapOverlay();
        launch();
    }
}