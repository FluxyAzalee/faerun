import java.util.Locale;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== Faerun ===");
            String mode = choisirMode(scanner);
            if (mode.equals("q")) {
                return;
            }
            boolean solo = mode.equals("1");
            int taille = choisirTaille(scanner);
            if (taille == 0) {
                return;
            }
            Partie partie = new Partie(solo ? "Bot" : "J2", taille, new Random());
            jouer(scanner, partie, solo ? new Bot() : null, partie.adversaire());
        }
    }

    private static String choisirMode(Scanner scanner) {
        System.out.println("1. Jouer seul contre le bot");
        System.out.println("2. Jouer à deux");
        System.out.println("q. Quitter");
        System.out.print("> ");
        while (scanner.hasNextLine()) {
            String choix = scanner.nextLine().strip().toLowerCase(Locale.ROOT);
            if (choix.equals("1") || choix.equals("2") || choix.equals("q")) {
                return choix;
            }
            System.out.print("Choix invalide. Saisis 1, 2 ou q > ");
        }
        return "q";
    }

    private static int choisirTaille(Scanner scanner) {
        System.out.print("Nombre de cases (" + Plateau.TAILLE_MIN + " à " + Plateau.TAILLE_MAX + ", q pour quitter) > ");
        while (scanner.hasNextLine()) {
            String choix = scanner.nextLine().strip();
            if (choix.equalsIgnoreCase("q")) {
                return 0;
            }
            try {
                int taille = Integer.parseInt(choix);
                if (taille >= Plateau.TAILLE_MIN && taille <= Plateau.TAILLE_MAX) {
                    return taille;
                }
            } catch (NumberFormatException erreur) {
                // Le même message couvre un nombre invalide et une taille hors limites.
            }
            System.out.print("Choisis un nombre entier entre 7 et 12, ou q > ");
        }
        return 0;
    }

    private static void jouer(Scanner scanner, Partie partie, Bot bot, Joueur joueur2) {
        System.out.println("Détruis le château adverse pour gagner.");
        boolean afficherEvenement = true;
        while (partie.vainqueur() == null) {
            System.out.println();
            if (afficherEvenement && !partie.annonceEvenement().isEmpty()) {
                System.out.println(partie.annonceEvenement());
            }
            afficherEvenement = false;
            System.out.print(partie.afficherPlateau());
            Joueur joueur = partie.actif();
            if (joueur.estBeni()) {
                System.out.println(joueur.nom() + " : bénédiction active (prochain coup ×2).");
            }
            if (bot != null && joueur == joueur2) {
                System.out.println("Bot > " + bot.jouer(partie));
                afficherEvenement = true;
                continue;
            }

            System.out.println("Au tour de " + joueur.nom() + ".");
            System.out.println("g = générer | i <type> = invoquer | d <id> <case> = déplacer");
            System.out.println("a <id> <cible> = attaquer | c <id> = attaquer le château | q = quitter");
            System.out.println("Types : 1 elfe (2), 2 chef elfe (4), 3 nain (2), 4 chef nain (4)");
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                return;
            }

            String[] commande = scanner.nextLine().strip().toLowerCase(Locale.ROOT).split("\\s+");
            if (commande.length == 1 && commande[0].equals("q")) {
                return;
            }

            try {
                System.out.println(executer(partie, commande));
                afficherEvenement = true;
            } catch (NumberFormatException erreur) {
                System.out.println("Saisis des nombres entiers pour les arguments.");
            } catch (ActionInvalideException erreur) {
                System.out.println(erreur.getMessage());
            }
        }
        System.out.print(partie.afficherPlateau());
        System.out.println(partie.vainqueur().nom() + " remporte la partie !");
    }

    private static String executer(Partie partie, String[] commande) {
        return switch (commande[0]) {
            case "g" -> {
                verifierNombreArguments(commande, 1, "g");
                yield partie.generer();
            }
            case "i" -> {
                verifierNombreArguments(commande, 2, "i <type>");
                yield partie.invoquer(TypeGuerrier.depuisNumero(Integer.parseInt(commande[1])));
            }
            case "d" -> {
                verifierNombreArguments(commande, 3, "d <id> <case>");
                int destination = Integer.parseInt(commande[2]);
                if (destination < 1 || destination > partie.plateau().taille()) {
                    throw new ActionInvalideException("Cette case est hors du plateau.");
                }
                yield partie.deplacer(Integer.parseInt(commande[1]), destination - 1);
            }
            case "a" -> {
                verifierNombreArguments(commande, 3, "a <id> <cible>");
                yield partie.attaquer(Integer.parseInt(commande[1]), Integer.parseInt(commande[2]));
            }
            case "c" -> {
                verifierNombreArguments(commande, 2, "c <id>");
                yield partie.attaquerChateau(Integer.parseInt(commande[1]));
            }
            default -> throw new ActionInvalideException("Commande inconnue. Utilise le menu affiché.");
        };
    }

    private static void verifierNombreArguments(String[] commande, int longueur, String exemple) {
        if (commande.length != longueur) {
            throw new ActionInvalideException("Commande attendue : " + exemple);
        }
    }
}
