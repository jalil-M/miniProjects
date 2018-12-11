package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;

/**
 * Maze in which an animal starts from a starting point and must find the exit.
 * Every animal added will have its position set to the starting point. The
 * animal is removed from the maze when it finds the exit.
 * 
 */

public final class Maze extends World {
    // tableau dynamique des animaux présents dans le labyrinthe.
    private ArrayList<Animal> presentAnimals;
    // tableau dynamique des animaux sortis du labyrinthe.
    private ArrayList<Animal> terminatedAnimals;

    /**
     * Constructs a Maze with a labyrinth structure.
     * 
     * @param labyrinth
     *            Structure of the labyrinth, an NxM array of tiles
     */

    public Maze(int[][] labyrinth) {
        super(labyrinth);
        terminatedAnimals = new ArrayList<Animal>();
        presentAnimals = new ArrayList<Animal>();
    }

    /**
     * Méthode vérifiant si l'ArrayList des animaux présents dans le
     * labyrinthe est vide.
     * 
     * @return boolean : Vrai si la condition ci-dessus est vérifiée, et faux
     *         sinon.
     * 
     */

    @Override
    public boolean isSolved() {
        return presentAnimals.isEmpty();
    }

    /**
     * Getter de l'ArrayList des animaux présents dans le labyrinthe.
     * 
     * @return List<Animal> : retourne l'ArrayList présenté ci-dessus.
     */

    @Override
    public List<Animal> getAnimals() {
        return new ArrayList<Animal>(presentAnimals);
    }

    /**
     * Determines if the maze contains an animal.
     * 
     * @param a
     *            The animal in question
     * @return <b>true</b> if the animal belongs to the world, <b>false</b>
     *         otherwise.
     */

    public boolean hasAnimal(Animal a) {
        if (presentAnimals.contains(a)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds an animal to the maze.
     * 
     * @param a
     *            The animal to add
     */

    public void addAnimal(Animal a) {
        // La méthode ajoute un animal au labyrinthe et le place à son
        // entrée
        presentAnimals.add(a);
        a.setPosition(this.getStart());
    }

    /**
     * Removes an animal from the maze.
     * 
     * @param a
     *            The animal to remove
     */

    public void removeAnimal(Animal a) {
        // Chaque élément supprimé de "presentAnimals" est mis dans
        // "terminatedAnimals".
        presentAnimals.remove(a);
        terminatedAnimals.add(a);
    }

    /**
     * Création de deux boucles FOR, qui vont copier les éléments des
     * "presentAnimals" et "terminatedAnimals", afin de réinitialiser le
     * labyrinthe sans perdre les éléments s'y trouvant.
     * 
     */

    @Override
    public void reset() {
        ArrayList<Animal> copy = new ArrayList<Animal>();
        for (Animal temp : presentAnimals) {
            Animal a = temp.copy();
            a.setPosition(getStart());
            copy.add(a);
        }
        for (Animal temp : terminatedAnimals) {
            Animal a = temp.copy();
            a.setPosition(getStart());
            copy.add(a);
        }

        terminatedAnimals = new ArrayList<Animal>();
        presentAnimals = copy;

    }

}