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
    public int damagePoints;
    public int maxHealthPoints;
    public HealthBar healthBar;
    public int healthBarSize;
    public Timer stateTimer;
    public Timer attackCooldownTimer;
    public Position position;
    public Position oldPosition;
    public boolean inCooldown;
    public boolean isFacingRight;


    /*
     * Method for drawing a child of the `Parent` class to the screen
     */
    public void draw() {
        currentImage.draw(getX(), getY());
    }

    // For getting the x and y coordinates of the Person object
    public int getX() {
        return position.getX();
    }
    public int getY() {
        return position.getY();
    }

    // For setting the x and y coordinates of the Person object
    public void setX(int x) {
        position.setX(x);
    }
    public void setY(int y) {
        position.setY(y);
    }

    // Method for getting the health points the sailor has (but not as a percentage!)
    public int getHealthPoints() {
        return healthBar.getCurrHealthPoints();
    }

    /*
     * Determines whether a Person object is dead or alive
     */
    public boolean isAlive() {
        return healthBar.getCurrHealthPoints() > 0;
    }

    /*
     * Determines whether a Person object can perform an attack
     */
    public boolean canAttack() {
        return !inCooldown;
    }

    /*
     * Method that gets the hitbox of a Person object.
     */
    public Rectangle getHitbox() {
        return currentImage.getBoundingBoxAt(new Point(getX(), getY()));
    }

    // Method for decreasing the current health of the sailor
    public void reduceHealth(int amount) {
        int currentHealth = this.getHealthPoints();
        healthBar.setCurrHealthPoints(currentHealth - amount);
    }

    /*
     * Determines whether a Person object's position is out of bounds
     */
    public boolean isOutOfBounds(Point topLeft, Point bottomRight) {
        int personX = getX();
        int personY = getY();
        return !(topLeft.x < personX && personX < bottomRight.x  &&
                 topLeft.y < personY && personY < bottomRight.y);
    }

    /*
     * Method that updates the states of the two
     * Timer objects (assuming they're activated)
     */
    public void updateTimers() {
        if (stateTimer.isOn()) {
            stateTimer.update();
        }
        if (attackCooldownTimer.isOn()) {
            attackCooldownTimer.update();
        }
    }
}
