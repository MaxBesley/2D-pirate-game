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

    /**
     * Creates a Blackbeard object that is loaded
     * at the specified x, y coordinate.
     */
    public Blackbeard(int x, int y) {
        super(x, y);

    }

    private void updateCurrentImage() {
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
