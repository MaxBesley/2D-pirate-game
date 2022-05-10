/* Max Besley. May 2022. */

import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;

/**
 *
 */
public abstract class Person {
    public Image currentImage;
    public Position position;
    public Position oldPosition;
    public int maxHealthPoints;
    public HealthBar healthBar;
    public int damagePoints;

    public abstract void update(Input input, ArrayList<Block> allBlocks);

    public abstract void move(Input input);

    /*
     * Method for drawing a child of the `Parent` class to the screen
     */
    public void draw() {
        currentImage.draw(position.getX(), position.getY());
    }

    /*
     *
     */
    public HealthBar getHealthBar() {
        return healthBar;
    }

    /*
     * Determines whether a Person object is dead or alive
     */
    public boolean isAlive() {
        return healthBar.getCurrHealthPoints() > 0;
    }

    /*
     * Determines whether a child of the `Parent` classes' position is out of bounds
     */
    public boolean isOutOfBounds(Point TopLeft, Point BottomRight) {
        return false;
    }
}
