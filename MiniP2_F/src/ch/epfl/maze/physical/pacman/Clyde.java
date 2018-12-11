package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Orange ghost from the Pac-Man game, alternates between direct chase if far
 * from its target and SCATTER if close.
 * 
 */

public class Clyde extends Ghost {

    /**
     * Constructs a Clyde with a starting position.
     * 
     * @param position
     *            Starting position of Clyde in the labyrinth
     */

    public Clyde(Vector2D position) {
        super(position);
    }

    /**
     * Redéfinition de la méthode getTarget héritée de la super-classe Ghost. En
     * mode "CHASE",la stratégie du Clyde étant de faire des va-et-vient entre
     * sa proie et sa zone maison: sa cible est donc sa proie quand il se trouve
     * à plus de 4 cases de celle-ci et dans le cas contraire sa cible devient
     * sa maison. En mode "SCATTER", le Clyde rentre dans sa zone "maison": sa
     * cible devient sa zone "maison"
     * 
     * @param daedalus
     *            Le dédale dans lequel bouge le Clyde
     * @return la cible du Clyde
     */
    protected Vector2D getTarget(Daedalus daedalus) {
        Vector2D cible = new Vector2D(0, 0);
        if (!(scatter())) {
            Prey prey = choosePrey(daedalus.getPreys());
            if (this.getPosition().sub(prey.getPosition()).dist() > 4) {
                cible = prey.getPosition();
            } else {
                cible = getHome();
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
        return new Clyde(this.getPosition());
    }
}
