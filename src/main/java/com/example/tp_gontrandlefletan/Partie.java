package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Random;

public class Partie {
    private Gontrand gontrand;
    private PoissonEnnemi[] poissonEnnemis;

    Random rnd = new Random();

    Partie(){
        gontrand = new Gontrand(new Point2D(0,0),new Point2D(100,100),new Point2D(0,0),new Point2D(64,64),new Image("gontrand.png"));
        poissonEnnemis = new PoissonEnnemi[rnd.nextInt(0,6)];
    }

    public void update(double deltaTemps){
        gontrand.update(deltaTemps);
     }

    public void draw(GraphicsContext context){
        gontrand.draw(context);
    }

    public Gontrand getGontrand(){
        return gontrand;
    }

}


