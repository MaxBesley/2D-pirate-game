/* Max Besley. May 2022. */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.Random;


public class Pirate extends Person {
    private final Image PIRATE_LEFT;
    private final Image PIRATE_RIGHT;
    private final Image PIRATE_INVINC_LEFT;
    private final Image PIRATE_INVINC_RIGHT;
    private final Image PIRATE_PROJECTILE_IMAGE;
    private static final int HEALTH_BAR_Y_OFFSET = 6;
    Rectangle attackRange;
    private int attackRangeSize = 100;
    private double attackCooldownLen = 3000.0;
    private double invincStateLen = 1500.0;
    private static final double RAND_LOWER = 0.2;
    private static final double RAND_UPPER = 0.7;
    private final double SPEED;
    private Direction direction;
    private boolean isInvincible;
    private static final int FRAMES_PER_MOVE = 4;


    // Constructor
    public Pirate(int xCoord, int yCoord) {
        PIRATE_LEFT = new Image("res/pirate/pirateLeft.png");
        PIRATE_RIGHT = new Image("res/pirate/pirateRight.png");
        PIRATE_INVINC_LEFT = new Image("res/pirate/pirateHitLeft.png");
        PIRATE_INVINC_RIGHT = new Image("res/pirate/pirateHitLeft.png");
        PIRATE_PROJECTILE_IMAGE = new Image("res/pirate/pirateProjectile.png");
        currentImage = PIRATE_RIGHT;
        damagePoints = 10;
        maxHealthPoints = 45;
        healthBar = new HealthBar(maxHealthPoints);
        healthBarSize = 15;
        stateTimer = new Timer(invincStateLen);
        attackCooldownTimer = new Timer(attackCooldownLen);
        collisionTimer = new Timer(COLLISION_PAUSE_LEN);
        position = new Position((int) (xCoord + PIRATE_LEFT.getWidth()/2), (int) (yCoord + PIRATE_LEFT.getHeight()/2));
        oldPosition = null;
        // Assign a random speed value using the formula below
        Random r = new Random();
        SPEED = RAND_LOWER + (RAND_UPPER - RAND_LOWER) * r.nextDouble();
        // Assign the Pirate object a random direction
        direction = new Direction();
        // Initialise the state of the Pirate object
        inCooldown = false;
        isInvincible = false;
        isFacingRight = true;
        justCollided = false;
        System.out.println("SPEED = " + SPEED + " DIR = " + direction.getCurrentDir());
    }

    public void update(Level level) {

        move();

        checkBlockCollisions(level);

        updateTimers();

        checkStateChange();

        attack(level);

        updateCurrentImage();
        draw();
        healthBar.draw((int) (getX() - currentImage.getWidth()/2),
                       (int) (getY() - currentImage.getHeight()/2 - HEALTH_BAR_Y_OFFSET), healthBarSize);
    }

    /*
     * Moves the pirate forward based on its current direction.
     */
    public void move() {
        // Only move every `FRAMES_PER_MOVE` frames/updates
        if ((ShadowPirate.getTotalFramesRendered() % FRAMES_PER_MOVE) != 0) {
            return;
        }
        // Move the pirate
        if (direction.isMovingLeft()) {
            setX((int) Math.round(getX() - FRAMES_PER_MOVE * SPEED));
            isFacingRight = false;
        } else if (direction.isMovingRight()) {
            setX((int) Math.round(getX() + FRAMES_PER_MOVE * SPEED));
            isFacingRight = true;
        }  else if (direction.isMovingUp()) {
            setY((int) Math.round(getY() - FRAMES_PER_MOVE * SPEED));
        } else if (direction.isMovingDown()) {
            setY((int) Math.round(getY() + FRAMES_PER_MOVE * SPEED));
        }
    }

    private void updateCurrentImage() {
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

    /*
     * Determines if a Pirate object has collided with any
     * of the Block objects, and if so, reverses the
     * pirate's direction.
     */
    private void checkBlockCollisions(Level level) {
        if (justCollided) {
            // Check if the timer is over yet
            if (!collisionTimer.isOn()) {
                justCollided = false;
                collisionTimer.reset();
            }
            // Skip over collision detection
            return;
        }
        for (Block b : level.allBlocks) {
            if (this.hasCollided(b)) {
                // Start moving the other way
                direction.reverse();
                // The timer makes collision logic safer
                justCollided = true;
                collisionTimer.turnOn();
                break;
            }
        }
    }

    /*
     * Method that causes a pirate to lose health
     * and then enter the INVINCIBLE state.
     */
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
                inCooldown = true;
                attackCooldownTimer.turnOn();
                System.out.println("BANG!");
            }
        }
    }

    private void checkStateChange() {
        // Check if the pirate's state should change.
        // Recall that there are three different states.
        if (isInvincible && !stateTimer.isOn()) {
            isInvincible = false;
            stateTimer.reset();
        }
        if (inCooldown && !attackCooldownTimer.isOn()) {
            inCooldown = false;
            attackCooldownTimer.reset();
        }
    }


}
