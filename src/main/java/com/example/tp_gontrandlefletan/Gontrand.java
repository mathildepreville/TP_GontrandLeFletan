package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Gontrand extends ObjetDuJeu{
    public Gontrand(Point2D velocite, Point2D position, Point2D acceleration, Image image) {
        super(velocite, position, acceleration,image);
    }

    @Override
    public void draw(GraphicsContext context){
        context.drawImage(image, position.getX(), position.getY());

    }

    @Override
    public void update(double deltaTemps) {
        position = position.add(velocite.multiply(deltaTemps));

    }

}
