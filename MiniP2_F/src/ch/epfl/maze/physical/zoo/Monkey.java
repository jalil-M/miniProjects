package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Monkey A.I. that puts its hand on the left wall and follows it.
 * 
 */

public class Monkey extends Animal {
    private Direction previousChoice;

    /**
     * Constructs a monkey with a starting position.
     * 
     * @param position
     *            Starting position of the monkey in the labyrinth
     */

    public Monkey(Vector2D position) {
        super(position);
        previousChoice=Direction.UP;
    }

    /**
     * Moves according to the relative left wall that the monkey has to follow.
     */
    @Override
    public Direction move(Direction[] choices) {
        // Conversion des directions relatives au labyrinthe en choix relatifs à
        // la direction dans laquelle le singe fait face
        Direction[] relativeChoices = previousChoice
                .relativeDirections(choices);

        ArrayList<Direction> monkChoices = new ArrayList<Direction>();
        // Boucle qui remplit l'arraylist monkChoices avec les choix relatifs à
        // la direction dans laquelle le singe fait face
        for (int i = 0; i < choices.length; ++i) {
            monkChoices.add(relativeChoices[i]);
        }
        // Si un passage à gauche est disponible, le singe emprunte cette
        // direction en priorité
        if (monkChoices.contains(Direction.LEFT)) {
            previousChoice = previousChoice.unRelativeDirection(Direction.LEFT);
        }
        // Si le passage à gauche n'est pas disponible, le singe tentera d'aller
        // tout droit en priorité
        else if (monkChoices.contains(Direction.UP)) {
            previousChoice = previousChoice.unRelativeDirection(Direction.UP);
        }
        // Si le singe ne peut pas aller tout droit, le singe tentera de se
        // diriger à droite
        else if (monkChoices.contains(Direction.RIGHT)) {
            previousChoice = previousChoice
                    .unRelativeDirection(Direction.RIGHT);
        }
        // Si le singe ne peut pas tourner à droite, le singe fait un demi-tour
        else {
            previousChoice = previousChoice.reverse();
        }

        return previousChoice;
    }

    @Override
    public Animal copy() {
        return new Monkey(this.getPosition());
    }
}