package com.example.tp_gontrandlefletan;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class ObjetDuJeu {
    protected Point2D position;
    protected Point2D velocite;
    protected Point2D acceleration;
    protected Image image;

    public ObjetDuJeu(Point2D velocite, Point2D position, Point2D acceleration, Image image) {
        this.velocite = velocite;
        this.position = position;
        this.acceleration = acceleration;
        this.image = image;
    }

    public void draw(GraphicsContext context){

    }

    public void update(double deltaTemps){

    }
}
