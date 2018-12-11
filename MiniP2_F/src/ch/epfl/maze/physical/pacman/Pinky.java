package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Pink ghost from the Pac-Man game, targets 4 squares in front of its target.
 * 
 */

public class Pinky extends Ghost {

    /**
     * Constructs a Pinky with a starting position.
     * 
     * @param position
     *            Starting position of Pinky in the labyrinth
     */

    public Pinky(Vector2D position) {
        super(position);
    }

    /**
     * Redéfinition de la méthode getTarget héritée de la super-classe Ghost.
     * 
     * En mode "CHASE",la stratégie du Blinky étant de barrer la route à sa proie:
     * sa cible se trouve donc 4 cases devant sa proie.
     * En mode "SCATTER", le Pinky rentre dans sa zone "maison": sa cible devient sa zone "maison"
     * 
     * @return la cible du Pinky
     */
    @Override
    protected Vector2D getTarget(Daedalus daedalus) {
        Vector2D cible = new Vector2D(0, 0);
        if (!(scatter())) {
            Prey prey = choosePrey(daedalus.getPreys());
            cible = new Vector2D(prey.getPosition().getX(), prey.getPosition()
                    .getY());
            Direction preyDir = prey.getPreviousChoice();
            if (preyDir == Direction.RIGHT) {
                cible = new Vector2D(prey.getPosition().getX() + 4, prey
                        .getPosition().getY());
            }
            if (preyDir == Direction.LEFT) {
                cible = new Vector2D(prey.getPosition().getX() - 4, prey
                        .getPosition().getY());
            }
            if (preyDir == Direction.UP) {
                cible = new Vector2D(prey.getPosition().getX(), prey
                        .getPosition().getY() - 4);
            }
            if (preyDir == Direction.DOWN) {
                cible = new Vector2D(prey.getPosition().getX(), prey
                        .getPosition().getY() + 4);
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
        return new Pinky(this.getHome());
    }
}