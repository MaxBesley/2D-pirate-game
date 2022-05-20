/* Max Besley. 20 May 2022. */

import bagel.Image;

/**
 * Represents the elixir pickup item.
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

    /**
     * Permanently increases the sailor’s maximum health points value
     * by `INCREASE_MAX_HEALTH_BY` and increases the current health
     * points to the new maximum value.
     */
    @Override
    public void applyEffect(Sailor sailor) {
        int boostedMaxHP = sailor.healthBar.getMaxHealthPoints() + INCREASE_MAX_HEALTH_BY;
        sailor.healthBar.setMaxHealthPoints(boostedMaxHP);
        sailor.healthBar.setCurrHealthPoints(boostedMaxHP);
    }
}
