package com.example.tp_gontrandlefletan;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javafx.geometry.Point2D;

public class Baril extends ObjetDuJeu{

    public Baril(Point2D position) {
        super(
                Point2D.ZERO,
                position,
                Point2D.ZERO,
                new Point2D(70, 83),
                new Image("baril.png")
        );

    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {
        super.draw(context, camera);
    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);
    }


}
