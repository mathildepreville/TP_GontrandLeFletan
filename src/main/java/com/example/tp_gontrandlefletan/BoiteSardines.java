package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class BoiteSardines extends Projectile{

    public static final double CHARGE = 900;
    private static final double VITESSE_MAX = 400;

    public BoiteSardines(Point2D position) {
        super(
                Point2D.ZERO,
                position,
                Point2D.ZERO,
                new Point2D(35, 29),
                new Image("sardines.png")
        );
    }

    public void update(double deltaTemps, Point2D champ, double hauteurJeu) {

        // Calcul de l'accélération électrique
        acceleration = champ.multiply(CHARGE);

        // Mise à jour de la vitesse et de la position
        super.update(deltaTemps);

        // Limiter la vitesse à 400 px/s
        if (velocite.magnitude() > VITESSE_MAX) {
            velocite = velocite.normalize().multiply(VITESSE_MAX);
        }

        // Rebond sur le haut de l'écran
        if (position.getY() <= 0 && velocite.getY() < 0) {
            velocite = new Point2D(velocite.getX(), -velocite.getY());
            position = new Point2D(position.getX(), 0);
        }

        // Rebond sur le bas de l'écran
        if (position.getY() + taille.getY() >= hauteurJeu
                && velocite.getY() > 0) {

            velocite = new Point2D(velocite.getX(), -velocite.getY());

            position = new Point2D(
                    position.getX(),
                    hauteurJeu - taille.getY()
            );
        }
    }


}
