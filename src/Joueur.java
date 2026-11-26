final class Joueur {
    private final String nom;
    private final Chateau chateau;
    private boolean beni;

    Joueur(String nom, int positionChateau) {
        this.nom = nom;
        chateau = new Chateau(positionChateau);
    }

    String nom() { return nom; }
    Chateau chateau() { return chateau; }
    boolean estBeni() { return beni; }

    void benir() {
        beni = true;
    }

    int consommerMultiplicateur() {
        int multiplicateur = beni ? 2 : 1;
        beni = false;
        return multiplicateur;
    }
}
