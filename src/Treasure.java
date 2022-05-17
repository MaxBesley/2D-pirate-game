/* Max Besley. May 2022. */

import bagel.Image;

/**
 *
 */
public class Treasure {
    private Image TREASURE_IMAGE = new Image("res/treasure.png");
    private final int x;
    private final int y;


    /**
     * Creates the treasure at the
     * specified x, y coordinate.
     */
    public Treasure(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
