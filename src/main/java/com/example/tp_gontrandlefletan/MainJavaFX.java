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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJavaFX extends Application {

    public static final int WIDTH = 900, HEIGHT = 520;
    private boolean enPause = false;
    @Override
    public void start(Stage stage) throws IOException {

        var paneMenu = new Pane();
        var sceneMenu = new Scene(paneMenu, WIDTH, HEIGHT);//création d'une scene pour le menu principal
        var paneJeu = new Pane();
        var sceneJeu = new Scene(paneJeu,WIDTH,HEIGHT);// création d'une scene pour la fenetre de jeu
        var paneInfo = new Pane();
        var sceneInfo = new Scene(paneInfo, WIDTH, HEIGHT);// création d'une scene pour la fenetre d'informations

        var canvas = new Canvas(WIDTH, HEIGHT);

        GraphicsContext context = canvas.getGraphicsContext2D();
        Partie partie = new Partie();

        sceneJeu.setOnKeyPressed(e-> {
            Input.setKeyPressed(e.getCode(), true);
            if (e.getCode() == KeyCode.ESCAPE) {
                partie.lancerProjectile();//lance un projectile lorsque escape est cliquer
                //avec polymorphisme
            }


        });

        sceneJeu.setOnKeyReleased(e->Input.setKeyPressed(e.getCode(),false));

        AnimationTimer timer = new AnimationTimer() {
            private long dernierTemps = System.nanoTime();
            @Override
            public void handle(long temps) {
                double deltaTemps = (temps - dernierTemps) * 1e-9;
                partie.update(deltaTemps);
                context.setFill(partie.getCouleurFond());
                context.fillRect(0, 0, WIDTH, HEIGHT);
                if(partie.isRetourAccueil()){
                    stage.setScene(sceneMenu);
                    partie.resetFinDePartie();
                }
                partie.draw(context);
                dernierTemps = temps;
            }
        };
        timer.start();

        //****** PREMIERE SECTION (menu principal) *******//

        var logo = new ImageView (new Image("logo.png"));
        logo.setFitWidth(380);//converti taille de l'image
        logo.setPreserveRatio(true);//garde le logo intacte sans déformer


        var btnJouer = new Button("Jouer!");
        btnJouer.setOnAction(e -> {
            stage.setScene(sceneJeu);//ouvre scene jouer
        });

        var btnInfo = new Button("Infos");

        // Images des poissons ennemis dans un tableau pour que la liste puisse etre parcouru aléatoirement
        String[] poissons = {
                "poisson1.png",
                "poisson2.png",
                "poisson3.png",
                "poisson4.png",
                "poisson5.png"
        };

        ImageView poissonInfos = new ImageView();//création d'un image view vide ou une poisson aléatoire ira après

        btnInfo.setOnAction(e -> {
            int hasard = (int)(Math.random() * poissons.length);//poisson au hasard est sélectionné

            poissonInfos.setImage(
                    new Image(getClass().getResourceAsStream(
                            "/" + poissons[hasard]
                    ))//le poisson sélectionné au hasard est inséré dans le ImageView
            );

            stage.setScene(sceneInfo);//ouvre la scene info
        });


        var lesBouttonsOg = new HBox(30,

                btnJouer,
                btnInfo
        );//placez les boutons horizontalement
        lesBouttonsOg.setAlignment(Pos.CENTER);

        var fenetreOG = new VBox(

                logo,
                lesBouttonsOg

        );//VBox principal de la fenetre douverture

        fenetreOG.setPrefSize(WIDTH, HEIGHT);
        fenetreOG.setFillWidth(true);
        fenetreOG.setAlignment(Pos.CENTER);
        fenetreOG.setSpacing(25);
        fenetreOG.setStyle("-fx-background-color:#2A7FFF");

        // Escape ferme le programme lorsqu'on est dans la scene du menu principal
        sceneMenu.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ESCAPE")) {
                stage.close();
            }
        });

        //********** DEUXIEME SECTION (menu informations) *********//


        var titreInfo = new Label("Gontrand le flétan");
        titreInfo.setStyle(
                "-fx-font-size: 42px;" +
                "-fx-font-weight: bold;"
        );


        poissonInfos.setFitWidth(150);
        poissonInfos.setPreserveRatio(true);

        // Noms des membres de l'équipe
        var equipe = new Label(
                "Par   Mathilde Préville\n" +
                "et   Siya Jeslyn"

        );

        equipe.setStyle(
                "-fx-font-size: 20px;" +
                " -fx-text-alignment: center;");

        // Texte explicatif
        var explication = new Label(
                "Travail remis et adapté à Raouf Babari, Graphismes adaptés de https://game-icons.net/ et de\n" +
                        "https://openclipart.org/. Développé dans le cardre du cours 420-SF3-RE. Développement d'applications dans un \n" +
                        "environnement graphique au Collège de Bois-de-Boulogne."
        );

        explication.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-text-alignment: center;"
        );


        // Bouton retour
        var btnRetour = new Button("Retour");

        btnRetour.setOnAction(e -> {
            stage.setScene(sceneMenu);
        });

        //Organisation des élémentas
        var fenetreInfo = new VBox(
                25,
                titreInfo,
                poissonInfos,
                equipe,
                explication,
                btnRetour
        );

        fenetreInfo.setAlignment(Pos.CENTER);
        fenetreInfo.setPrefSize(WIDTH, HEIGHT);
        fenetreInfo.setStyle("-fx-background-color: #2A7FFF;");

        // Escape ouvre le menu principal
        sceneInfo.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ESCAPE")) {
                stage.setScene(sceneMenu);
            }
        });


        paneInfo.getChildren().add(fenetreInfo);
        
        paneMenu.getChildren().add(fenetreOG);

        paneJeu.getChildren().add(canvas);

        stage.setTitle("Gontrand le flétan");

        stage.setScene(sceneMenu);
        stage.show();
    }
}
