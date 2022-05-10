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
    private static final int SPEED = 5;


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
    public void update(Input input, ArrayList<Block> allBlocks) {
        draw();
        drawHealthBar();

        move(input);
        if (hasCollided(allBlocks)) {
            moveBack();
        }
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

    private void setOldPosition() {
        // `oldPosition` is a copy of the current position
        // Use the copy constructor in the `Position` class
        oldPosition = new Position(position);
    }

    /**
     * Moves the sailor back to its previous position
     */
    private void moveBack(){
        position = oldPosition;
    }

    /*
     * Draws the health percentage the sailor currently has to the screen
     */
    private void drawHealthBar() {
        healthBar.drawToScreen();
    }

    /*
     * Method that gets the hitbox of the sailor
     */
    public Rectangle getHitbox() {
        return currentImage.getBoundingBoxAt(new Point(getX(), getY()));
    }

    /*
     * Method that determines if the sailor has collided with the passed Block object
     */
    public boolean doesCollide(Block block) {
        Rectangle sailorHitbox = this.getHitbox();
        return sailorHitbox.intersects(block.getHitbox());
    }

    /*
     *
     */
    private boolean hasCollided(ArrayList<Block> allBlocks) {
        for (Block b : allBlocks) {
            if (this.doesCollide(b)) {
                return true;
            }
        }

        return false;
    }
}

