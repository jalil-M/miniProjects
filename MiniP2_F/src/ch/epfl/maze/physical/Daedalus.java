package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;

/**
 * Daedalus in which predators hunt preys. Once a prey has been caught by a
 * predator, it will be removed from the daedalus.
 * 
 */

public final class Daedalus extends World {

    // tableau dynamique des proies présentes dans le dédale
    private ArrayList<Prey> presentPreys;
    // tableau dynamique des prédateurs présents dans le dédale
    private ArrayList<Predator> presentPredators;
    // tableau dynamique des proies absentes dans le dédale
    private ArrayList<Prey> terminatedPreys;
    // tableau dynamique des prédateurs absents dans le dédale
    private ArrayList<Predator> terminatedPredators;

    /**
     * Constructs a Daedalus with a labyrinth structure
     * 
     * @param labyrinth
     *            Structure of the labyrinth, an NxM array of tiles
     */

    public Daedalus(int[][] labyrinth) {
        super(labyrinth);
        presentPreys = new ArrayList<Prey>();
        presentPredators = new ArrayList<Predator>();
        terminatedPreys = new ArrayList<Prey>();
        terminatedPredators = new ArrayList<Predator>();
    }

    /**
     * Méthode vérifiant si l'ArrayList des proies présentes dans le dédale est
     * vide.
     * 
     * @return boolean : Vrai si la condition ci-dessus est vérifiée, et faux
     *         sinon.
     * 
     */

    @Override
    public boolean isSolved() {
        return presentPreys.isEmpty();
    }

    /**
     * Adds a predator to the daedalus.
     * 
     * @param p
     *            The predator to add
     */

    public void addPredator(Predator p) {
        presentPredators.add(p);
    }

    /**
     * Adds a prey to the daedalus.
     * 
     * @param p
     *            The prey to add
     */

    public void addPrey(Prey p) {
        presentPreys.add(p);
    }

    /**
     * Removes a predator from the daedalus.
     * 
     * @param p
     *            The predator to remove
     */

    public void removePredator(Predator p) {
        // chaque prédateur supprimée du dédale est mise dans
        // "terminatedPredators".
        presentPredators.remove(p);
        terminatedPredators.add(p);
    }

    /**
     * Removes a prey from the daedalus.
     * 
     * @param p
     *            The prey to remove
     */

    public void removePrey(Prey p) {
        // chaque proie supprimée du dédale est mise dans "terminatedPreys".
        presentPreys.remove(p);
        terminatedPreys.add(p);
    }

    /**
     * Méthode permettant de getter les animaux (proies et prédateurs) présents
     * dans le dédale.
     * 
     * @return List<Animal> animals: liste de tous les animaux du dédale
     */
    @Override
    public List<Animal> getAnimals() {
        ArrayList<Animal> animals = new ArrayList<Animal>();
        animals.addAll(getPredators());
        animals.addAll(getPreys());

        return animals;
    }

    /**
     * Returns a copy of the list of all current predators in the daedalus.
     * 
     * @return A list of all predators in the daedalus
     */

    public List<Predator> getPredators() {
        return new ArrayList<Predator>(presentPredators);
    }

    /**
     * Returns a copy of the list of all current preys in the daedalus.
     * 
     * @return A list of all preys in the daedalus
     */

    public List<Prey> getPreys() {
        return new ArrayList<Prey>(presentPreys);
    }

    /**
     * Determines if the daedalus contains a predator.
     * 
     * @param p
     *            The predator in question
     * @return <b>true</b> if the predator belongs to the world, <b>false</b>
     *         otherwise.
     */

    public boolean hasPredator(Predator p) {
        if (presentPredators.contains(p)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines if the daedalus contains a prey.
     * 
     * @param p
     *            The prey in question
     * @return <b>true</b> if the prey belongs to the world, <b>false</b>
     *         otherwise.
     */

    public boolean hasPrey(Prey p) {
        if (presentPreys.contains(p)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Création de boucles FOR , qui vont copier les éléments des
     * "presentPreys" et "terminatedPrey", "presentPredators" et
     * "terminatedPredators", cela afin de réinitialiser le dédale sans perdre
     * les éléments s'y trouvant : les proies et les prédateurs.
     * 
     */

    @Override
    public void reset() {
        // Création de copies pour les proies.

        ArrayList<Prey> copyPrey = new ArrayList<Prey>();

        for (Prey temp1 : presentPreys) {
            Prey p = (Prey) temp1.copy();
            copyPrey.add(p);
        }
        for (Prey temp1 : terminatedPreys) {
            Prey p = (Prey) temp1.copy();
            copyPrey.add(p);
        }

        terminatedPreys = new ArrayList<Prey>();
        presentPreys = copyPrey;

        // Création de copies pour les prÃ©dateurss.

        ArrayList<Predator> copyPred = new ArrayList<Predator>();
        for (Predator temp2 : presentPredators) {

            Predator p = (Predator) temp2.copy();
            copyPred.add(p);
        }
        for (Predator temp2 : terminatedPredators) {
            Predator p = (Predator) temp2.copy();
            copyPred.add(p);
        }

        terminatedPredators = new ArrayList<Predator>();
        presentPredators = copyPred;

    }
}
