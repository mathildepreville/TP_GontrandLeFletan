package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PoissonEnnemi extends ObjetDuJeu{

    public PoissonEnnemi(Point2D velocite, Point2D position, Point2D acceleration, Point2D taille, Image image) {
        super(velocite, position, acceleration, taille, image);
    }

    @Override
    public void draw(GraphicsContext context) {
        super.draw(context);
    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);
    }
}
