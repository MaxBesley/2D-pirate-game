/* Max Besley. May 2022. */

import bagel.Keys;
import bagel.Image;
import bagel.Input;
import bagel.util.Rectangle;

/**
 * Represents the sailor in the ShadowPirate game (i.e. the player).
 */
public class Sailor extends Person {
    // The sailor's internal instance variables
    private final Image SAILOR_LEFT = new Image("res/sailor/sailorLeft.png");
    private final Image SAILOR_RIGHT = new Image("res/sailor/sailorRight.png");
    private final Image SAILOR_HIT_LEFT = new Image("res/sailor/sailorHitLeft.png");
    private final Image SAILOR_HIT_RIGHT = new Image("res/sailor/sailorHitRight.png");
    private boolean isIdle;
    private boolean isAttacking;
    private static final int SPEED = 1;
    private static final double ATTACK_STATE_DURATION = 1000.0;
    private static final double ATTACK_COOLDOWN_DURATION = 2000.0;
    private static final int HEALTH_BAR_X = 10;
    private static final int HEALTH_BAR_Y = 25;


    /**
     * Creates a sailor at an initial (x, y) position.
     */
    public Sailor(int xCoord, int yCoord) {
        currentImage = SAILOR_RIGHT;
        damagePoints = 15;
        maxHealthPoints = 100;
        healthBar = new HealthBar(maxHealthPoints);
        healthBarSize = 30;
        stateTimer = new Timer(ATTACK_STATE_DURATION);
        attackCooldownTimer = new Timer(ATTACK_COOLDOWN_DURATION);
        // The position is at the centre of the image (not the top left)
        position = new Position(xCoord + SAILOR_LEFT.getWidth()/2.0, yCoord + SAILOR_LEFT.getHeight()/2.0);
        oldPosition = null;
        // Initialise the state of the Sailor object
        inCooldown = false;
        isFacingRight = true;
        isIdle = true;
        isAttacking = false;
    }

    /**
     * Method that updates the internal state of a Sailor object.
     * @param input This is the input from the game's user.
     * @param level This is the level the sailor is contained in.
     */
    public void update(Input input, Level level) {
        /* Note that the methods move(), checkCollisions(),
           attack() and updateTimers() might do nothing at all. */

        move(input);

        checkCollisions(level);

        attack(input, level);

        updateTimers();

        updateCurrentImage();
        draw();
        healthBar.draw(HEALTH_BAR_X, HEALTH_BAR_Y, healthBarSize);
    }

    /**
     * Moves the sailor forward based on the user's input.
     */
    public void move(Input input) {
        // For moving the sailor left, right, up and down
        if (input.isDown(Keys.LEFT)) {
            // For collision logic, store a backup copy of the current position
            setOldPosition();
            // Update the current position accordingly (i.e. move the sailor)
            setX(getX() - SPEED);
            // Update the sailor's current image (there are four choices)
            isFacingRight = false;
        } else if (input.isDown(Keys.RIGHT)) {
            // Similar logic applies for moving right, up and down
            setOldPosition();
            setX(getX() + SPEED);
            isFacingRight = true;
        } else if (input.isDown(Keys.UP)) {
            setOldPosition();
            setY(getY() - SPEED);
        } else if (input.isDown(Keys.DOWN)) {
            setOldPosition();
            setY(getY() + SPEED);
        }
    }

    private void setOldPosition() {
        // The old position should be a copy of the current position
        // So use the copy constructor in the `Position` class
        oldPosition = new Position(position);
    }

    /**
     * Moves the Sailor object back to its previous position.
     */
    private void moveBack(){
        position = oldPosition;
    }

    private void updateCurrentImage() {
        // Determine what the current image should be
        if (isIdle) {
            if (isFacingRight) {
                currentImage = SAILOR_RIGHT;
            } else {
                currentImage = SAILOR_LEFT;
            }
        } else if (isAttacking) {
            if (isFacingRight) {
                currentImage = SAILOR_HIT_RIGHT;
            } else {
                currentImage = SAILOR_HIT_LEFT;
            }
        } else {
            System.err.println("Error: sailor must be either idle OR attacking");
            System.exit(1);
        }
    }

    /* Determines if the sailor has collided with any of
       the blocks or a level edge, and if so, moves the
       sailor back to its previous position. */
    private void checkCollisions(Level level) {
        checkLevelEdgeCollisions(level);
        checkBlockCollisions(level);
    }

    private void checkLevelEdgeCollisions(Level level) {
        // Check for a level boundary collision
        if (isOutOfBounds(level.boundaryTopLeft, level.boundaryBottomRight)) {
            moveBack();
        }
    }

    private void checkBlockCollisions(Level level) {
        // Check for a block collision (including bombs)
        for (Block b : level.allBlocks) {
            if (hasCollided(b)) {
                // Check if `b` is a Bomb object
                if (b instanceof Bomb) {
                    // Explode the bomb in the sailor's face... Ouch!
                    ((Bomb) b).explode(this);
                }
                // Either way, move the sailor back to its previous position
                moveBack();
                break;
            }
        }
    }

    /**
     * Causes the sailor to lose health points.
     */
    @Override
    public void getHit(int damagePoints) {
        reduceHealth(damagePoints);
    }

    private void attack(Input input, Level level) {
        // Check if the sailor should enter the attack state
        checkAttackKey(input);
        // If the sailor is idle then just exit this method
        if (isIdle) {
            return;
        }
        // If the timer is on then the sailor
        // is still in the attack state
        if (!stateTimer.isOff()) {
            for (Pirate p : level.allPirates) {
                // Check if the sailor has collided with (attacked) the pirate
                if (hasCollided(p)) {
                    p.getHit(damagePoints);
                }
            }
        } else {
            // Revert back to the idle state
            isIdle = true;
            isAttacking = false;
        }
    }

    private void checkAttackKey(Input input) {
        if (input.wasPressed(Keys.S) && isIdle && canAttack()) {
            // Enter the attack state
            isIdle = false;
            isAttacking = true;
            stateTimer.turnOn();
        }
    }

    /* Determines if the sailor has collided
       with the passed Pirate object. */
    private boolean hasCollided(Pirate pirate) {
        Rectangle sailorHitbox = this.getHitbox();
        return sailorHitbox.intersects(pirate.getHitbox());
    }

    /**
     *  Determines if the sailor has collided
     *  with the passed Item object.
     */
    public boolean hasCollided(Item item) {
        Rectangle sailorHitbox = this.getHitbox();
        return sailorHitbox.intersects(item.getHitbox());
    }
}
