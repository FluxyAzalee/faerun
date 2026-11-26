# Faerun

Faerun est un jeu de stratégie au tour par tour. Pour gagner, détruis le château adverse.

## Lancer une partie

Avec Java 17 ou plus récent, lance la classe `Main` depuis ton IDE. Sous PowerShell, tu peux aussi utiliser ces commandes depuis le dossier du projet.

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem src/*.java).FullName
java -cp out Main
```

Choisis `1` pour jouer contre le bot ou `2` pour jouer à deux sur le même clavier. Choisis ensuite un plateau de **7 à 12 cases**. Les châteaux occupent la première et la dernière case et commencent chacun avec **100 PV**.

## Déroulement d’un tour

Au début de ton tour, ton château reçoit **1 ressource**. Tu peux ensuite effectuer **une seule action**. Une commande invalide te laisse rejouer.

| Commande | Action |
| --- | --- |
| `g` | Gagner 1 ressource supplémentaire. |
| `i 1` à `i 4` | Invoquer le guerrier choisi dans ton château. |
| `d 3 2` | Déplacer ton guerrier nº 3 vers la case 2 si elle est voisine. |
| `a 3 5` | Attaquer l’ennemi nº 5 avec ton guerrier nº 3 sur la même case. |
| `c 3` | Attaquer le château adverse avec ton guerrier nº 3. |
| `q` | Quitter la partie. |

Au premier tour, tu as une ressource. Joue `g` pour en avoir deux. Au début de ton tour suivant, tu en auras au moins trois et tu pourras jouer `i 1` pour invoquer un elfe. Son identifiant s’affiche avec un `#` sur le plateau.

## Guerriers

| Type | Guerrier | Coût | PV | Attaque | Défense |
| --- | --- | ---: | ---: | ---: | ---: |
| 1 | Elfe | 2 | 100 | 20 | 5 |
| 2 | Chef elfe | 4 | 100 | 40 | 5 |
| 3 | Nain | 2 | 100 | 10 | 10 |
| 4 | Chef nain | 4 | 100 | 10 | 20 |

## Déplacements et combats

- Plusieurs guerriers, alliés ou ennemis, peuvent occuper la même case. Tu peux entrer sur une case occupée par un ennemi, mais tu ne peux plus en sortir tant qu’un ennemi s’y trouve.
- Pour attaquer un guerrier, place-toi sur sa case. Les dégâts valent **attaque − défense + 1**, avec un minimum de **1**. Un guerrier à 0 PV est retiré du plateau.
- Pour attaquer le château adverse, place un guerrier sur sa case et élimine les défenseurs présents. Le château subit les dégâts d’attaque du guerrier sans réduction de défense. À 0 PV, le château est détruit et la partie se termine.

## Événements

À partir du deuxième tour, un événement a **15 % de chances** de se déclencher en début de tour. Après un événement, les **trois tours suivants** se déroulent sans tirage.

- **Bénédiction divine** — le prochain coup du joueur actif inflige le double de dégâts, contre un guerrier ou un château. Le bonus reste disponible jusqu’à son utilisation.
- **Épidémie** — tous les guerriers situés hors des deux cases de château perdent 30 PV, quel que soit leur camp.
- **Grande récolte** — le joueur actif gagne de 2 à 4 ressources supplémentaires.
