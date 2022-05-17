/* Max Besley. May 2022. */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Represents a block in the ShadowPirate game.
 * Note that this class is immutable.
 */
public class Block {
    public final Image BLOCK_IMAGE = new Image("res/block.png");
    public Image currentImage = BLOCK_IMAGE;
    public boolean toBeDeleted;
    private final int x;
    private final int y;


    /**
     * Creates a Block object centred at the specified x, y coordinate.
     */
    public Block(int x, int y) {
        this.x = (int) (x + BLOCK_IMAGE.getWidth()/2);
        this.y = (int) (y + BLOCK_IMAGE.getHeight()/2);
        toBeDeleted = false;
    }

    /**
     * Updates the internal state of a Block object.
     */
    public void update(Sailor sailor) {
        draw();
    }

    /**
     * Draws a Block object to the screen.
     */
    public void draw() {
        currentImage.draw(x, y);
    }

    /**
     * Gets the hitbox of a Block object.
     */
    public Rectangle getHitbox(){
        return BLOCK_IMAGE.getBoundingBoxAt(new Point(x, y));
    }

    /**
     * Returns whether a Block object should be deleted from the game.
     */
    public boolean isToBeDeleted() {
        return toBeDeleted;
    }
}
