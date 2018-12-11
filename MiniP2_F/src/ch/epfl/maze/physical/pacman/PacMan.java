package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Pac-Man character, from the famous game of the same name.
 * 
 */

public class PacMan extends Prey {

    public PacMan(Vector2D position) {
        super(position);
    }

    /**
     * Le pacman se déplace selon une marche aléatoire basique (comme la souris)
     * 
     * @param choices
     *            Les choix disponibles restants au pacman à partir de sa
     *            position.
     * @param daedalus
     *            Dédale dans lequel bouge le pacman.
     * @return The next direction of the animal, chosen in {@code choices}
     */

    @Override
    public Direction move(Direction[] choices, Daedalus daedalus) {
        return super.move(choices);

    }

    @Override
    public Animal copy() {
        return new PacMan(this.getHome());
    }
}
