package com.example.tp_gontrandlefletan;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJavaFX extends Application {

    public static final int WIDTH = 900, HEIGHT = 520;
    @Override
    public void start(Stage stage) throws IOException {

        var paneMenu = new Pane();
        var sceneMenu = new Scene(paneMenu, WIDTH, HEIGHT);
        var paneJeu = new Pane();
        var sceneJeu = new Scene(paneJeu,WIDTH,HEIGHT);

        var canvas = new Canvas(WIDTH, HEIGHT);

        GraphicsContext context = canvas.getGraphicsContext2D();
        Partie partie = new Partie();
        sceneJeu.setOnKeyPressed(e->{
            switch (e.getCode()){
                case W -> partie.getGontrand().allerHaut();
                case S -> partie.getGontrand().allerBas();
                case A -> partie.getGontrand().allerGauche();
                case D -> partie.getGontrand().allerDroite();
            }
        });

        sceneJeu.setOnKeyReleased(e->{
            partie.getGontrand().arreter();
        });

        AnimationTimer timer = new AnimationTimer() {
            private long dernierTemps = System.nanoTime();
            @Override
            public void handle(long temps) {
                double deltaTemps = (temps - dernierTemps) * 1e-9;
                partie.update(deltaTemps);
                context.setFill(Color.web("#2A7FFF"));
                context.fillRect(0, 0, WIDTH, HEIGHT);
                partie.draw(context);
                dernierTemps = temps;
            }
        };
        timer.start();



        //****** PREMIERE SECTION *******//

        var logo = new ImageView (new Image("logo.png"));
        logo.setFitWidth(380);
        logo.setPreserveRatio(true);
//
//        var algues1 = new ImageView(new Image("decor3.png"));
//        var algues2 = new ImageView(new Image("decor5.png"));
//        var algues3 = new ImageView(new Image("decor1.png"));
//        var algues4 = new ImageView(new Image("decor6.png"));
//        var algues5 = new ImageView(new Image("decor2.png"));
//        var algues6 = new ImageView(new Image("decor3.png"));
//        var algues7 = new ImageView(new Image("decor6.png"));
//
//        var decorAlguesOg = new HBox(30,
//
//                algues1,
//                algues2,
//                algues3,
//                algues4,
//                algues5,
//                algues6,
//                algues7
//        );
//        decorAlguesOg.setAlignment(Pos.CENTER);

        var btnJouer = new Button("Jouer!");
        btnJouer.setOnAction(e -> {
            stage.setScene(sceneJeu);
        });
        var btnInfo = new Button("Infos");

        var lesBouttonsOg = new HBox(30,

                btnJouer,
                btnInfo
        );//placez les boutons horizontalement
        lesBouttonsOg.setAlignment(Pos.CENTER);

        var fenetreOG = new VBox(

                logo,
//                decorAlguesOg,
                lesBouttonsOg

        );//VBox principal de la fenetre douverture

        fenetreOG.setPrefSize(WIDTH, HEIGHT);
        fenetreOG.setFillWidth(true);
        fenetreOG.setAlignment(Pos.CENTER);
        fenetreOG.setSpacing(25);
        fenetreOG.setStyle("-fx-background-color:#2A7FFF");
        
        paneMenu.getChildren().add(fenetreOG);

        paneJeu.getChildren().add(canvas);

        stage.setTitle("Gontrand le flétan");

        stage.setScene(sceneMenu);
        stage.show();
    }
}
