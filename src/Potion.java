/* Max Besley. 20 May 2022. */

import bagel.Image;

/**
 * Represents the potion pickup item.
 */
public class Potion extends Item {
    private static int INCREASE_CURR_HEALTH_BY = 25;


    /**
     * Creates a Potion object at the
     * specified x, y coordinate.
     */
    public Potion(int x, int y) {
        super(x, y);
        ITEM_IMAGE  = new Image("res/items/potion.png");
        ITEM_ICON_IMAGE  = new Image("res/items/potionIcon.png");
    }

    /**
     * Increases the sailor’s current health points value by
     * `INCREASE_CURR_HEALTH_BY` (but not beyond the sailor’s
     * current maximum health points value).
     */
    @Override
    public void applyEffect(Sailor sailor) {
        int boostedCurrHP = sailor.healthBar.getCurrHealthPoints() + INCREASE_CURR_HEALTH_BY;
        int maxHP = sailor.healthBar.getMaxHealthPoints();

        sailor.healthBar.setCurrHealthPoints(Math.min(boostedCurrHP, maxHP));
    }
}
