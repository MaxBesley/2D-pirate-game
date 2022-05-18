/* Max Besley. May 2022. */

import java.util.Random;

/**
 * Represents a direction that can be either left, right up or down.
 * Diagonal directions are not supported/possible.
 */
public class Direction {
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private String currentDir;


    /**
     * Creates a random direction, where each of the four
     * possible directions have equal probability.
     */
    public Direction() {
        // Generate a random direction (as a string)
        String[] allDirs = {LEFT, RIGHT, UP, DOWN};
        Random r = new Random();
        String randomDir = allDirs[r.nextInt(allDirs.length)];
        // Initialise to this random direction
        setCurrentDir(randomDir);
    }

    /**
     * Returns the `currentDir` attribute (a string).
     */
    public String getCurrentDir() {
        return currentDir;
    }

    // Setter for the `currentDir` attribute.
    private void setCurrentDir(String currentDir) {
        this.currentDir = currentDir;
    }

    /**
     * Returns whether the direction is left.
     */
    public boolean isLeft() {
        return currentDir.equals(LEFT);
    }

    /**
     * Returns whether the direction is right.
     */
    public boolean isRight() {
        return currentDir.equals(RIGHT);
    }

    /**
     * Returns whether the direction is up.
     */
    public boolean isUp() {
        return currentDir.equals(UP);
    }

    /**
     * Returns whether the direction is down.
     */
    public boolean isDown() {
        return currentDir.equals(DOWN);
    }

    /**
     * Reverses to the opposite direction.
     */
    public void reverse() {
        switch (currentDir) {
            case LEFT:
                setCurrentDir(RIGHT);
                break;
            case RIGHT:
                setCurrentDir(LEFT);
                break;
            case UP:
                setCurrentDir(DOWN);
                break;
            case DOWN:
                setCurrentDir(UP);
                break;
            default:
                System.err.println("Error: direction must be one of left, right up or down.");
                System.exit(1);
        }
    }
}
