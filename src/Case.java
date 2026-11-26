import java.util.ArrayList;
import java.util.List;

final class Case {
    private final List<Guerrier> guerriers = new ArrayList<>();

    List<Guerrier> guerriers() {
        return List.copyOf(guerriers);
    }

    void ajouter(Guerrier guerrier) {
        guerriers.add(guerrier);
    }

    void retirer(Guerrier guerrier) {
        guerriers.remove(guerrier);
    }

    Guerrier trouver(int numero) {
        for (Guerrier guerrier : guerriers) {
            if (guerrier.numero() == numero) {
                return guerrier;
            }
        }
        return null;
    }

    boolean contientEnnemi(Joueur joueur) {
        for (Guerrier guerrier : guerriers) {
            if (guerrier.proprietaire() != joueur) {
                return true;
            }
        }
        return false;
    }
}

