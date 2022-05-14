/* Max Besley. May 2022. */

import java.util.Random;

/*
 *
 */
public class Direction {
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private String currentDir;

    // Constructor
    public Direction() {
        // Generate a random direction (as a string)
        String[] allDirs = {LEFT, RIGHT, UP, DOWN};
        Random r = new Random();
        String randomDir = allDirs[r.nextInt(allDirs.length)];
        // Initialise to this random direction
        setCurrentDir(randomDir);
    }

    /*
     * Method that gets the `currentDir` string
     */
    public String getCurrentDir() {
        return currentDir;
    }

    private void setCurrentDir(String currentDir) {
        this.currentDir = currentDir;
    }

    public boolean isMovingLeft() {
        return currentDir.equals(LEFT);
    }

    public boolean isMovingRight() {
        return currentDir.equals(RIGHT);
    }

    public boolean isMovingUp() {
        return currentDir.equals(UP);
    }

    public boolean isMovingDown() {
        return currentDir.equals(DOWN);
    }

    /*
     *
     */
    public void reverseDirection() {
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
        }
    }
}
