enum TypeGuerrier {
    ELFE(2), CHEF_ELFE(4), NAIN(2), CHEF_NAIN(4);

    private final int cout;

    TypeGuerrier(int cout) {
        this.cout = cout;
    }

    int cout() { return cout; }

    static TypeGuerrier depuisNumero(int numero) {
        TypeGuerrier[] types = values();
        if (numero < 1 || numero > types.length) {
            throw new ActionInvalideException("Choisis un type entre 1 et 4.");
        }
        return types[numero - 1];
    }

    Guerrier creer(int numero, Joueur joueur) {
        return switch (this) {
            case ELFE -> new Elfe(numero, joueur);
            case CHEF_ELFE -> new ChefElfe(numero, joueur);
            case NAIN -> new Nain(numero, joueur);
            case CHEF_NAIN -> new ChefNain(numero, joueur);
        };
    }
}
