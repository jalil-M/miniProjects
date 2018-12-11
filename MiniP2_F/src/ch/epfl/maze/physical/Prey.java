package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Prey that is killed by a predator when they meet each other in the labyrinth.
 * 
 */

abstract public class Prey extends Animal {

    private Direction previousChoice;
    private Random random;
    private final Vector2D home;

    /**
     * Constructs a prey with a specified position.
     * 
     * @param position
     *            Position of the prey in the labyrinth
     */

    protected Prey(Vector2D position) {
        super(position);
        random = new Random();
        setPreviousChoice(Direction.NONE);
        home = position;
    }

    /**
     * Getteur de la "maison" de la proie.
     * 
     * @return Vector2D : position de la "maison".
     */

    protected Vector2D getHome() {
        return home;
    }

    /**
     * Getteur de previousChoice, soit le choix précédent nécessaire à notre
     * méthode move commune aux proies.
     * 
     * @return Direction prévousChoice : choix précédent de notre proie.
     */

    public Direction getPreviousChoice() {
        return previousChoice;
    }

    /**
     * Setteur de previousChoice, soit la modification de notre choix
     * précédent dans la méthode move.
     * 
     */

    private void setPreviousChoice(Direction previousChoice) {
        this.previousChoice = previousChoice;
    }

    /**
     * Moves according to a <i>random walk</i>, used while not being hunted in a
     * {@code MazeSimulation}.
     * 
     */

    @Override
    public final Direction move(Direction[] choices) {
        // Utilisation de la méthode move de la sourie pour que la proie se
        // déplace de manière générale comme la souris.
        ArrayList<Direction> actualChoices = new ArrayList<Direction>();
        for (int i = 0; i < choices.length; ++i)
            if (getPreviousChoice() == Direction.NONE) {
                setPreviousChoice(choices[random.nextInt(choices.length)]);
            }
        if (choices.length == 1) {
            setPreviousChoice(choices[0]);
        }

        if (choices.length == 2) {
            if (!(getPreviousChoice().isOpposite(choices[0]))) {
                setPreviousChoice(choices[0]);
            } else {
                setPreviousChoice(choices[1]);
            }
        }

        if (choices.length >= 3) {
            for (int i = 0; i < choices.length; ++i) {
                if (!(getPreviousChoice().isOpposite(choices[i]))) {
                    actualChoices.add(choices[i]);
                }
            }
            setPreviousChoice(actualChoices.get(random.nextInt(actualChoices
                    .size())));
        }

        return getPreviousChoice();

    }

    /**
     * Retrieves the next direction of the animal, by selecting one choice among
     * the ones available from its position.
     * <p>
     * In this variation, the animal knows the world entirely. It can therefore
     * use the position of other animals in the daedalus to evade predators more
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
}
