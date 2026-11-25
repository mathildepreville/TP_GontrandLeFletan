package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Hippocampe extends Projectile{

    private double amplitude;
    private double periode;
    private double tempsEcoule;
    private double yInitial;

    public Hippocampe(Point2D position) {
        super(
                new Point2D(500, 0),
                position,
                Point2D.ZERO,
                new Point2D(20, 36),
                new Image("hippocampe.png")
        );
        Random random = new Random();

        amplitude = 30 + random.nextDouble() * 30;

        if (random.nextBoolean()) {
            amplitude *= -1;
        }

        periode = 1 + random.nextDouble() * 2;

        tempsEcoule = 0;

        yInitial = position.getY() + taille.getY() / 2.0;
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
