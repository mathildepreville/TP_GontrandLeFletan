package com.example.tp_gontrandlefletan;

import javafx.scene.canvas.GraphicsContext;

public class Partie {
    private Gontrand gontrand;

    Partie(){
        gontrand = new Gontrand();
    }

    public void update(double deltaTemps){
        gontrand.update(deltaTemps);
    }
    public void draw(GraphicsContext context){
        gontrand.draw();
    }

}
