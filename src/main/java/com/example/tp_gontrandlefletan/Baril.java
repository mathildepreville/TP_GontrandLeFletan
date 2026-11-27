package com.example.tp_gontrandlefletan;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javafx.geometry.Point2D;

public class Baril extends ObjetDuJeu{

    private static final double PERIODE = 3.0;//nb de seconde pour une oscillation complete
    private double tempsEcoule = 0;
    private boolean utilise = false;
    private Image imageOuvert = new Image("baril-ouvert.png");

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

        if (utilise) {
            image = imageOuvert;
        }//change limage lorsque le baril a ete utilise

        super.draw(context, camera);
    }

    @Override
    public void update(double deltaTemps) {

        tempsEcoule += deltaTemps;//additiopnne le temps ecoule
        super.update(deltaTemps);

        double y = (MainJavaFX.HEIGHT - taille.getY()) / 2 * Math.sin(2 * Math.PI * tempsEcoule / PERIODE)
                + (MainJavaFX.HEIGHT - taille.getY()) / 2;

        position = new Point2D(position.getX(), y);
        // Calcule la position verticale avec le sinus
    }

    public void utiliser() {
        utilise = true;
    }//change letat du baril

    public boolean estUtilise() {
        return utilise;
    }//permet de verifier si le baril a ete utiliser
}



