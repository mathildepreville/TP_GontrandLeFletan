package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;

public abstract class Projectile extends ObjetDuJeu{

    public Projectile(Point2D velocite, Point2D position, Point2D acceleration) {
        super(velocite, position, acceleration);
    }
}
