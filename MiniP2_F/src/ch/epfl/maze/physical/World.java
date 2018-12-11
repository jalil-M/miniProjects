package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * World that is represented by a labyrinth of tiles in which an {@code Animal}
 * can move.
 * 
 */

public abstract class World {
    private int[][] labyrinth;

    /* tiles constants */
    public static final int FREE = 0;
    public static final int WALL = 1;
    public static final int START = 2;
    public static final int EXIT = 3;
    public static final int NOTHING = -1;

    /**
     * Constructs a new world with a labyrinth. The labyrinth must be rectangle.
     * 
     * @param labyrinth
     *            Structure of the labyrinth, an NxM array of tiles
     */

    protected World(int[][] labyrinth) throws IllegalArgumentException {
        this.labyrinth = labyrinth;
        // boucle For permettant de vérifier les bords du labyrithe en termes
        // de constituants.

        for (int i = 0; i < labyrinth.length; ++i) {
            for (int j = 0; j < labyrinth[0].length; ++j) {
                if (labyrinth[i][j] != FREE && labyrinth[i][j] != WALL
                        && labyrinth[i][j] != START && labyrinth[i][j] != EXIT) {
                    labyrinth[i][j] = NOTHING;
                }
            }
        }
        // boucle For permettant de vérifier que le labyrinthe entrée est bien
        // rectangulaire.

        for (int i = 0; i < labyrinth.length; ++i) {
            if (labyrinth[0].length != labyrinth[i].length) {
                throw new IllegalArgumentException(
                        "Le labyrinthe entré n'est pas rectangulaire.");
            }
        }
    }

    /**
     * Determines whether the labyrinth has been solved by every animal.
     * 
     * @return <b>true</b> if no more moves can be made, <b>false</b> otherwise
     */

    abstract protected boolean isSolved();

    /**
     * Resets the world as when it was instantiated.
     */

    abstract protected void reset();

    /**
     * Returns a copy of the list of all current animals in the world.
     * 
     * @return A list of all animals in the world
     */

    abstract public List<Animal> getAnimals();

    /**
     * Checks in a safe way the tile number at position (x, y) in the labyrinth.
     * 
     * @param x
     *            Horizontal coordinate
     * @param y
     *            Vertical coordinate
     * @return The tile number at position (x, y), or the NONE tile if x or y is
     *         incorrect.
     */

    public final int getTile(int x, int y) {
        // Vérification des bords du labyrinthe.
        if (x >= 0 && x < labyrinth[0].length && y >= 0 && y < labyrinth.length) {
            return labyrinth[y][x];
        } else {
            return NOTHING;
        }
    }

    /**
     * Determines if coordinates are free to walk on.
     * 
     * @param x
     *            Horizontal coordinate
     * @param y
     *            Vertical coordinate
     * @return <b>true</b> if an animal can walk on tile, <b>false</b> otherwise
     */

    public final boolean isFree(int x, int y) {
        if (getTile(x, y) != WALL && getTile(x, y) != NOTHING) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Computes and returns the available choices for a position in the
     * labyrinth. The result will be typically used by {@code Animal} in
     * {@link ch.epfl.maze.physical.Animal#move(Direction[]) move(Direction[])}
     * 
     * @param position
     *            A position in the maze
     * @return An array of all available choices at a position
     */

    public final Direction[] getChoices(Vector2D position) {
        int posx = position.getX();
        int posy = position.getY();
        ArrayList<Direction> directions = new ArrayList<Direction>();
        if (posx >= 0 && posx <= labyrinth[0].length && posy >= 0
                && posy <= labyrinth.length) {
            if (isFree(posx, posy - 1)) {
                directions.add(Direction.UP);
            }
            if (isFree(posx + 1, posy)) {
                directions.add(Direction.RIGHT);
            }
            if (isFree(posx, posy + 1)) {
                directions.add(Direction.DOWN);
            }
            if (isFree(posx - 1, posy)) {
                directions.add(Direction.LEFT);
            }
            if (directions.size() == 0) {
                directions.add(Direction.NONE);
            }
        }
        Direction[] choices = new Direction[directions.size()];
        choices = directions.toArray(choices);
        return choices;
    }

    /**
     * Returns horizontal length of labyrinth.
     * 
     * @return The horizontal length of the labyrinth
     */

    public final int getWidth() {
        return labyrinth[0].length;
    }

    /**
     * Returns vertical length of labyrinth.
     * 
     * @return The vertical length of the labyrinth
     */

    public final int getHeight() {
        return labyrinth.length;
    }

    /**
     * Returns the entrance of the labyrinth at which animals should begin when
     * added.
     * 
     * @return Start position of the labyrinth, null if none.
     */

    public final Vector2D getStart() {
        int coordX = 0;
        int coordY = 0;
        for (int y = 0; y < labyrinth.length; ++y) {
            for (int x = 0; x < labyrinth[0].length; ++x) {
                if (labyrinth[y][x] == START) {
                    coordX = x;
                    coordY = y;
                }
            }
        }
        return new Vector2D(coordX, coordY);
    }

    /**
     * Returns the exit of the labyrinth at which animals should be removed.
     * 
     * @return Exit position of the labyrinth, null if none.
     */

    public final Vector2D getExit() {
        int coordX = 0;
        int coordY = 0;
        for (int y = 0; y < labyrinth.length; ++y) {
            for (int x = 0; x < labyrinth[0].length; ++x) {
                if (labyrinth[y][x] == EXIT) {
                    coordX = x;
                    coordY = y;
                }
            }
        }
        return new Vector2D(coordX, coordY);

    }
}
