/* Max Besley. May 2022. */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Represents an abstract stationary pickup item in the game.
 */
public abstract class Item {
    public Image ITEM_IMAGE;
    public Image ITEM_ICON_IMAGE;
    public static final int ITEM_ICON_SPACING = 40;
    public final int x;
    public final int y;
    public boolean pickedUp;


    /**
     * Called in subclasses of Item class.
     */
    public Item(int x, int y) {
        this.x = x;
        this.y = y;
        pickedUp = false;
    }

    /**
     * Updates the internal state of an Item object.
     * Does nothing if the item has already been acquired.
     */
    public void update(Sailor sailor) {
        if (!pickedUp) {
            drawImage();
            // Check if the sailor is touching the item
            if (sailor.hasCollided(this)) {
                pickedUp = true;
                applyEffect(sailor);
                sailor.addToInventory(this);
            }
        } else {
            // Draw the item's icon below the sailor's health bar
            drawIcon(10, 40 + ITEM_ICON_SPACING * sailor.getIndexOfItem(this));
        }
    }

    /**
     * Draws an Item object to the screen.
     */
    public void drawImage() {
        ITEM_IMAGE.drawFromTopLeft(x, y);
    }

    /**
     * Draws an Item object's icon to the screen
     * at the specified x, y coordinate.
     */
    public void drawIcon(int x, int y) {
        ITEM_ICON_IMAGE.drawFromTopLeft(x, y);
    }

    /**
     * Returns the hitbox of an item object.
     */
    public Rectangle getHitbox() {
        return ITEM_IMAGE.getBoundingBoxAt(new Point(x + ITEM_IMAGE.getWidth()/2.0,
                                                     y + ITEM_IMAGE.getHeight()/2.0));
    }

    /**
     *  Applies a particular effect to the
     *  sailor who picked up the item.
     *  These effects boost the sailor's attributes.
     */
    public abstract void applyEffect(Sailor sailor);
}
