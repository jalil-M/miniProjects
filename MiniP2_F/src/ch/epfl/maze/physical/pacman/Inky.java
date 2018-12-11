package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Blue ghost from the Pac-Man game, targets the result of two times the vector
 * from Blinky to its target.
 * 
 */

public class Inky extends Ghost {

    /**
     * Constructs a Inky with a starting position.
     * 
     * @param position
     *            Starting position of Inky in the labyrinth
     */

    public Inky(Vector2D position) {
        super(position);
    }

    /**
     * Redéfinition de la méthode getTarget héritée de la super-classe Ghost.
     * 
     * En mode "CHASE",la stratégie de Inky étant de de se projeter à deux fois
     * la distance depuis Blinky jusqu'à sa proie: sa cible est donc la case où
     * pointe le double du vecteur allant de Blinky vers sa proie. En mode
     * "SCATTER", le Blinky rentre dans sa zone "maison": sa cible devient sa
     * zone "maison"
     * 
     * @param daedalus
     *            Le dédale dans lequel bouge l'Inky
     * @return la cible du Inky
     */
    @Override
    protected Vector2D getTarget(Daedalus daedalus) {
        Vector2D cible = new Vector2D(0, 0);
        if (!(scatter())) {
            Predator blinky = null;
            Prey prey = choosePrey(daedalus.getPreys());
            for (Predator predator : daedalus.getPredators()) {
                if (predator instanceof Blinky) {
                    blinky = predator;
                    cible = (prey.getPosition().mul(2)).sub(blinky
                            .getPosition());
                }
            }
        } else {
            cible = getHome();
        }
        return cible;
    }

    @Override
    public Direction move(Direction[] choices, Daedalus daedalus) {
        return super.move(choices, daedalus);
    }

    @Override
    public Animal copy() {
        return new Inky(this.getPosition());
    }
}
