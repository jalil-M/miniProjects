package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Red ghost from the Pac-Man game, chases directly its target.
 * 
 */

public class Blinky extends Ghost {
    /**
     * Constructs a Blinky with a starting position.
     * 
     * @param position
     *            Starting position of Blinky in the labyrinth
     */

    public Blinky(Vector2D position) {
        super(position);
    }

    /**
     * Redéfinition de la méthode getTarget héritée de la super-classe Ghost. En
     * mode "CHASE",la stratégie du Blinky étant de chasser directement sa
     * proie: sa cible est donc sa proie. En mode "SCATTER", le Blinky rentre
     * dans sa zone "maison": sa cible devient sa zone "maison"
     *
     * @param daedalus
     *            Le dédale dans lequel bouge le Blinky
     *
     * @return la cible du Blinky
     */
    @Override
    protected Vector2D getTarget(Daedalus daedalus) {
        if (!(scatter())) {
            return choosePrey(daedalus.getPreys()).getPosition();
        } else {
            return getHome();
        }
    }

    @Override
    public Direction move(Direction[] choices, Daedalus daedalus) {
        return super.move(choices, daedalus);

    }

    @Override
    public Animal copy() {
        return new Blinky(this.getPosition());
    }
}