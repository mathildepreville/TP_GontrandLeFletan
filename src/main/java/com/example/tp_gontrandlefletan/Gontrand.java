package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Gontrand extends ObjetDuJeu{

    private boolean invincible = false;
    private double tempsInvincible;
    private int vies;
    private static final double VITESSE_MAX = 300;
    private static final double ACCELERATION_MAX = 1000;

    private Image imageOutch;

    private boolean enMouvement = false;
    private Image imageAvant;
    private Image imageNormale;
    public Gontrand(Point2D velocite, Point2D position, Point2D acceleration, Point2D taille, Image image) {
        super(velocite, position, acceleration, taille, image);
        vies = 4;
        imageNormale = new Image("gontrand.png");
        imageAvant = new Image("gontrand-avant.png");
        imageOutch = new Image("gontrand-outch.png");
    }

    public void draw(GraphicsContext context, Camera camera) {
        if(!invincible|| estVisible()) {
            super.draw(context, camera);
        }

        if(invincible){
            image = imageOutch;
        }else if (enMouvement){
            image = imageAvant;
        }else{
            image = imageNormale;
        }
    }

    @Override
    public void update(double deltaTemps) {
        // gérer les touches
        if(Input.isKeyPressed(KeyCode.RIGHT)){
            allerDroite();
        }else if(Input.isKeyPressed(KeyCode.LEFT)){
            allerGauche();
        }else{
            ralentirX();
        }
        if(Input.isKeyPressed(KeyCode.UP)){
            allerHaut();
        }else if(Input.isKeyPressed(KeyCode.DOWN)){
            allerBas();
        }else{
            ralentirY();
        }
        super.update(deltaTemps);
        // vitesse max en X
        if (Math.abs(velocite.getX()) > VITESSE_MAX) {
            velocite = new Point2D(
                    Math.signum(velocite.getX()) * VITESSE_MAX,
                    velocite.getY()
            );
        }
        // vitesse max en Y
        if (Math.abs(velocite.getY()) > VITESSE_MAX) {
            velocite = new Point2D(
                    velocite.getX(),
                    Math.signum(velocite.getY()) * VITESSE_MAX
            );
        }

        if(invincible){
            tempsInvincible -= deltaTemps;
            if(tempsInvincible <= 0){
                image = imageNormale;
                invincible = false;
                tempsInvincible = 0;
            }
        }

        enMouvement = Input.isKeyPressed(KeyCode.RIGHT)
                || Input.isKeyPressed(KeyCode.LEFT)
                || Input.isKeyPressed(KeyCode.UP)
                || Input.isKeyPressed(KeyCode.DOWN);
    }

    public boolean estVisible(){
        if(!invincible){
            return true;
        }
         return ((int)(tempsInvincible*4))%2 == 0;
    }

    public void allerGauche(){
        acceleration = new Point2D(-ACCELERATION_MAX,acceleration.getY());
    }

    public void allerDroite(){
       acceleration = new Point2D(ACCELERATION_MAX, acceleration.getY());
    }

    public void allerHaut(){
        acceleration = new Point2D(acceleration.getX(),-ACCELERATION_MAX);
    }

    public void allerBas(){
        acceleration = new Point2D(acceleration.getX(),ACCELERATION_MAX);
    }

    public void ralentirX(){

        if(velocite.getX() > 0 && velocite.getX() < 10){
            velocite = new Point2D(0, velocite.getY());
            acceleration = new Point2D(0,acceleration.getY());
            return;
        }
        acceleration = new Point2D(-velocite.getX()*5,acceleration.getY() );
    }
    public void ralentirY(){

        if(velocite.getY() > 0 && velocite.getY() < 10){
            velocite = new Point2D(velocite.getX(),0);
            acceleration = new Point2D(acceleration.getX(),0);
            return;
        }
        acceleration = new Point2D(acceleration.getX(), -velocite.getY()*5);
    }

    public void perdreVie(){
        if(invincible){
            return;
        }
        if(vies>0){
            vies--;
        }
        invincible = true;
        tempsInvincible = 2;

        image = imageOutch;
    }

    public int getVies() {
        return vies;
    }


}
