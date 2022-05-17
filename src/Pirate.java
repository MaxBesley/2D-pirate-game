/* Max Besley. May 2022. */

import bagel.Image;
import bagel.util.Rectangle;
import java.util.Random;

/**
 * Represents a pirate in the ShadowPirate game.
 */
public class Pirate extends Person {
    private final Image PIRATE_LEFT = new Image("res/pirate/pirateLeft.png");
    private final Image PIRATE_RIGHT = new Image("res/pirate/pirateRight.png");
    private final Image PIRATE_INVINC_LEFT = new Image("res/pirate/pirateHitLeft.png");
    private final Image PIRATE_INVINC_RIGHT = new Image("res/pirate/pirateHitRight.png");
    private final Image PIRATE_PROJECTILE_IMAGE = new Image("res/pirate/pirateProjectile.png");
    private static final double PIRATE_PROJECTILE_SPEED = 0.4;
    private static final int HEALTH_BAR_Y_OFFSET = 6;
    Rectangle attackRange;
    private int attackRangeSize = 100;
    private double attackCooldownLen = 3000.0;
    private double invincStateLen = 1500.0;
    private static final double RAND_LOWER = 0.2;
    private static final double RAND_UPPER = 0.7;
    private final double SPEED;
    private Direction direction;
    private boolean toBeDeleted;
    private boolean isInvincible;

    /* These attributes below ensure that a pirate doesn't
       get stuck on a block.
       Once a collision has occurred, collision detection will
       be halted for `COLLISION_PAUSE_LEN` milliseconds. */
    private boolean justCollidedWithBlock;
    private Timer collisionPauseTimer;
    private static final int COLLISION_PAUSE_LEN = 100;


    /**
     * Creates a pirate at an initial (x, y) position.
     */
    public Pirate(int xCoord, int yCoord) {
        currentImage = PIRATE_RIGHT;
        damagePoints = 10;
        maxHealthPoints = 45;
        healthBar = new HealthBar(maxHealthPoints);
        healthBarSize = 15;
        stateTimer = new Timer(invincStateLen);
        attackCooldownTimer = new Timer(attackCooldownLen);
        collisionPauseTimer = new Timer(COLLISION_PAUSE_LEN);
        position = new Position(xCoord + PIRATE_LEFT.getWidth()/2, yCoord + PIRATE_LEFT.getHeight()/2);
        oldPosition = null;
        // Assign a random speed value using the formula below
        Random r = new Random();
        SPEED = RAND_LOWER + (RAND_UPPER - RAND_LOWER) * r.nextDouble();
        // Assign the Pirate object a random direction
        direction = new Direction();
        // Initialise the state of the Pirate object
        toBeDeleted = false;
        inCooldown = false;
        isInvincible = false;
        isFacingRight = true;
        justCollidedWithBlock = false;
    }

    /**
     * Updates the internal state of a Pirate object.
     * @param level This is the level the pirate is contained in.
     */
    public void update(Level level) {
        if (!isAlive()) {
            toBeDeleted = true;
            return;
        }

        move();

        checkCollisions(level);

        updateTimers();

        checkStateChange();

        attack(level);

        updateCurrentImage();
        draw();
        healthBar.draw(getX() - currentImage.getWidth()/2,
                       getY() - currentImage.getHeight()/2 - HEALTH_BAR_Y_OFFSET, healthBarSize);
    }

    /*
     * Returns whether the pirate needs to be deleted from the game.
     */
    public boolean isToBeDeleted() {
        return toBeDeleted;
    }

    /*
     * Moves the pirate forward based on its current direction.
     */
    public void move() {
        // Move the pirate (note: cannot move diagonally)
        if (direction.isMovingLeft()) {
            setX(getX() - SPEED);
            isFacingRight = false;
        } else if (direction.isMovingRight()) {
            setX(getX() + SPEED);
            isFacingRight = true;
        }  else if (direction.isMovingUp()) {
            setY(getY() - SPEED);
        } else if (direction.isMovingDown()) {
            setY(getY() + SPEED);
        }
    }

    private void updateCurrentImage() {
        // Determine what the current image should be
        if (isInvincible) {
            if (isFacingRight) {
                currentImage = PIRATE_INVINC_RIGHT;
            } else {
                currentImage = PIRATE_INVINC_LEFT;
            }
        } else {
            if (isFacingRight) {
                currentImage = PIRATE_RIGHT;
            } else {
                currentImage = PIRATE_LEFT;
            }
        }
    }

    /* Determines if a pirate has collided with any of
       the blocks or a level edge, and if so, reverses
       the pirate's direction. */
    private void checkCollisions(Level level) {
        checkLevelEdgeCollisions(level);
        checkBlockCollisions(level);
    }

    private void checkLevelEdgeCollisions(Level level) {
        if (isOutOfBounds(level.boundaryTopLeft, level.boundaryBottomRight)) {
            direction.reverse();
        }
    }

    private void checkBlockCollisions(Level level) {
        // Check if the pirate collided with a block not so long ago
        if (justCollidedWithBlock) {
            // Check if the timer is over yet
            if (!collisionPauseTimer.isOn()) {
                justCollidedWithBlock = false;
            } else {
                // The timer is on so update its state
                collisionPauseTimer.update();
            }
            // Skip over collision detection
            return;
        }
        // Otherwise, check for a block collision
        for (Block b : level.allBlocks) {
            if (hasCollided(b)) {
                // Start moving the other way
                direction.reverse();
                // Active the timer
                justCollidedWithBlock = true;
                collisionPauseTimer.turnOn();
                // Bye!
                break;
            }
        }
    }

    /*
     * Causes a pirate to lose health and
     * then enter the invincible state.
     */
    @Override
    public void getHit(int damagePoints) {
        if (!isInvincible) {
            reduceHealth(damagePoints);
            isInvincible = true;
            stateTimer.turnOn();
        }
    }

    private void attack(Level level) {
        // Exit immediately if the pirate is in the cooldown state
        if (!inCooldown) {
            // Check if the sailor is within the pirate's attack range
            attackRange = new Rectangle(getX() - attackRangeSize/2.0, (getY() - attackRangeSize/2.0),
                                        attackRangeSize, attackRangeSize);
            if (attackRange.intersects(level.sailor.getHitbox())) {
                // BANG!!! Shoot at the sailor
                level.allProjectiles.add(new Projectile(position, level.sailor.getPosition(), damagePoints,
                                                        PIRATE_PROJECTILE_SPEED, PIRATE_PROJECTILE_IMAGE));
                // Now enter the cooldown state
                inCooldown = true;
                attackCooldownTimer.turnOn();
            }
        }
    }

    private void checkStateChange() {
        // Check if the pirate's state should change.
        // Recall that there are three different states.
        if (isInvincible && !stateTimer.isOn()) {
            isInvincible = false;
        }
        if (inCooldown && !attackCooldownTimer.isOn()) {
            inCooldown = false;
        }
    }


}
