class Nain extends Guerrier {
    Nain(int numero, Joueur proprietaire) {
        this(numero, proprietaire, DEFENSE_BASE * 2);
    }

    protected Nain(int numero, Joueur proprietaire, int defense) {
        super(numero, proprietaire, VIE_BASE, defense, DOMMAGES_BASE);
    }

    @Override
    String nom() { return "Nain"; }
}
