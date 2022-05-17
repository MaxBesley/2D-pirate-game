/* Max Besley. May 2022. */

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
        hasExploded = false;
    }

    /**
     * Updates the internal state of a Bomb object.
     */
    public void update(Sailor sailor) {
        draw();
        if (!hasExploded && sailor.hasCollided(this)) {
            // Explode the bomb in the sailor's face
            explode(sailor);
        } else if (hasExploded && explosionTimer.isOff()) {
            // Get rid of the exploded bomb ASAP
            toBeDeleted = true;
        }
    }

    private void explode(Sailor sailor) {
        currentImage = EXPLOSION_IMAGE;
        hasExploded = true;
        sailor.getHit(DAMAGE_POINTS);
        explosionTimer = new Timer(EXPLODE_DURATION);
        explosionTimer.turnOn();
    }
}
