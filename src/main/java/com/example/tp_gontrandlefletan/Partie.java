package com.example.tp_gontrandlefletan;

import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

public class Partie {
    private Gontrand gontrand;
    private PoissonEnnemi[] poissonEnnemis;
    Random rnd = new Random();

    Partie(){
        gontrand = new Gontrand();
        poissonEnnemis = new PoissonEnnemi[rnd.nextInt(0,6)];
    }

    public void update(double deltaTemps){
        gontrand.update(deltaTemps);
    }
    public void draw(GraphicsContext context){
        gontrand.draw(context);
    }

}
