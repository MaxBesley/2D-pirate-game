/* Max Besley. May 2022. */

import bagel.Image;

/**
 * Represents the pirate Blackbeard, who is the "boss" of the ShadowPirate game.
 */
public class Blackbeard extends Pirate {
    private final Image BB_LEFT = new Image("res/blackbeard/blackbeardLeft.png");
    private final Image BB_RIGHT = new Image("res/blackbeard/blackbeardRight.png");
    private final Image BB_INVINC_LEFT = new Image("res/blackbeard/blackbeardHitLeft.png");
    private final Image BB_INVINC_RIGHT = new Image("res/blackbeard/blackbeardHitRight.png");
    private final Image BB_PROJECTILE_IMAGE = new Image("res/blackbeard/blackbeardProjectile.png");
    private static final int BB_SCALE_FACTOR = 2;


    /**
     * Creates a Blackbeard object that is loaded
     * at the specified x, y coordinate.
     */
    public Blackbeard(int x, int y) {
        super(x, y);
        // Boost the stats of Blackbeard
        projectileImage = BB_PROJECTILE_IMAGE;
        damagePoints *= BB_SCALE_FACTOR;
        maxHealthPoints *= BB_SCALE_FACTOR;
        healthBar = new HealthBar(maxHealthPoints);
        attackRangeSize *= BB_SCALE_FACTOR;
        projectileSpeed += BB_SCALE_FACTOR;
        attackCooldownDuration /= BB_SCALE_FACTOR;
        attackCooldownTimer = new Timer(attackCooldownDuration);
    }

    @Override
    protected void updateCurrentImage() {
        // Determine what the current image should be
        if (isInvincible) {
            if (isFacingRight) {
                currentImage = BB_INVINC_RIGHT;
            } else {
                currentImage = BB_INVINC_LEFT;
            }
        } else {
            if (isFacingRight) {
                currentImage = BB_RIGHT;
            } else {
                currentImage = BB_LEFT;
            }
        }
    }
}
