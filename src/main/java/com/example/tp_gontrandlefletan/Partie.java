package com.example.tp_gontrandlefletan;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.tp_gontrandlefletan.BoiteSardines.CHARGE;

public class Partie {
    private boolean pause = false;
    private boolean pAvant = false;
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

        double x = LARGEUR_NIVEAU * (0.2 + rnd.nextDouble() * 0.6);//pos entre 1/5 et 4/5 au hasard
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

        // Activation/désactivation mode Pause
        boolean pMaintenant = Input.isKeyPressed(KeyCode.SPACE);

        if(pMaintenant && !pAvant){
            pause = !pause;
        }
        pAvant = pMaintenant;


        if(!pause){
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
                    gontrand.perdreVie();
                    System.out.println(gontrand.getVies());
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
            baril.update(deltaTemps);//met a jour les deplacements du baril

        }

    }

    public void draw(GraphicsContext context){
        if(pause){
            context.setFill(
                    Color.rgb(0,0,0,0.4)
            );

            context.fillRect(
                    0,
                    0,
                    MainJavaFX.WIDTH,
                    MainJavaFX.HEIGHT
            );

            context.setFill(Color.WHITE);

            context.setFont(Font.font(35));

            context.fillText(
                    "PAUSE",
                    MainJavaFX.WIDTH / 2.0 - 60,
                    MainJavaFX.HEIGHT / 2.0
            );
        }

        drawDebug(context);

        double laergeurVie = 150 * gontrand.getVies()/4;
        context.setFill(Color.WHITE);
        context.fillRect(20,20,laergeurVie,20);
        context.setStroke(Color.WHITE);
        context.strokeRect(20,20,150,20);

        gontrand.draw(context,camera);

        for (PoissonEnnemi poisson : poissonEnnemis) {
            poisson.draw(context, camera);
        }

        for (Projectile projectile : projectiles) {
            projectile.draw(context, camera);
        }//dessiner les projectiles

        if (baril != null) {
            baril.draw(context, camera);//dessiner un baril
        }
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
                            new Point2D(x+i*200, y+i*100),
                            new Point2D(0, 0),
                            new Point2D(largeur, hauteur),
                            new Image(choixImages[rnd.nextInt(choixImages.length)])
                    )
            );
        }
    }

    public void lancerProjectile() {

        switch (typeProjectile) {

            case ETOILE:
                Point2D position = gontrand.getCentre()
                        .subtract(new Point2D(36 / 2.0, 35 / 2.0));

                EtoileDeMer etoile = new EtoileDeMer(position);//car je ne lai pas fait dans sa classe

                projectiles.add(etoile);//creer une etoile
                break;

            case HIPPOCAMPES:
                // Créer les trois hippocampes
                ArrayList<Hippocampe> trio = Hippocampe.creerTrio(gontrand.getCentre());

                projectiles.addAll(trio);
                break;

            case SARDINES:
                // Créer une boîte de sardines
                Point2D positionSardines = gontrand.getCentre()
                        .subtract(new Point2D(35 / 2.0, 29 / 2.0));

                BoiteSardines sardines = new BoiteSardines(positionSardines);
                projectiles.add(sardines);
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


