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

    private Color couleurFond;
    private boolean retourAccueil = false;
    private boolean finDePartie = false;
    private double tempsFinDePartie = 3;
    private int numeroNiveau = 1;
    private double tempsAffichageNiveau = 4;
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
        genererCouleurFond();

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

    public Color getCouleurFond() {
        return couleurFond;
    }

    public void genererCouleurFond(){
        double hue = rnd.nextDouble(190,271);
        couleurFond = Color.hsb(hue, 0.84,1.0);
    }

    public void update(double deltaTemps){
        // Activation/désactivation mode debug_D
        boolean dMaintenant = Input.isKeyPressed(KeyCode.D);

        if(dMaintenant && !dAvant){
            modeDebug = !modeDebug;
        }

        dAvant = dMaintenant;

        // Activation/désactivation mode Pause
        boolean pMaintenant = Input.isKeyPressed(KeyCode.P);

        if(pMaintenant && !pAvant){
            pause = !pause;
        }
        pAvant = pMaintenant;

        if(finDePartie){
            tempsFinDePartie -= deltaTemps;

            if(tempsFinDePartie <= 0){
                retourAccueil = true;
            }

            return;
        }


        if(!pause){
            if(tempsAffichageNiveau > 0){
                tempsAffichageNiveau -= deltaTemps;
            }
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

            // Fait apparaître un nouveau groupe
            double tempsEntreGroupes = 0.75 + 1/ Math.sqrt(numeroNiveau);
            tempDepuisDernierGroupe += deltaTemps;
            if(tempDepuisDernierGroupe >= tempsEntreGroupes ){
                creerGroupEnnemi();
                tempDepuisDernierGroupe = 0;
            }

            for (PoissonEnnemi poisson : poissonEnnemis) {
                poisson.update(deltaTemps);
                if(gontrand.collisionneAvec(poisson)){
                    gontrand.perdreVie();
                    if(gontrand.getVies() <= 0){
                        finDePartie = true;
                    }
                }
            }

            //clear poisson ennemi une fois collision

            poissonEnnemis.removeIf(poisson-> gontrand.collisionneAvec(poisson));


            for (Projectile projectile : projectiles) {

                if (projectile instanceof BoiteSardines sardine) {

                    Point2D champ = champElectrique(poissonEnnemis, sardine.getCentre());
                    //update les boitesSardines en fonction des poissonEnnemis et du centre de la boite

                    sardine.update(deltaTemps, champ, MainJavaFX.HEIGHT);

                } else {

                    projectile.update(deltaTemps);//update le reste des projectils en appelant leur methode update respective
                }
            }
            verifierCollisionsProjectiles();//
            //met a jour les deplacements du baril en sassurant quil disparait lorsque la camerta le depasse
            if (baril != null) {
                baril.update(deltaTemps);

                // Vérifier si Gontrand touche le baril
                if (baril.toucherGontrand(gontrand)) {
                    changerTypeProjectile();
                }

                // Vérifier si le baril est sorti de l'écran
                double droiteBarilEcran = camera.coordoEcran(
                        new Point2D(baril.getDroite(), baril.getPosition().getY())
                ).getX();

                if (droiteBarilEcran < 0) {
                    baril = null;
                }
            }

            if(gontrand.getDroite()>=LARGEUR_NIVEAU){
                prochainNiveau();
            }

        }

    }

    public void draw(GraphicsContext context){

        if(tempsAffichageNiveau > 0){
            context.setFill(Color.WHITE);

            context.setFont(Font.font(80));

            context.fillText("NIVEAU "+ numeroNiveau,
                    MainJavaFX.WIDTH/2-150,
                    MainJavaFX.HEIGHT/2);
        }

        drawDebug(context);

        double largeurVie = 150 * gontrand.getVies()/4;
        context.setFill(Color.WHITE);
        context.fillRect(20,20,largeurVie,20);
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

            context.setFont(Font.font(100));

            context.fillText(
                    "PAUSE",
                    MainJavaFX.WIDTH / 2.0 -150,
                    MainJavaFX.HEIGHT / 2.0
            );
        }

        if (finDePartie) {
            context.setFill(Color.DARKRED);
            context.fillText("FIN DE PARTIE", MainJavaFX.WIDTH/2-250,MainJavaFX.HEIGHT/2);

        }
    }

    public void prochainNiveau(){
        genererCouleurFond();
        numeroNiveau++;
        poissonEnnemis.clear();
        gontrand.setPosition(new Point2D(0,100));
        gontrand.setVies(4);
        camera.setPositionCamera(new Point2D(0,0));
        tempsAffichageNiveau = 4;

    }

    public boolean isRetourAccueil(){
        return retourAccueil;
    }
    public void resetFinDePartie(){
        retourAccueil = false;
        finDePartie = false;
        tempsFinDePartie = 3;
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

        //Création d'un groupe de poisson ennemi
        int tailleGroupe = rnd.nextInt(1,6);
        for(int i = 0; i < tailleGroupe; i++){
            // Taille de l'image
            double hauteur = rnd.nextDouble(50,121);
            double largeur = hauteur*120/104;

            //Vitesse du poisson
            double vitesseX = -(100*Math.pow(numeroNiveau,0.33)+200);
            double vitesseY = rnd.nextDouble(0,100);
            if(rnd.nextBoolean()){
                vitesseY = -vitesseY;
            }

            PoissonEnnemi poisson = new PoissonEnnemi(
                            new Point2D(vitesseX, vitesseY),
                            new Point2D(x+i*200, y+i*150),
                            new Point2D(-500, 0),
                            new Point2D(largeur, hauteur),
                            new Image(choixImages[rnd.nextInt(choixImages.length)])
            );

            poissonEnnemis.add(poisson);

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

    private void changerTypeProjectile() {

        switch (typeProjectile) {

            case ETOILE:
                typeProjectile = TypeProjectile.HIPPOCAMPES;
                break;

            case HIPPOCAMPES:
                typeProjectile = TypeProjectile.SARDINES;
                break;

            case SARDINES:
                typeProjectile = TypeProjectile.ETOILE;
                break;
        }
    }

    private void verifierCollisionsProjectiles() {

        for (Projectile projectile : projectiles) {

            for (int i = 0; i < poissonEnnemis.size(); i++) {

                PoissonEnnemi poisson = poissonEnnemis.get(i);

                if (projectile.collisionneAvec(poisson)) {

                    poissonEnnemis.remove(i);
                    i--;
                }
            }
        }
    }

    public Point2D champElectrique(ArrayList<PoissonEnnemi> poissons, Point2D position) {
        Point2D champTotal = Point2D.ZERO;//commencer avec un champ nul

        double k = 9e9;
        double chargePoisson = -900;

        for (PoissonEnnemi poisson : poissons) {

            Point2D direction = position.subtract(poisson.getCentre());

            double distance = Math.max(1, direction.magnitude()); //calculer la distance entre le centre du poisson et la boîte de sardines(jamais sous 1)
            //direction.magnitude() Calcule la longueur du vecteur direction
            double module = k * Math.abs(chargePoisson) / (distance * distance);// Ei= (k|qi|)/r^2

            Point2D directionUnitaire = direction.normalize();
            Point2D champ = directionUnitaire.multiply(module);

            // Le champ est inversé parce que le poisson est négatif.
            champ = champ.multiply(-1);

            champTotal = champTotal.add(champ);
        }


        return champTotal;
    }

}


