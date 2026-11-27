package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.tp_gontrandlefletan.BoiteSardines.CHARGE;

public class Partie {
    private static final double LARGEUR_NIVEAU = MainJavaFX.WIDTH*8;
    private boolean modeDebug = false;
    private boolean dAvant = false;
    private Camera camera;
    private Gontrand gontrand;
    private ArrayList<PoissonEnnemi> poissonEnnemis;
    private ArrayList<Projectile> projectiles;
    private TypeProjectile typeProjectile = TypeProjectile.ETOILE;//commence a lancer des etoiles
    private Baril baril;

    // Temps écoulé depuis l'apparition du dernier groupe d'ennemis.
    private double tempDepuisDernierGroupe = 0;

    Random rnd = new Random();

    Partie(){
        gontrand = new Gontrand(new Point2D(0,0), //vitesse
                new Point2D(0,100), //position
                new Point2D(100,100), // accélération
                new Point2D(120,90), //taille
                new Image("gontrand.png"));
        poissonEnnemis = new ArrayList<>();
        camera = new Camera();
        projectiles = new ArrayList<>();

        double x = LARGEUR_NIVEAU * (0.15 + rnd.nextDouble() * 0.30);
        double y = (MainJavaFX.HEIGHT - 83) / 2;

        baril = new Baril(new Point2D(x, y));
    }
    public void update(double deltaTemps){
        // Activation/désactivation mode debug_D
        boolean dMaintenant = Input.isKeyPressed(KeyCode.D);

        if(dMaintenant && !dAvant){
            modeDebug = !modeDebug;
        }

        dAvant = dMaintenant;

        // Met à jour les déplacements de Gontrand.
        gontrand.update(deltaTemps);
        //faire le camera suivre gontrand
        camera.suivre(gontrand);
        // Empêche gontrand de soritr de l'écran.
        if(gontrand.getGauche()<camera.getPositionCamera().getX()){
            gontrand.setPosition(new Point2D(camera.getPositionCamera().getX(),gontrand.getPosition().getY()));
        }
        if(gontrand.getDroite()>LARGEUR_NIVEAU){
            gontrand.setPosition(new Point2D(LARGEUR_NIVEAU-gontrand.getTaille().getX(),gontrand.getPosition().getY()));
        }
        if(gontrand.getHaut()<0){
            gontrand.setPosition(new Point2D(gontrand.getPosition().getX(),0));
        }
        if(gontrand.getBas()>MainJavaFX.HEIGHT){
            gontrand.setPosition(new Point2D(gontrand.getPosition().getX(),MainJavaFX.HEIGHT-gontrand.getTaille().getY()));
        }

        // Fait apparaître un nouveau groupe toutes les 1.75 secondes.
        tempDepuisDernierGroupe += deltaTemps;
        if(tempDepuisDernierGroupe >= 1.75){
            creerGroupEnnemi();
            tempDepuisDernierGroupe = 0;
        }

        for (PoissonEnnemi poisson : poissonEnnemis) {
            poisson.update(deltaTemps);
            boolean collision = gontrand.getDroite()>poisson.getGauche()&&
                    gontrand.getGauche()<poisson.getDroite()&&
                    gontrand.getHaut()<poisson.getBas()&&
                    gontrand.getBas()>poisson.getHaut();
            if(collision){
                System.out.println("ouch!");
            }
        }
        //clear poisson ennemi une fois côté droite de l'image touche le côté gauche de la caméra

        poissonEnnemis.removeIf(poisson ->
                poisson.getDroite()<camera.getPositionCamera().getX());


        for (Projectile projectile : projectiles) {

            if (projectile instanceof BoiteSardines sardine) {

                Point2D champ = champElectrique(poissonEnnemis, sardine.getCentre());
                //update les boitesSardines en fonction des poissonEnnemis et du centre de la boite

                sardine.update(deltaTemps, champ, MainJavaFX.HEIGHT);

            } else {

                projectile.update(deltaTemps);//update le reste des projectils en appelant leur methode update respective
            }
        }
    }

    public void draw(GraphicsContext context){
        drawDebug(context);
        gontrand.draw(context,camera);

        for (PoissonEnnemi poisson : poissonEnnemis) {
            poisson.draw(context, camera);
        }

        for (Projectile projectile : projectiles) {
            projectile.draw(context, camera);
        }//dessiner les projectiles
    }

    public void drawDebug(GraphicsContext context){
        if(modeDebug){
            context.setStroke(Color.YELLOW);
            context.strokeLine(MainJavaFX.WIDTH*0.2,0,MainJavaFX.WIDTH*0.2,MainJavaFX.HEIGHT);
            context.setLineWidth(3);

            List<ObjetDuJeu> tousLesObjets = new ArrayList<>();
            tousLesObjets.add(gontrand);
            tousLesObjets.addAll(poissonEnnemis);

            for (ObjetDuJeu objet : tousLesObjets) {
                var coordonEcran = camera.coordoEcran(objet.position);
                Image img = objet.image;
                context.strokeRect(
                        coordonEcran.getX(),
                        coordonEcran.getY(),
                        img.getWidth(),
                        img.getHeight()
                );
            }
        }

    }


    // Crée un groupe de 1 à 5 poissons ennemis.
    private void creerGroupEnnemi(){
        String [] choixImages = {
                "poisson1.png",
                "poisson2.png",
                "poisson3.png",
                "poisson4.png",
                "poisson5.png"
        };

        String imageChoisi = choixImages[rnd.nextInt(choixImages.length)];//choisi un poisson au hasard

        // Position aléatoire à droite de l'écran.
        double x = camera.getPositionCamera().getX()+MainJavaFX.WIDTH+rnd.nextDouble(100,300);
        double y = rnd.nextDouble(MainJavaFX.HEIGHT*0.2, MainJavaFX.HEIGHT*0.8);
        // Taille de l'image
        double hauteur = rnd.nextDouble(50,121);
        double largeur = hauteur*120/104;
        //Vitesse du poisson
        double vitesseX = -(100*Math.pow(1,0.33)+200);
        double vitesseY = rnd.nextDouble(0,100);
        if(rnd.nextBoolean()){
            vitesseY = -vitesseY;
        }
        //Création d'un groupe de poisson ennemi
        int tailleGroupe = rnd.nextInt(1,6);
        for(int i = 0; i < tailleGroupe; i++){
            poissonEnnemis.add(
                    new PoissonEnnemi(
                            new Point2D(vitesseX, vitesseY),
                            new Point2D(x+i*200, y+i),
                            new Point2D(0, 0),
                            new Point2D(largeur, hauteur),
                            new Image(imageChoisi)
                    )
            );
        }
    }

    public void lancerProjectile() {

        switch (typeProjectile) {

            case ETOILE:
                Point2D position = gontrand.getCentre()
                        .subtract(new Point2D(36 / 2.0, 35 / 2.0));

                EtoileDeMer etoile = new EtoileDeMer(position);

                projectiles.add(etoile);//creer une etoile
                break;

            case HIPPOCAMPES:
                // Créer les trois hippocampes
                break;

            case SARDINES:
                // Créer une boîte de sardines
                break;
        }
    }

    public Point2D champElectrique(ArrayList<PoissonEnnemi> poissons, Point2D position) {
        Point2D champTotal = Point2D.ZERO;//commencer avec un champ nul

        double k = 8.99e9;

        for (PoissonEnnemi poisson : poissons) {

            Point2D direction = position.subtract(poisson.getCentre());

            double distance = Math.max(1, direction.magnitude());

            double module = k * CHARGE / (distance * distance);

            Point2D champ = direction.normalize().multiply(-module);

            champTotal = champTotal.add(champ);
        }


        return champTotal;
    }

}


