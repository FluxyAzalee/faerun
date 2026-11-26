# Faerûn

Faerûn est un jeu de stratégie en console. Joue seul contre le bot ou à deux sur le même clavier. Pour gagner, détruis le château adverse.

## Lancer une partie

Avec Java 17 ou plus récent, ouvre PowerShell dans le dossier du projet et lance :

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem src/*.java).FullName
java -cp out Main
```

Tape `1` pour jouer contre le bot ou `2` pour jouer à deux.

## Règles du jeu

- Chaque château commence avec **8 PV** et stocke les ressources de son joueur.
- Au début de ton tour, tu gagnes **1 ressource**, puis tu effectues **une action**. Une commande invalide te laisse rejouer.
- Les troupes sont invoquées dans ton château. Elles se déplacent d’une case à la fois et ne peuvent pas quitter une case occupée par un ennemi.
- Pour attaquer une troupe adverse, tu dois être sur la même case. Les dégâts valent **dommages de l’attaquant − défense de la cible + 1**, avec un minimum de **1**. Une troupe à **0 PV** est retirée.
- Pour attaquer le château adverse, ta troupe doit être sur sa case et avoir éliminé les défenseurs présents. Le château perd autant de PV que les dommages de la troupe.

## Commandes

| Commande | Action |
| --- | --- |
| `g` | Gagner une ressource supplémentaire |
| `i 1` à `i 4` | Invoquer une troupe selon son type |
| `d 1 2` | Déplacer la troupe n° 1 vers la case voisine n° 2 |
| `a 1 2` | Faire attaquer la troupe n° 1 contre l’ennemi n° 2 sur la même case |
| `c 1` | Faire attaquer le château adverse par la troupe n° 1 |
| `q` | Quitter la partie |

## Troupes

| Type | Troupe | Coût | PV | Dommages | Défense |
| --- | --- | ---: | ---: | ---: | ---: |
| `1` | Elfe | 2 | 4 | 2 | 1 |
| `2` | Chef elfe | 4 | 4 | 4 | 1 |
| `3` | Nain | 2 | 4 | 1 | 2 |
| `4` | Chef nain | 4 | 4 | 1 | 4 |