abstract class Guerrier {
    static final int VIE_BASE = 100;
    static final int DEFENSE_BASE = 5;
    static final int DOMMAGES_BASE = 10;
    private final int numero;
    private final Joueur proprietaire;
    private final int defense;
    private final int dommages;
    private int vie;

    protected Guerrier(int numero, Joueur proprietaire, int vie, int defense, int dommages) {
        this.numero = numero;
        this.proprietaire = proprietaire;
        this.vie = vie;
        this.defense = defense;
        this.dommages = dommages;
    }

    int numero() { return numero; }
    Joueur proprietaire() { return proprietaire; }
    int vie() { return vie; }
    int defense() { return defense; }
    int dommages() { return dommages; }
    abstract String nom();

    int subirDegats(int dommagesSubis) {
        int degats = Math.min(vie, dommagesSubis);
        vie -= degats;
        return degats;
    }

    int attaquer(Guerrier cible, int multiplicateur) {
        int degats = Math.max(1, dommages - cible.defense() + 1);
        return cible.subirDegats(degats * multiplicateur);
    }

    int attaquer(Chateau cible, int multiplicateur) {
        return cible.subirDegats(dommages * multiplicateur);
    }

    boolean estMort() {
        return vie == 0;
    }

    @Override
    public String toString() {
        return "#" + numero + " " + nom() + " (" + proprietaire.nom()
                + ", " + vie + " PV, " + dommages + " ATQ, " + defense + " DEF)";
    }
}
