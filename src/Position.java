/* Max Besley. May 2022. */

/**
 * A mutable class that represents an (x, y) coordinate/position.
 */
public class Position {
    private int x;
    private int y;


    // Constructor
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Copy constructor
    public Position(Position aPosition) {
        if (aPosition == null) {
            System.err.println("Error: null reference passed to Position class's copy constructor");
            System.exit(1);
        }
        this.x = aPosition.x;
        this.y = aPosition.y;
    }

    // Getters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
