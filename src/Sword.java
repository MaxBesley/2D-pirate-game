/* Max Besley. 20 May 2022. */

import bagel.Image;

/**
 * Represents the sword pickup item.
 */
public class Sword extends Item {
    private static int INCREASE_DAMAGE_POINTS_BY = 15;


    /**
     * Creates a Sword object at the
     * specified x, y coordinate.
     */
    public Sword(int x, int y) {
        super(x, y);
        ITEM_IMAGE  = new Image("res/items/sword.png");
        ITEM_ICON_IMAGE  = new Image("res/items/swordIcon.png");
    }

    /**
     * Permanently increases the sailor’s damage
     * points value by `INCREASE_DAMAGE_POINTS_BY`.
     */
    @Override
    public void applyEffect(Sailor sailor) {
        sailor.damagePoints += INCREASE_DAMAGE_POINTS_BY;
    }
}
