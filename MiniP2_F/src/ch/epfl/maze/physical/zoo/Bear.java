package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Bear A.I. that implements the Pledge Algorithm.
 * 
 */

public class Bear extends Animal {
    private Direction previousChoice;
    private Direction bestChoice;

    /**
     * Constructs a bear with a starting position.
     * 
     * @param position
     *            Starting position of the bear in the labyrinth
     */

    public Bear(Vector2D position) {
        super(position);
        previousChoice = Direction.UP;
    }

    /**
     * Moves according to the <i>Pledge Algorithm</i> : the bear tries to move
     * towards a favorite direction until it hits a wall. In this case, it will
     * turn right, put its paw on the left wall, count the number of times it
     * turns right, and subtract to this the number of times it turns left. It
     * will repeat the procedure when the counter comes to zero, or until it
     * leaves the maze.
     */

    @Override
    public Direction move(Direction[] choices) {
        int counter = 1;
        ArrayList<Direction> bChoices = new ArrayList<Direction>();
        for (int i = 0; i < choices.length; ++i) {
            bChoices.add(choices[i]);
        }

        if (bChoices.contains(bestChoice)) {
            previousChoice = bestChoice;
        } else {

            // Conversion des directions relatives au labyrinthe en choix
            // relatifs à la direction dans laquelle l'ours fait face
            Direction[] relativeChoices = previousChoice
                    .relativeDirections(choices);
            ArrayList<Direction> bearChoices = new ArrayList<Direction>();
            // Boucle qui remplit l'arraylist monkChoices avec les choix
            // relatifs à la direction dans laquelle l'ours fait face
            for (int i = 0; i < choices.length; ++i) {
                bearChoices.add(relativeChoices[i]);
            }

            if (counter != 0) {
                // Si un passage à gauche est disponible, l'ours emprunte cette
                // direction en priorité
                if (bearChoices.contains(Direction.LEFT)) {
                    previousChoice = previousChoice
                            .unRelativeDirection(Direction.LEFT);
                    counter = counter - 1;
                }
                // Si le passage à gauche n'est pas disponible, l'ours tentera
                // d'aller
                // tout droit en priorité
                else if (bearChoices.contains(Direction.UP)) {
                    previousChoice = previousChoice
                            .unRelativeDirection(Direction.UP);
                }
                // Si l'ours ne peut pas aller tout droit, l'ours tentera de
                // se diriger à droite
                else if (bearChoices.contains(Direction.RIGHT)) {
                    previousChoice = previousChoice
                            .unRelativeDirection(Direction.RIGHT);
                    counter = counter + 1;

                }
                // Si l'ours ne peut pas tourner à droite, l'ours fait un
                // demi-tour
                else if (bearChoices.contains(Direction.DOWN)) {
                    previousChoice = previousChoice.reverse();
                    counter = counter + 2;

                }

            }
        }
        return previousChoice;
    }

    @Override
    public Animal copy() {
        return new Bear(this.getPosition());
    }
}