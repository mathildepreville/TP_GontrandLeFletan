package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;

public class Camera {
    private static final double LARGEUR_NIVEAU = MainJavaFX.WIDTH*8;
    private Point2D positionCamera;

    public Camera() {
        this.positionCamera = new Point2D(0,0);
    }

    public Point2D coordoEcran(Point2D positionMonde){
        return positionMonde.subtract(positionCamera);
    }

    public Point2D getPositionCamera() {
        return positionCamera;
    }

    public void setPositionCamera(Point2D positionCamera) {
        this.positionCamera = positionCamera;
    }

    public void suivre(Gontrand gontrand ){
        double nouvellePosition = gontrand.getPosition().getX()-MainJavaFX.WIDTH*0.2;
        nouvellePosition = Math.min(nouvellePosition,LARGEUR_NIVEAU -MainJavaFX.WIDTH);
        nouvellePosition = Math.max(0, nouvellePosition);
        if(nouvellePosition > positionCamera.getX()){
            positionCamera = new Point2D(nouvellePosition,0);
        }
    }
}
