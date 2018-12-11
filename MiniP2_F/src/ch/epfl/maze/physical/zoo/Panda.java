package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Panda A.I. that implements Trémeaux's Algorithm.
 * 
 */
public class Panda extends Animal {
    private Direction previousChoice;
    private Random random;

    /**
     * Constructs a panda with a starting position.
     * 
     * @param position
     *            Starting position of the panda in the labyrinth
     */

    public Panda(Vector2D position) {
        super(position);
        random=new Random();
    }

    /**
     * Moves according to <i>Trémeaux's Algorithm</i>: when the panda moves, it
     * will mark the ground at most two times (with two different colors). It
     * will prefer taking the least marked paths. Special cases have to be
     * handled, especially when the panda is at an intersection.
     */
    // ArrayList contenant les positions des cases marquées avec la première
    // couleur
    private ArrayList<Vector2D> color1 = new ArrayList<Vector2D>();
    // ArrayList contenant les positions des cases marquées avec la deuxième
    // couleur.
    private ArrayList<Vector2D> color2 = new ArrayList<Vector2D>();

    @Override
    public Direction move(Direction[] choices) {

        if (choices.length == 1) {
            previousChoice = choices[0];
            color2.add(this.getPosition());
            return previousChoice;
        }
        // Le panda trie ses choix dans trois listes différentes:
        // ArrayList contenant les directions qui mènent vers des cases non
        // marquées.
        ArrayList<Direction> choicesNonM = new ArrayList<Direction>();
        // ArrayList contenant les directions qui mènent vers des cases marquées
        // avec la première couleur.
        ArrayList<Direction> choicesColor1 = new ArrayList<Direction>();
        // ArrayList contenant les directions qui mènent vers des cases marquées
        // avec la deuxième couleur.
        ArrayList<Direction> choicesColor2 = new ArrayList<Direction>();
        for (int i = 0; i < choices.length; i++) {
            if (color1.contains(getPosition().addDirectionTo(choices[i]))) {
                choicesColor1.add(choices[i]);
            } else if (color2
                    .contains(getPosition().addDirectionTo(choices[i]))) {
                choicesColor2.add(choices[i]);
            } else {
                choicesNonM.add(choices[i]);
            }
        }
        /*
         * Si des choix menant à une case non marquée sont disponibles, le panda
         * en emprunte un au hasard.
         */
        if (choicesNonM.size() != 0) {
            previousChoice = choicesNonM
                    .get(random.nextInt(choicesNonM.size()));

            // Si la case de la position du panda est marquée avec la première
            // couleur , le panda la marque avec la deuxième couleur.
            if (color1.contains(this.getPosition())
                    && choicesColor2.size() == choices.length - 1) {
                color2.add(this.getPosition());
                color1.remove(this.getPosition());
            }
            // Si la case de la position du panda n'est pas marquée , le
            // panda la marque avec la première couleur
            else {
                color1.add(this.getPosition());
            }
        }
        /*
         * Si aucune case non marquée ne l'entoure, le panda emprunte un chemin
         * qui le mène vers une case marquée par la première couleur.
         */
        else if (choicesColor1.size() != 0) {
            // Si le panda se trouve à une intersection et que tous les chemins
            // proposés sont marqués de sa première couleur, il rebrousse chemin
            // et revient directement en arrière.
            if (choices.length >= 3 && choices.length == choicesColor1.size()) {
                previousChoice = previousChoice.reverse();
            } else {
                // Boucle qui retire des chemins menant vers une case marquée
                // par la première couleur ceux qui font revenir directement en
                // arrière
                for (int j = 0; j < choicesColor1.size(); ++j) {
                    if (previousChoice.isOpposite(choicesColor1.get(j))) {
                        choicesColor1.remove(j);
                    }
                }

                if (choicesColor1.size() == 0) {
                    if (!(choicesColor2.size() == 1)) {
                        // Boucle qui retire des chemins menant vers une case
                        // marquée
                        // par la deuxième couleur ceux qui font revenir
                        // directement en
                        // arrière
                        for (int j = 0; j < choicesColor2.size(); ++j) {
                            if (previousChoice.isOpposite(choicesColor2.get(j))) {
                                choicesColor2.remove(j);
                            }
                        }
                    }
                    previousChoice = choicesColor2.get(random
                            .nextInt(choicesColor2.size()));
                } else {
                    previousChoice = choicesColor1.get(random
                            .nextInt(choicesColor1.size()));

                    if (choicesColor1.size() == 1) {
                        color1.remove(this.getPosition());
                        color2.add(this.getPosition());
                    }
                }
            }
        } else {
            if (!(choicesColor2.size() == 1)) {
                for (int j = 0; j < choicesColor2.size(); ++j) {
                    if (previousChoice.isOpposite(choicesColor2.get(j))) {
                        choicesColor2.remove(j);
                    }
                }
            }
            previousChoice = choicesColor2.get(random.nextInt(choicesColor2
                    .size()));
        }

        /*
         * Si le panda se trouve à une intersection marquée par sa première
         * couleur et que plusieurs chemins sont disponibles le panda marque une
         * deuxième fois l'intersection que lorsqu'il empruntera le dernier
         * chemin marqué par sa première couleur à partir de l'intersection.
         */
        if (choices.length >= 3) {
            if (color1.contains(this.getPosition())) {
                if (choicesNonM.size() == 0 && choicesColor1.size() == 1) {

                    color2.add(this.getPosition());
                    color1.remove(this.getPosition());
                }
            }
        }

        return previousChoice;
    }

    @Override
    public Animal copy() {
        return new Panda(this.getPosition());
    }
}