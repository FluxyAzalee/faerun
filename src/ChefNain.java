final class ChefNain extends Nain {
    ChefNain(int numero, Joueur proprietaire) {
        super(numero, proprietaire, DEFENSE_BASE * 4);
    }

    @Override
    String nom() { return "Chef nain"; }
}
