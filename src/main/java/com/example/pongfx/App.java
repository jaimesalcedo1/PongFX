package com.example.pongfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new Pong(), 1000, 600);
        stage.setTitle("PongFX");
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image("logo.png"));
    }

    public static void main(String[] args) {
        launch();
    }
}