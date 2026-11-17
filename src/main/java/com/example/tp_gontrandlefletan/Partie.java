package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Partie {
    private Gontrand gontrand;
    private PoissonEnnemi[] poissonEnnemis;

    private Point2D position;
    private Point2D velocite;
    private Point2D acceleration;
    private Image image;

    Random rnd = new Random();

    Partie(){
        gontrand = new Gontrand(velocite,position,acceleration,image);
        poissonEnnemis = new PoissonEnnemi[rnd.nextInt(0,6)];
    }

    public void update(double deltaTemps){
        gontrand.update(deltaTemps);
    }
    public void draw(GraphicsContext context){
        gontrand.draw(context);
    }

}
