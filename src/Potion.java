/* Max Besley. May 2022. */

import bagel.Image;

/**
 *
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
}
