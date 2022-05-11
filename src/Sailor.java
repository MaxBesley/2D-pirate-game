/* Max Besley. May 2022. */

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;

/**
 * Represents the sailor in the ShadowPirate game (i.e. the player).
 * Note that this class is mutable.
 */
public class Sailor extends Person {
    // The sailor's internal instance variables
    private final Image SAILOR_LEFT;
    private final Image SAILOR_RIGHT;
    private static final int SPEED = 1;


    // Constructor
    public Sailor(int xCoord, int yCoord) {
        SAILOR_LEFT = new Image("res/sailor/sailorLeft.png");
        SAILOR_RIGHT = new Image("res/sailor/sailorRight.png");
        currentImage = SAILOR_RIGHT;
        damagePoints = 15;
        maxHealthPoints = 100;
        healthBar = new HealthBar(maxHealthPoints);
        // The position is at the centre of the image (not the top left)
        position = new Position((int) (xCoord + SAILOR_LEFT.getWidth()/2), (int) (yCoord + SAILOR_LEFT.getHeight()/2));
        oldPosition = null;
    }

    @Override
    public void update(Input input, Level level) {
        draw();
        drawHealthBar();

        move(input);

        checkOutOfBounds(level);
        checkBlockCollisions(level);
    }

    // For getting the x and y coordinates of the sailor object
    public int getX() {
        return position.getX();
    }
    public int getY() {
        return position.getY();
    }

    @Override
    public void move(Input input) {
        // For moving the sailor left, right, up and down
        if (input.isDown(Keys.LEFT)) {
            // For collision detection, store a backup copy of the current position and current hitbox
            setOldPosition();
            // Update the current position accordingly (i.e. move the sailor)
            position.setX(getX() - SPEED);
            // Change the sailor's current image so that he is facing the right way
            currentImage = SAILOR_LEFT;
        }
        if (input.isDown(Keys.RIGHT)) {
            // Similar logic applies for moving right, up and down
            setOldPosition();
            position.setX(getX() + SPEED);
            currentImage = SAILOR_RIGHT;
        }
        if (input.isDown(Keys.UP)) {
            setOldPosition();
            position.setY(getY() - SPEED);
        }
        if (input.isDown(Keys.DOWN)) {
            setOldPosition();
            position.setY(getY() + SPEED);
        }

    }

    /*
     * Method that sets `oldPosition` to be identical to `position`
     */
    private void setOldPosition() {
        // The old position should be a copy of the current position
        // So use the copy constructor in the `Position` class
        oldPosition = new Position(position);
    }

    /**
     * Moves the Sailor object back to its previous position
     */
    private void moveBack(){
        position = oldPosition;
    }

    /*
     * Draws the health percentage the Sailor object currently has to the screen
     */
    private void drawHealthBar() {
        healthBar.drawToScreen();
    }

    /*
     * Method that gets the hitbox of the Sailor object
     */
    public Rectangle getHitbox() {
        return currentImage.getBoundingBoxAt(new Point(getX(), getY()));
    }

    /*
     * Method that determines if a Sailor object has collided with the passed Block object
     */
    private boolean hasCollided(Block block) {
        Rectangle sailorHitbox = this.getHitbox();
        return sailorHitbox.intersects(block.getHitbox());
    }

    /*
     * Determines if a Sailor object has collided with any
     * of the Block objects, and if so, moves the Sailor
     * back to his previous position.
     */
    private void checkBlockCollisions(Level level) {
        for (Block b : level.allBlocks) {
            // Check if the sailor has collided with the block
            if (this.hasCollided(b)) {
                moveBack();
                break;
            }
        }
    }

    /*
     * Determines if a Sailor object is within the
     * level bounds, and if not, moves the Sailor
     * back to his previous position.
     */
    private void checkOutOfBounds(Level level) {
        if (isOutOfBounds(level.boundaryTopLeft, level.boundaryBottomRight)) {
            moveBack();
        }
    }
}

