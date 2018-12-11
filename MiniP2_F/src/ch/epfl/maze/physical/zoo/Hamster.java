package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Hamster A.I. that remembers the previous choice it has made and the dead ends
 * it has already met.
 * 
 */

public class Hamster extends Animal {
    private Random random;
    private Direction previousChoice;

    /**
     * Constructs a hamster with a starting position.
     * 
     * @param position
     *            Starting position of the hamster in the labyrinth
     */

    public Hamster(Vector2D position) {
        super(position);
        random = new Random();
        previousChoice = Direction.NONE;
    }

    // ArrayList représentant les impasses
    private ArrayList<Vector2D> deadEnds = new ArrayList<Vector2D>();

    /**
     * Moves without retracing directly its steps and by avoiding the dead-ends
     * it learns during its journey.
     */
    @Override
    public Direction move(Direction[] choices) {
        ArrayList<Direction> actualChoices = new ArrayList<Direction>();
        ArrayList<Direction> finalChoices = new ArrayList<Direction>();
        /*
         * A chaque décision l'hamster retire de ses choix les directions qui le
         * mènent à une impasse: Boucle qui remplit l'arrayList actualChoices
         * avec les choix disponibles qui ne le mènent pas à une impasse
         */
        for (int i = 0; i < choices.length; ++i) {
            if (!(deadEnds.contains(getPosition().addDirectionTo(choices[i])))) {
                actualChoices.add(choices[i]);
            }
        }
        // A son arrivée, l'hamster prend une direction au hasard
        if (previousChoice == Direction.NONE) {
            previousChoice = choices[random.nextInt(choices.length)];
        }

        /*
         * 1er cas: impasse --> l'hamster se souvient de sa position en
         * l'ajoutant dans l'arraylist deadEnds et emprunte la seule direction
         * qui lui est proposée
         */
        if (actualChoices.size() == 1) {
            previousChoice = actualChoices.get(0);
            deadEnds.add(this.getPosition());
        }
        /*
         * 2eme cas: couloir (=2) --> L'hamster choisit la direction qui ne la
         * fait pas revenir en arrière. 3eme cas: intersection(>=3)--> L'hamster
         * choisit au hasard la direction qui ne le fait pas revenir en arrière
         */
        else if (actualChoices.size() >= 2) {
            for (int i = 0; i < actualChoices.size(); ++i) {
                if (!(previousChoice.isOpposite(actualChoices.get(i)))) {
                    finalChoices.add(actualChoices.get(i));
                }
            }
            previousChoice = finalChoices.get(random.nextInt(finalChoices
                    .size()));
        }
        /*
         * Si l'hamster arrive à un endroit où toutes les directions mènent à
         * une impasse: il s'arrête proprement à sa place.
         */
        else if (choices.length == deadEnds.size()) {
            previousChoice = Direction.NONE;
        }

        return previousChoice;
    }

    @Override
    public Animal copy() {
        return new Hamster(this.getPosition());
    }
}
