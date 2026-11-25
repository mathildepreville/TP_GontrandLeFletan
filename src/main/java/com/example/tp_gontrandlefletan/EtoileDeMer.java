package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public class EtoileDeMer extends Projectile{


    public EtoileDeMer(Point2D position) {
        super(
                new Point2D(800,0),//velocite
                position,
                Point2D.ZERO,//acceleration
                new Point2D(36, 35),//taille en px
                new Image("etoile.png")
        );

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
