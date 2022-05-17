/* Max Besley. May 2022. */

import bagel.Image;

/**
 *
 */
public class Elixir extends Item {
    private static int INCREASE_MAX_HEALTH_BY = 35;


    /**
     * Creates an Elixir object at the
     * specified x, y coordinate.
     */
    public Elixir(int x, int y) {
        super(x, y);
        ITEM_IMAGE  = new Image("res/items/elixir.png");
        ITEM_ICON_IMAGE  = new Image("res/items/elixirIcon.png");
    }
}
