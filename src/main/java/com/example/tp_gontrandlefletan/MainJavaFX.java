package com.example.tp_gontrandlefletan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJavaFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< HEAD
        FXMLLoader fxmlLoader = new FXMLLoader(MainJavaFX.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
=======


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
        fenetreOG.setStyle("-fx-background-color: #4564c1");
        
        pane.getChildren().add(fenetreOG);




        pane.getChildren().add(canvas);

        stage.setTitle("Gontrand le flétan");
>>>>>>> parent of 14a5ad4 (imports)
        stage.setScene(scene);
        stage.show();
    }
}
