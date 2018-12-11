package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Predator that kills a prey when they meet with each other in the labyrinth.
 * 
 */

abstract public class Predator extends Animal {
    private Direction previousChoice;
    private Random random;
    private final Vector2D home;
    /* constants relative to the Pac-Man game */
    protected static final int SCATTER_DURATION = 14;
    protected static final int CHASE_DURATION = 40;

    /**
     * Constructs a predator with a specified position.
     * 
     * @param position
     *            Position of the predator in the labyrinth
     */

    protected Predator(Vector2D position) {
        super(position);
        random = new Random();
        home = position;
        previousChoice = Direction.NONE;
    }

    /**
     * Getter qui permet de retourner la maison du prédateur
     * 
     * @return Vector2D home : Position de la maison du prédateur
     */
    protected Vector2D getHome() {
        return home;
    }

    /**
     * Moves according to a <i>random walk</i>, used while not hunting in a
     * {@code MazeSimulation}.
     * 
     */

    @Override
    public final Direction move(Direction[] choices) {
        // Méthode générale de déplacement des prédateurs, comme des
        // souris.
        ArrayList<Direction> actualChoices = new ArrayList<Direction>();
        if (previousChoice == Direction.NONE) {
            previousChoice = choices[random.nextInt(choices.length)];
        }
        if (choices.length == 1) {
            previousChoice = choices[0];
        }

        if (choices.length == 2) {
            if (!(previousChoice.isOpposite(choices[0]))) {
                previousChoice = choices[0];
            } else {
                previousChoice = choices[1];
            }
        }

        if (choices.length >= 3) {
            for (int i = 0; i < choices.length; ++i) {
                if (!(previousChoice.isOpposite(choices[i]))) {
                    actualChoices.add(choices[i]);
                }
            }
            previousChoice = actualChoices.get(random.nextInt(actualChoices
                    .size()));
        }

        return previousChoice;
    }

    /**
     * Retrieves the next direction of the animal, by selecting one choice among
     * the ones available from its position.
     * <p>
     * In this variation, the animal knows the world entirely. It can therefore
     * use the position of other animals in the daedalus to hunt more
     * effectively.
     * 
     * @param choices
     *            The choices left to the animal at its current position (see
     *            {@link ch.epfl.maze.physical.World#getChoices(Vector2D)
     *            World.getChoices(Vector2D)})
     * @param daedalus
     *            The world in which the animal moves
     * @return The next direction of the animal, chosen in {@code choices}
     */

    abstract public Direction move(Direction[] choices, Daedalus daedalus);

    abstract public Animal copy();

    /**
     * Méthode permettant de piocher une proie au hasard. Cette méthode est
     * utilisée dans les méthodes moves des fantômes.
     * 
     * @param preys
     * @return prey : Une proie à notre prédateur.
     */

    protected Prey choosePrey(List<Prey> preys) {
        Prey prey = preys.get(random.nextInt(preys.size()));
        return prey;
    }
}
