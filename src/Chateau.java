final class Chateau {
    static final int PV_MAX = 100;

    private final int position;
    private int ressources;
    private int vie = PV_MAX;

    Chateau(int position) {
        this.position = position;
    }

    int position() { return position; }
    int ressources() { return ressources; }
    int vie() { return vie; }

    void ajouterRessources(int quantite) {
        ressources += quantite;
    }

    void payer(int cout) {
        if (ressources < cout) {
            throw new ActionInvalideException("Ressources insuffisantes.");
        }
        ressources -= cout;
    }

    int subirDegats(int dommages) {
        int degats = Math.min(vie, dommages);
        vie -= degats;
        return degats;
    }
}
