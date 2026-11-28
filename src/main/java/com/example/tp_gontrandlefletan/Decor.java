package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Decor extends ObjetDuJeu{
    public Decor(Point2D position, Image image) {
        super(new Point2D(0,0), position, new Point2D(0,0), new Point2D(80,119), image);
    }
}
