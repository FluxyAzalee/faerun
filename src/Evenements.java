import java.util.Random;

final class Evenements {
    private static final int CHANCE = 15;
    private static final int TOURS_CALMES = 3;

    private final Random hasard;
    private int toursAvantTirage = 1;

    Evenements(Random hasard) {
        this.hasard = hasard;
    }

    String debutTour(Joueur actif, Plateau plateau) {
        if (toursAvantTirage > 0) {
            toursAvantTirage--;
            return "";
        }
        if (hasard.nextInt(100) >= CHANCE) {
            return "";
        }

        toursAvantTirage = TOURS_CALMES;
        Evenement[] evenements = Evenement.values();
        return switch (evenements[hasard.nextInt(evenements.length)]) {
            case BENEDICTION -> benir(actif);
            case EPIDEMIE -> epidemie(plateau);
            case RECOLTE -> recolte(actif);
        };
    }

    private String benir(Joueur actif) {
        actif.benir();
        return "Bénédiction divine ! Le prochain coup de " + actif.nom() + " infligera le double de dégâts.";
    }

    private String epidemie(Plateau plateau) {
        int touches = 0;
        int morts = 0;
        for (int i = 1; i < plateau.taille() - 1; i++) {
            Case lieu = plateau.caseA(i);
            for (Guerrier guerrier : lieu.guerriers()) {
                guerrier.subirDegats(30);
                touches++;
                if (guerrier.estMort()) {
                    lieu.retirer(guerrier);
                    morts++;
                }
            }
        }
        return "Épidémie ! Les guerriers hors des châteaux perdent 30 PV ("
                + touches + " touché(s), " + morts + " éliminé(s)).";
    }

    private String recolte(Joueur actif) {
        int bonus = 2 + hasard.nextInt(3);
        actif.chateau().ajouterRessources(bonus);
        return "Grande récolte ! " + actif.nom() + " gagne " + bonus + " ressources.";
    }
}
