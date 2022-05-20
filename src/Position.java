/* Max Besley. 20 May 2022. */

/**
 * A mutable class that represents an (x, y) coordinate.
 */
public class Position {
    private double x;
    private double y;


    /*
     * Creates a position with the specified x, y coordinates.
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /*
     * Copy constructor.
     */
    public Position(Position aPosition) {
        if (aPosition == null) {
            System.err.println("Error: null reference passed to Position class's copy constructor");
            System.exit(1);
        }
        this.x = aPosition.x;
        this.y = aPosition.y;
    }

    /*
     * Returns the x coordinate of a position.
     */
    public double getX() {
        return x;
    }

    /*
     * Returns the y coordinate of a position.
     */
    public double getY() {
        return y;
    }

    /*
     * Sets the x coordinate of a position
     * to the value passed as a parameter.
     */
    public void setX(double x) {
        this.x = x;
    }

    /*
     * Sets the y coordinate of a position
     * to the value passed as a parameter.
     */
    public void setY(double y) {
        this.y = y;
    }
}
