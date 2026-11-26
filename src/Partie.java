import java.util.Random;

final class Partie {
    private final Plateau plateau;
    private final Evenements evenements;
    private final Joueur joueur1 = new Joueur("J1", 0);
    private final Joueur joueur2;
    private Joueur actif = joueur1;
    private Joueur vainqueur;

    private int prochainNumero = 1;

    private String annonceEvenement;

    Partie(String nomJoueur2, int taille, Random hasard) {
        plateau = new Plateau(taille);
        evenements = new Evenements(hasard);
        joueur2 = new Joueur(nomJoueur2, taille - 1);
        commencerTour();
    }

    String annonceEvenement() { return annonceEvenement; }

    Joueur actif() { return actif; }
    Joueur vainqueur() { return vainqueur; }
    Plateau plateau() { return plateau; }

    Joueur adversaire() {
        return actif == joueur1 ? joueur2 : joueur1;
    }

    String generer() {
        verifierPartieEnCours();
        actif.chateau().ajouterRessources(1);
        terminerTour();
        return "Une ressource supplémentaire gagnée.";
    }

    String invoquer(TypeGuerrier type) {
        verifierPartieEnCours();
        Chateau chateau = actif.chateau();
        chateau.payer(type.cout());
        Guerrier guerrier = type.creer(prochainNumero++, actif);
        plateau.caseA(chateau.position()).ajouter(guerrier);
        terminerTour();
        return guerrier + " apparaît dans son château.";
    }

    String deplacer(int numero, int destination) {
        verifierPartieEnCours();
        int depart = plateau.positionDe(numero);
        Case origine = plateau.caseA(depart);
        Guerrier guerrier = troupeAlliee(origine, numero);

        if (origine.contientEnnemi(actif)) {
            throw new ActionInvalideException("Un ennemi sur cette case bloque le déplacement.");
        }
        if (!plateau.contientPosition(destination) || Math.abs(destination - depart) != 1) {
            throw new ActionInvalideException("Choisis une case voisine du plateau.");
        }

        plateau.deplacer(guerrier, depart, destination);
        terminerTour();
        return "#" + numero + " se déplace sur la case " + (destination + 1) + ".";
    }

    String attaquer(int numero, int cible) {
        verifierPartieEnCours();
        Case lieu = plateau.caseA(plateau.positionDe(numero));
        Guerrier attaquant = troupeAlliee(lieu, numero);
        Guerrier defenseur = lieu.trouver(cible);

        if (defenseur == null || defenseur.proprietaire() == actif) {
            throw new ActionInvalideException("Choisis un ennemi sur la même case.");
        }

        int degats = attaquant.attaquer(defenseur, actif.consommerMultiplicateur());
        String resultat = "#" + numero + " inflige " + degats + " dégât(s) à #" + cible + ".";
        if (defenseur.estMort()) {
            lieu.retirer(defenseur);
            resultat += " La cible est éliminée.";
        }
        terminerTour();
        return resultat;
    }

    String attaquerChateau(int numero) {
        verifierPartieEnCours();
        Chateau cible = adversaire().chateau();
        int position = plateau.positionDe(numero);
        Case lieu = plateau.caseA(position);
        Guerrier attaquant = troupeAlliee(lieu, numero);

        if (position != cible.position()) {
            throw new ActionInvalideException("Place ton guerrier sur la case du château adverse.");
        }
        if (lieu.contientEnnemi(actif)) {
            throw new ActionInvalideException("Élimine les défenseurs avant d'attaquer le château.");
        }

        int degats = attaquant.attaquer(cible, actif.consommerMultiplicateur());
        String resultat = "#" + numero + " inflige " + degats
                + " dégât(s) au château adverse (" + cible.vie() + " PV restants).";
        if (cible.vie() == 0) {
            vainqueur = actif;
        } else {
            terminerTour();
        }
        return resultat;
    }

    String afficherPlateau() {
        StringBuilder texte = new StringBuilder();
        for (int i = 0; i < plateau.taille(); i++) {
            texte.append(i + 1);
            if (i == joueur1.chateau().position()) {
                ajouterEtatChateau(texte, joueur1);
            } else if (i == joueur2.chateau().position()) {
                ajouterEtatChateau(texte, joueur2);
            }
            var guerriers = plateau.caseA(i).guerriers();
            texte.append(" | ").append(guerriers.isEmpty() ? "vide" : guerriers)
                    .append(System.lineSeparator());
        }
        return texte.toString();
    }

    private void ajouterEtatChateau(StringBuilder texte, Joueur joueur) {
        Chateau chateau = joueur.chateau();
        texte.append(" [château ").append(joueur.nom()).append(", ")
                .append(chateau.vie()).append("/").append(Chateau.PV_MAX).append(" PV, ")
                .append(chateau.ressources()).append(" ressource(s)]");
    }

    private void verifierPartieEnCours() {
        if (vainqueur != null) {
            throw new ActionInvalideException("La partie est terminée.");
        }
    }

    private Guerrier troupeAlliee(Case lieu, int numero) {
        Guerrier guerrier = lieu.trouver(numero);
        if (guerrier.proprietaire() != actif) {
            throw new ActionInvalideException("Ce guerrier appartient à l'autre joueur.");
        }
        return guerrier;
    }

    private void terminerTour() {
        actif = adversaire();
        commencerTour();
    }

    private void commencerTour() {
        actif.chateau().ajouterRessources(1);
        annonceEvenement = evenements.debutTour(actif, plateau);
    }
}
