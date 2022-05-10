/* Max Besley. May 2022. */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Represents a block in the ShadowPirate game.
 * Note that this class is immutable.
 */
public class Block {
    // Instance variables
    private final Image BLOCK_IMAGE = new Image("res/block.png");;
    private final Point blockPos;


    // Constructor
    public Block(int xCoord, int yCoord) {
        blockPos = new Point(xCoord, yCoord);
    }

    // Method for drawing a block object to the screen
    private void drawBlock() {
        BLOCK_IMAGE.draw(blockPos.x, blockPos.y);
    }

    //
    public void update() {
        this.drawBlock();
    }

    /*
     * Method that gets the hitbox of a block
     */
    public Rectangle getHitbox(){
        return BLOCK_IMAGE.getBoundingBoxAt(blockPos);
    }
}
