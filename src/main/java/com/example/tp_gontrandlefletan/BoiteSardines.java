package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class BoiteSardines extends Projectile{

    public BoiteSardines(Point2D position) {
        super(
                Point2D.ZERO,
                position,
                Point2D.ZERO,
                new Point2D(35, 29),
                new Image("sardines.png")
        );
    }


}
