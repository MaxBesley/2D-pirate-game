/* Max Besley. 20 May 2022. */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Represents an in-game person (or living thing).
 */
public abstract class Person {
    /** The current image that should be drawn to represent the person in game. */
    public Image currentImage;
    /** Amount of damage the person does upon attacking. */
    public int damagePoints;
    /** Maximum health points the person can have and starts with. */
    public int maxHealthPoints;
    /** The person's health bar. */
    public HealthBar healthBar;
    /** Size of the person's health bar in the game. */
    public int healthBarSize;
    /** For counting down a particular state the person can be in. */
    public Timer stateTimer;
    /** For counting down attack cooldown state. */
    public Timer attackCooldownTimer;
    /** Stores the current position of the person. */
    public Position position;
    /** Stores the previous position of the person. */
    public Position oldPosition;
    /** Stores whether or not the person is in the attack cooldown state. */
    public boolean inCooldown;
    /** Stores whether or not the person is currently facing right. */
    public boolean isFacingRight;


    /**
     * Method for drawing a child of the `Parent` class to the screen
     */
    public void draw() {
        currentImage.draw(getX(), getY());
    }

    /**
     * Gets the x coordinate of a Person object.
     */
    public double getX() {
        return position.getX();
    }

    /**
     * Gets the y coordinate of a Person object.
     */
    public double getY() {
        return position.getY();
    }

    /**
     * Sets the x coordinate of a Person object to a new value.
     */
    public void setX(double x) {
        position.setX(x);
    }

    /**
     * Sets the y coordinate of a Person object to a new value.
     */
    public void setY(double y) {
        position.setY(y);
    }

    /**
     * Returns the current position of the Person object.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Determines whether a Person object is dead or alive.
     */
    public boolean isAlive() {
        return healthBar.getCurrHealthPoints() > 0;
    }

    /**
     * Determines whether a Person object can perform an attack.
     */
    public boolean canAttack() {
        return !inCooldown;
    }

    /**
     * Determines if a Person object has collided with the passed Block object.
     */
    public boolean hasCollided(Block block) {
        Rectangle hitbox = this.getHitbox();
        return hitbox.intersects(block.getHitbox());
    }

    /**
     * Returns the hitbox of a Person object.
     */
    public Rectangle getHitbox() {
        return currentImage.getBoundingBoxAt(new Point(getX(), getY()));
    }

    /**
     * Decreases the current health points of a Person object.
     */
    public void reduceHealth(int amount) {
        int currentHealth = healthBar.getCurrHealthPoints();
        healthBar.setCurrHealthPoints(currentHealth - amount);
    }

    /**
     * Determines whether a Person object's position is out of bounds.
     * Where "position" is defined as the top left corner of the image.
     */
    public boolean isOutOfBounds(Point topLeft, Point bottomRight) {
        double topLeftX = getX() - currentImage.getWidth()/2.0;
        double topLeftY = getY() - currentImage.getHeight()/2.0;
        return !(topLeft.x < topLeftX && topLeftX < bottomRight.x &&
                 topLeft.y < topLeftY && topLeftY < bottomRight.y);
    }

    /**
     * Updates the states of the person's Timer
     * objects (assuming they're activated).
     */
    public void updateTimers() {
        if (!stateTimer.isOff()) {
            stateTimer.update();
        }
        if (!attackCooldownTimer.isOff()) {
            attackCooldownTimer.update();
        }
    }

    /**
     * Inflicts `damagePoints` damage on the Person object.
     */
    public abstract void getHit(int damagePoints);
}
