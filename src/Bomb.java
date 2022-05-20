/* Max Besley. 20 May 2022. */

import bagel.Image;

/**
 * Represents a bomb in the ShadowPirate game.
 */
public class Bomb extends Block {
    private final Image BOMB_IMAGE = new Image("res/bomb.png");;
    private final Image EXPLOSION_IMAGE = new Image("res/explosion.png");
    private static final int DAMAGE_POINTS = 10;
    private static final int EXPLODE_DURATION = 500;
    private Timer explosionTimer;
    private boolean hasExploded;


    /**
     * Creates a Bomb object centred at the specified x, y coordinate.
     */
    public Bomb(int x, int y) {
        super(x, y);
        currentImage = BOMB_IMAGE;
        explosionTimer = new Timer(EXPLODE_DURATION);
        hasExploded = false;
    }

    /**
     * Updates the internal state of a Bomb object.
     */
    public void update(Sailor sailor) {
        draw();
        // The line below will do nothing if the timer is off
        explosionTimer.update();
        // Check if the timer has finished ticking down
        if (hasExploded && explosionTimer.isOff()) {
            // Get rid of the exploded bomb ASAP
            toBeDeleted = true;
        }
    }

    /**
     * Causes the bomb to explode once and cause damage to the sailor.
     * @param sailor This is the sailor who will be attacked.
     */
    public void explode(Sailor sailor) {
        // The if statement here ensures only one explosion occurs
        if (!hasExploded) {
            // Deal damage to the sailor
            sailor.getHit(DAMAGE_POINTS);
            // Put the bomb into the 'exploding' state
            currentImage = EXPLOSION_IMAGE;
            hasExploded = true;
            explosionTimer.turnOn();
            // Print out a log entry
            System.out.println("Bomb inflicts " + DAMAGE_POINTS + " damage points on Sailor. Sailor’s current health: "
                              + sailor.healthBar.getCurrHealthPoints() + "/" + sailor.healthBar.getMaxHealthPoints());

        }
    }
}
