package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Mouse A.I. that remembers only the previous choice it has made.
 * 
 */

public class Mouse extends Animal {
    private Direction previousChoice;
    private Random random;

    /**
     * Constructs a mouse with a starting position.
     * 
     * @param position
     *            Starting position of the mouse in the labyrinth
     */

    public Mouse(Vector2D position) {
        super(position);
        random = new Random();
    }

    /**
     * Moves according to an improved version of a <i>random walk</i> : the
     * mouse does not directly retrace its steps.
     */

    @Override
    public Direction move(Direction[] choices) {
        ArrayList<Direction> actualChoices = new ArrayList<Direction>();
        // A son arrivée, la souris prend une direction au hasard     
        if (previousChoice == Direction.NONE) {
            previousChoice = choices[random.nextInt(choices.length)];
        }
        /*
         * 1er cas: impasse --> La souris empreinte la seule direction qui lui
         * est proposée.
         */
        if (choices.length == 1) {
            previousChoice = choices[0];
        }
        /*
         * 2eme cas: couloir --> La souris choisit la direction qui ne la fait
         * pas revenir en arrière.
         */
        if (choices.length == 2) {
            if (!(previousChoice.isOpposite(choices[0]))) {
                previousChoice = choices[0];
            } else {
                previousChoice = choices[1];
            }
        }
        /*
         * 3eme cas: intersection --> La souris prend une direction au hasard
         * qui ne la fait pas revenir en arrière
         */
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

    @Override
    public Animal copy() {
        return new Mouse(this.getPosition());
    }
}
