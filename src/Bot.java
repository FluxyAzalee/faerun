final class Bot {
    String jouer(Partie partie) {
        Plateau plateau = partie.plateau();
        Joueur joueur = partie.actif();
        int objectif = partie.adversaire().chateau().position();
        int direction = Integer.compare(objectif, joueur.chateau().position());

        Case caseChateauAdverse = plateau.caseA(objectif);
        if (!caseChateauAdverse.contientEnnemi(joueur)) {
            Guerrier assaillant = meilleurAttaquant(caseChateauAdverse, joueur);
            if (assaillant != null) {
                return partie.attaquerChateau(assaillant.numero());
            }
        }

        // Les combats près de notre château sont prioritaires.
        for (int position = joueur.chateau().position(); plateau.contientPosition(position);
                position += direction) {
            Case lieu = plateau.caseA(position);
            Guerrier attaquant = meilleurAttaquant(lieu, joueur);
            Guerrier cible = ciblePrioritaire(lieu, joueur);
            if (attaquant != null && cible != null) {
                return partie.attaquer(attaquant.numero(), cible.numero());
            }
        }

        // En l'absence de combat, on fait avancer la troupe la plus proche du but.
        for (int position = objectif - direction; plateau.contientPosition(position);
                position -= direction) {
            Guerrier guerrier = meilleurAttaquant(plateau.caseA(position), joueur);
            if (guerrier != null) {
                return partie.deplacer(guerrier.numero(), position + direction);
            }
        }

        int ressources = joueur.chateau().ressources();
        if (ressources >= TypeGuerrier.CHEF_ELFE.cout()) {
            return partie.invoquer(TypeGuerrier.CHEF_ELFE);
        }
        if (ressources >= TypeGuerrier.ELFE.cout()) {
            return partie.invoquer(TypeGuerrier.ELFE);
        }
        return partie.generer();
    }

    private Guerrier meilleurAttaquant(Case lieu, Joueur joueur) {
        Guerrier meilleur = null;
        for (Guerrier candidat : lieu.guerriers()) {
            if (candidat.proprietaire() == joueur
                    && (meilleur == null || candidat.dommages() > meilleur.dommages())) {
                meilleur = candidat;
            }
        }
        return meilleur;
    }

    private Guerrier ciblePrioritaire(Case lieu, Joueur joueur) {
        Guerrier cible = null;
        for (Guerrier candidat : lieu.guerriers()) {
            if (candidat.proprietaire() != joueur
                    && (cible == null || candidat.vie() < cible.vie())) {
                cible = candidat;
            }
        }
        return cible;
    }
}
