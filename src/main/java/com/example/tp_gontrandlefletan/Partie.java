package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class Partie {
    private Camera camera;
    private Gontrand gontrand;
    private ArrayList<PoissonEnnemi> poissonEnnemis;

    // Temps écoulé depuis l'apparition du dernier groupe d'ennemis.
    private double tempDepuisDernierGroupe = 0;

    Random rnd = new Random();

    Partie(){
        gontrand = new Gontrand(new Point2D(0,0),new Point2D(100,100),new Point2D(100,100),new Point2D(64,64),new Image("gontrand.png"));
        poissonEnnemis = new ArrayList<>();
        camera = new Camera();
        camera.setPositionCamera(
                new Point2D(300, 0)
        );
    }
    public void update(double deltaTemps){
        // Met à jour les déplacements de Gontrand.
        gontrand.update(deltaTemps);
        camera.suivre(gontrand);
        // Fait apparaître un nouveau groupe toutes les 1.75 secondes.
        tempDepuisDernierGroupe += deltaTemps;
        if(tempDepuisDernierGroupe >= 1.75){
            creerGroupEnnemi();
            tempDepuisDernierGroupe = 0;
        }

        for (PoissonEnnemi poisson : poissonEnnemis) {
            poisson.update(deltaTemps);
        }
     }

    public void draw(GraphicsContext context){
        gontrand.draw(context,camera);

        for (PoissonEnnemi poisson : poissonEnnemis) {
            poisson.draw(context);
        }
    }

    public Gontrand getGontrand(){
        return gontrand;
    }

    // Crée un groupe de 1 à 5 poissons ennemis.
    private void creerGroupEnnemi(){
        String [] choixImages = {
                "poisson1.png",
                "poisson2.png",
                "poisson3.png",
                "poisson4.png",
                "poisson5.png"
        };

        String imageChoisi = choixImages[rnd.nextInt(choixImages.length)];//choisi un poisson au hasard

        // Position aléatoire à droite de l'écran.
        double x = rnd.nextDouble(900, 1200);
        // Hauteur aléatoire.
        double y = rnd.nextDouble(50, 450);
        int taille = rnd.nextInt(1,6);
        for(int i = 0; i < taille;i++){
            poissonEnnemis.add(
                    new PoissonEnnemi(
                            new Point2D(-150, 0),
                            new Point2D(x, y),
                            new Point2D(0, 0),
                            new Point2D(64, 64),
                            new Image(imageChoisi)
                    )
            );
        }
    }

}


