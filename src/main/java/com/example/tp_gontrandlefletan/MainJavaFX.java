package com.example.tp_gontrandlefletan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJavaFX extends Application {

    public static final int WIDTH = 900, HEIGHT = 520;
    Image imgGontrand;
    @Override
    public void start(Stage stage) throws IOException {


        var pane = new Pane();
        var scene = new Scene(pane, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);


        var titre = new Label("Gontrand le Flétan");//titre en haut

        imgGontrand= new Image("gontrand.png");
        var viewGontrandOg = new ImageView(imgGontrand);//image de gontrand lorsquon ouvre le jeu

        var algues1 = new ImageView(new Image("decor3.png"));
        var algues2 = new ImageView(new Image("decor5.png"));
        var algues3 = new ImageView(new Image("decor1.png"));
        var algues4 = new ImageView(new Image("decor6.png"));
        var algues5 = new ImageView(new Image("decor2.png"));
        var algues6 = new ImageView(new Image("decor3.png"));
        var algues7 = new ImageView(new Image("decor6.png"));

        var decorAlguesOg = new HBox(

                algues1,
                algues2,
                algues3,
                algues4,
                algues5,
                algues6,
                algues7
        );

        var btnJouer = new Button("Jouer!");
        var btnInfo = new Button("Infos");

        var lesBouttonsOg = new HBox(

                btnJouer,
                btnInfo
        );//placez les boutons horizontalement

        var fenetreOG = new VBox(

                titre,
                viewGontrandOg,
                decorAlguesOg,
                lesBouttonsOg

        );//VBox principal de la fenetre douverture

        fenetreOG.setAlignment(Pos.CENTER);//positionner le vbox dans le centre de la fenetre

        pane.getChildren().add(fenetreOG);




        pane.getChildren().add(canvas);

        stage.setTitle("Gontrand le flétan");
        stage.setScene(scene);
        stage.show();
    }
}
