package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Random;

public class Partie {
    private Gontrand gontrand;
    private PoissonEnnemi[] poissonEnnemis;

    private Point2D position;
    private Point2D velocite;
    private Point2D acceleration;
    private Point2D taille;
    private Image image;

    Random rnd = new Random();

    Partie(){
        gontrand = new Gontrand(velocite,position,acceleration,taille,image);
        poissonEnnemis = new PoissonEnnemi[rnd.nextInt(0,6)];
    }

//    public void update(double deltaTemps){
//
//        boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
//        boolean droite = Input.isKeyPressed(KeyCode.RIGHT);
//
//        // AXE X
//
//        if (gauche) {
//            gontrand.acceleration =
//                    new Point2D(-1000,
//                            gontrand.acceleration.getY());
//
//        } else if (droite) {
//            gontrand.acceleration =
//                    new Point2D(1000,
//                            gontrand.acceleration.getY());
//
//        } else if (Math.abs(gontrand.velocite.getX()) > 0.5) {
//
//            int signe =
//                    gontrand.velocite.getX() > 0 ? -1 : 1;
//
//            gontrand.acceleration =
//                    new Point2D(signe * 500,
//                            gontrand.acceleration.getY());
//
//        } else {
//
//            gontrand.velocite =
//                    new Point2D(0,
//                            gontrand.velocite.getY());
//
//            gontrand.acceleration =
//                    new Point2D(0,
//                            gontrand.acceleration.getY());
//        }
//
//        gontrand.update(deltaTemps);
//    }



    public void draw(GraphicsContext context){
        gontrand.draw(context);
    }

}


