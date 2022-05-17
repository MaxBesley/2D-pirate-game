/* Max Besley. May 2022. */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Represents an in-game person (or living thing).
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
    public double getX() {
        return position.getX();
    }
    public double getY() {
        return position.getY();
    }

    // For setting the x and y coordinates of the Person object
    public void setX(double x) {
        position.setX(x);
    }
    public void setY(double y) {
        position.setY(y);
    }

    /*
     * Returns the current position of the Person object.
     */
    public Position getPosition() {
        return position;
    }

    // Method for getting the health points the sailor has (but not as a percentage!)
    public int getHealthPoints() {
        return healthBar.getCurrHealthPoints();
    }

    /*
     * Determines whether a Person object is dead or alive.
     */
    public boolean isAlive() {
        return healthBar.getCurrHealthPoints() > 0;
    }

    /*
     * Determines whether a Person object can perform an attack.
     */
    public boolean canAttack() {
        return !inCooldown;
    }

    /*
     * Method that determines if a Person object has collided with the passed Block object.
     */
    public boolean hasCollided(Block block) {
        Rectangle hitbox = this.getHitbox();
        return hitbox.intersects(block.getHitbox());
    }

    /*
     * Returns the hitbox of a Person object.
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
     * Determines whether a Person object's position is out of bounds.
     */
    public boolean isOutOfBounds(Point topLeft, Point bottomRight) {
        double personX = getX();
        double personY = getY();
        return !(topLeft.x < personX && personX < bottomRight.x  &&
                 topLeft.y < personY && personY < bottomRight.y);
    }

    /*
     * Updates the states of the person's Timer
     * objects (assuming they're activated).
     */
    public void updateTimers() {
        if (stateTimer.isOn()) {
            stateTimer.update();
        }
        if (attackCooldownTimer.isOn()) {
            attackCooldownTimer.update();
        }
    }

    /*
     * Inflicts `damagePoints` damage on the Person object.
     */
    public abstract void getHit(int damagePoints);
}
