package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Gontrand extends ObjetDuJeu{

    private static final double VITESSE_MAX = 300;

    public Gontrand(Point2D velocite, Point2D position, Point2D acceleration, Point2D taille, Image image) {
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

    public void allerGauche(){
        velocite = new Point2D(-VITESSE_MAX,0);
    }

    public void allerDroite(){
        velocite = new Point2D(VITESSE_MAX, 0);
    }

    public void allerHaut(){
        velocite = new Point2D(0,-VITESSE_MAX);
    }

    public void allerBas(){
        velocite = new Point2D(0,VITESSE_MAX);
    }

    public void arreter(){
        velocite = new Point2D(0,0);
    }
}
