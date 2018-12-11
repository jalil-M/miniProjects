package ch.epfl.maze.physical.pacman;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 *
 * Fantôme qui tue une proie quand ils se rencontrent dans un labyrinthe.
 * 
 */

abstract public class Ghost extends Predator {

    Random random = new Random();
    private Direction previousChoice;
    private int counter;

    /**
     * Construit un fantôme avec une position de départ.
     * 
     * @param position
     *            Position de départ du fantôme dans le labyrinthe
     */
    protected Ghost(Vector2D position) {
        super(position);
        previousChoice = Direction.NONE;
        counter = 0;
    }

    /**
     * Détermine si le fantôme est en mode "SCATTER" ou en mode "CHASE".
     * 
     * @return <b>vrai</b> si le fantôme est en mode "SCATTER" , <b>faux</b>
     *         sinon
     */
    protected boolean scatter() {
        ++counter;
        if (counter > CHASE_DURATION + SCATTER_DURATION) {
            counter = 0;
        }
        return counter >= CHASE_DURATION;

    }

    /**
     * Détermine la cible du fantôme.
     * 
     * @param daedalus
     *            Dédale (monde) dans lequel se déplace le fantôme
     * @return La position de la cible du fantôme
     */
    protected abstract Vector2D getTarget(Daedalus daedalus);

    /**
     * Redéfinition de la méthode abstraite de la super-classe Predator.
     * 
     * Permet au fantôme de se déplacer tout en ayant une connaissance globale
     * du labyrinthe.
     * 
     * Retourne la prochaine direction du fantôme en sélectionnant un choix
     * parmi les choix disponibles à partir de sa position.
     * 
     * @param choices
     *            Les choix disponibles restants au fantôme à sa position
     * @param daedalus
     *            Le monde où le fantôme bouge
     * @return La prochaine direction du fantôme
     */
    @Override
    public Direction move(Direction[] choices, Daedalus daedalus) {
        Vector2D cible = getTarget(daedalus);
        if (previousChoice == Direction.NONE) {
            previousChoice = choices[random.nextInt(choices.length)];
        }
        /*
         * Cas où le fantôme arrive à une impasse: Il emprunte la seule
         * direction proposée.
         */
        if (choices.length == 1) {
            previousChoice = choices[0];
        }
        /*
         * Cas où le fantôme se trouve dans un couloir: Il emprunte la direction
         * qui ne le fait pas revenir en arrière.
         */
        else if (choices.length == 2) {
            if (!(previousChoice.isOpposite(choices[0]))) {
                previousChoice = choices[0];
            } else {
                previousChoice = choices[1];
            }

        }
        /*
         * Cas où le fantôme arrive à une intersection
         */
        else if (choices.length >= 3) {
            ArrayList<Direction> newChoices = new ArrayList<>();
            // Boucle qui permet de remplir l'arrayList newChoices avec les choix de
            // directions qui ne font pas revenir le fantôme en arrière
            for (int i = 0; i < choices.length; i++) {
                if (!(previousChoice.isOpposite(choices[i]))) {
                    newChoices.add(choices[i]);
                }
            }
            // Comparaison de la distance euclidienne entre la cible et chaque case de l'intersection
            double dist1 = (cible.sub(getPosition().addDirectionTo(
                    newChoices.get(0)))).dist();
            double dist2 = (cible.sub(getPosition().addDirectionTo(
                    newChoices.get(1)))).dist();
            double dist3 = Double.MAX_VALUE;
            if (newChoices.size() == 3) {
                dist3 = (cible.sub(getPosition().addDirectionTo(
                        newChoices.get(2)))).dist();
            }
            double shortest = Math.min(dist1, Math.min(dist2, dist3));
            
            // Sélectionne la direction qui minimise la distance euclidienne précédemment calculée
            if (dist1 == shortest) {
                previousChoice = newChoices.get(0);
            } else if (dist2 == shortest) {
                previousChoice = newChoices.get(1);
            } else if (dist3 == shortest) {
                previousChoice = newChoices.get(2);
            }
        }

        return previousChoice;
    }
}
