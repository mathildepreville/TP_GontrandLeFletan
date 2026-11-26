package com.example.tp_gontrandlefletan;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class ObjetDuJeu {
    protected Point2D position;
    protected Point2D velocite;
    protected Point2D acceleration;
    protected Point2D taille;
    protected Image image;

    public ObjetDuJeu(Point2D velocite, Point2D position, Point2D acceleration, Point2D taille, Image image) {
        this.velocite = velocite;
        this.position = position;
        this.acceleration = acceleration;
        this.taille = taille;
        this.image = image;
    }

    public void draw(GraphicsContext context){
        context.drawImage(image,position.getX(),position.getY());

    }

    public void update(double deltaTemps){
        velocite = velocite.add(acceleration.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));

    }

    public double getHaut() {
        return position.getY();
    }
    public double getBas() {
        return position.getY() + taille.getY();
    }
    public double getGauche() {
        return position.getX();
    }
    public double getDroite() {
        return position.getX() + taille.getX();
    }
    public Point2D getCentre() {
        return position.add(taille.multiply(1/2.0));
    }

    public Point2D getPosition() {
        return position;
    }

    public Point2D getTaille() {
        return taille;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Point2D getVelocite() {
        return velocite;
    }

    public void setVelocite(Point2D velocite) {
        this.velocite = velocite;
    }

    public Point2D getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Point2D acceleration) {
        this.acceleration = acceleration;
    }
}
