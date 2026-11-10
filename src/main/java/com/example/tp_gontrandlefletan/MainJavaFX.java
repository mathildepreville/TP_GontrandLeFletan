package com.example.tp_gontrandlefletan;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJavaFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainJavaFX.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        Partie partie = new Partie();

        var timer = new AnimationTimer(){
            long dernierTemps = System.nanoTime();
            @Override
            public void handle(long temps) {
                double deltaTemps = (temps-dernierTemps)*1e-9;
                partie.update(deltaTemps);
                partie.draw(context);
            }
        };
        timer.start();


        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
