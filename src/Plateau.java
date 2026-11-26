final class Plateau {
    static final int TAILLE_MIN = 7;
    static final int TAILLE_MAX = 12;
    private final Case[] cases;

    Plateau(int taille) {
        if (taille < TAILLE_MIN || taille > TAILLE_MAX) {
            throw new IllegalArgumentException("Le plateau doit contenir de 7 à 12 cases.");
        }
        cases = new Case[taille];
        for (int i = 0; i < taille; i++) {
            cases[i] = new Case();
        }
    }

    int taille() { return cases.length; }

    boolean contientPosition(int position) {
        return position >= 0 && position < taille();
    }

    Case caseA(int position) {
        return cases[position];
    }

    int positionDe(int numero) {
        for (int i = 0; i < taille(); i++) {
            if (cases[i].trouver(numero) != null) {
                return i;
            }
        }
        throw new ActionInvalideException("Guerrier #" + numero + " introuvable.");
    }

    void deplacer(Guerrier guerrier, int depart, int destination) {
        caseA(depart).retirer(guerrier);
        caseA(destination).ajouter(guerrier);
    }
}
