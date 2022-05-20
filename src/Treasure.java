/* Max Besley. 20 May 2022. */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Represents the treasure. The goal of level1.
 */
public class Treasure {
    private Image TREASURE_IMAGE = new Image("res/treasure.png");
    private final int x;
    private final int y;


    /**
     * Creates the treasure with its top left
     * corner at the specified x, y coordinate.
     */
    public Treasure(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the treasure to the screen at the correct location.
     */
    public void draw() {
        TREASURE_IMAGE.drawFromTopLeft(x, y);
    }

    /**
     * Returns the treasure's hitbox.
     */
    public Rectangle getHitbox() {
        return TREASURE_IMAGE.getBoundingBoxAt(new Point(x + TREASURE_IMAGE.getWidth()/2,y + TREASURE_IMAGE.getHeight()/2));
    }
}
