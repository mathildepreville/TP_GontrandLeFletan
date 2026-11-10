package com.example.tp_gontrandlefletan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJavaFX extends Application {

    public static final int WIDTH = 900, HEIGHT = 520;
    @Override
    public void start(Stage stage) throws IOException {


        var pane = new Pane();
        var scene = new Scene(pane, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);

        pane.getChildren().add(canvas);

        stage.setTitle("Gontrand le flétan");
        stage.setScene(scene);
        stage.show();
    }
}
