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
    private static final int ATTACK_RANGE = 100;
    private static final double ATTACK_COOLDOWN_LEN = 3000.0;
    private static final double INVINC_STATE_LEN = 1500.0;
    private Timer invincStateTimer;
    private static final double RAND_LOWER = 0.2;
    private static final double RAND_UPPER = 0.7;
    private final double SPEED;
    private Direction direction;
    private boolean isInvincible;
    private boolean isReadyToAttack;


    // Constructor
    public Pirate(int xCoord, int yCoord) {
        PIRATE_LEFT = new Image("res/pirate/pirateLeft.png");
        PIRATE_RIGHT = new Image("res/pirate/pirateRight.png");
        PIRATE_INVINC_LEFT = new Image("res/pirate/pirateHitLeft.png");
        PIRATE_INVINC_RIGHT = new Image("res/pirate/pirateHitLeft.png");
        PIRATE_PROJECTILE_IMAGE = new Image("res/pirate/pirateProjectile.png");
        damagePoints = 10;
        maxHealthPoints = 45;
        healthBar = new HealthBar(maxHealthPoints);
        healthBarSize = 15;
        stateTimer = new Timer(INVINC_STATE_LEN);
        attackCooldownTimer = new Timer(ATTACK_COOLDOWN_LEN);
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
        isReadyToAttack = true;
        System.out.println("SPEED = " + SPEED + " DIR = " + direction.getCurrentDir());
    }

    public void update() {

        move();

        updateTimers();
        if (isInvincible && !stateTimer.isOn()) {
            isInvincible = false;
            stateTimer.reset();
        }

        updateCurrentImage();
        draw();
        healthBar.draw((int) (getX() - currentImage.getWidth()/2),
                       (int) (getY() - currentImage.getHeight()/2 - HEALTH_BAR_Y_OFFSET), healthBarSize);
    }

    /*
     * Moves the pirate forward based on its current direction.
     */
    public void move() {
        if (direction.isMovingLeft()) {
            setX((int) (getX() - SPEED));
            isFacingRight = false;
        } else if (direction.isMovingRight()) {
            setX((int) (getX() + SPEED));
            isFacingRight = true;
        }  else if (direction.isMovingUp()) {
            setY((int) (getY() - SPEED));
        } else if (direction.isMovingDown()) {
            setY((int) (getY() + SPEED));
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
     * Method that causes a pirate to lose health
     * and then enter the INVINCIBLE state.
     */
    public void getHit(int damagePoints) {
        if (isInvincible) {
            return;
        }
        reduceHealth(damagePoints);
        isInvincible = true;
        stateTimer.turnOn();
        System.out.println("I was hit!");
    }
}
