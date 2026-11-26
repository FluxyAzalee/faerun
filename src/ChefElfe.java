final class ChefElfe extends Elfe {
    ChefElfe(int numero, Joueur proprietaire) {
        super(numero, proprietaire, DOMMAGES_BASE * 4);
    }

    @Override
    String nom() { return "Chef elfe"; }
}
