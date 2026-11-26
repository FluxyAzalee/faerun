class Elfe extends Guerrier {
    Elfe(int numero, Joueur proprietaire) {
        this(numero, proprietaire, DOMMAGES_BASE * 2);
    }

    protected Elfe(int numero, Joueur proprietaire, int dommages) {
        super(numero, proprietaire, VIE_BASE, DEFENSE_BASE, dommages);
    }

    @Override
    String nom() { return "Elfe"; }
}
