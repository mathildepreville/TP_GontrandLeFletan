package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;


public abstract class Projectile extends ObjetDuJeu{

    public Projectile(Point2D velocite, Point2D position, Point2D acceleration, Image image) {
        super(velocite, position, acceleration, image);
    }
}
