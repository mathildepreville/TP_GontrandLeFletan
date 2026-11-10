package com.example.tp_gontrandlefletan;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public abstract class ObjetDuJeu {
    private Point2D position;
    private Point2D velocite;
    private Point2D acceleration;

    public ObjetDuJeu(Point2D velocite, Point2D position, Point2D acceleration) {
        this.velocite = velocite;
        this.position = position;
        this.acceleration = acceleration;
    }

    public void draw(GraphicsContext context){

    }

    public void update(double deltaTemps){

    }
}
