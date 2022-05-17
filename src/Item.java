/* Max Besley. May 2022. */

import bagel.Image;

/**
 *
 */
public abstract class Item {
    public Image ITEM_IMAGE;
    public Image ITEM_ICON_IMAGE;
    public boolean isPickedUp;
    public final int x;
    public final int y;


    /**
     * Called in subclasses of Item.
     */
    public Item(int x, int y) {
        this.x = x;
        this.y = y;
        isPickedUp = false;
    }

    /**
     * Updates the internal state of an Item object.
     */
    public void update() {
        drawImage();
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
        ITEM_ICON_IMAGE.draw(x, y);
    }
}
