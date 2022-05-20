/* Max Besley. 20 May 2022. */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Represents an abstract stationary pickup item in the game.
 */
public abstract class Item {
    /** Image to be used to draw the specific item. */
    public Image ITEM_IMAGE;
    /** Item's icon for the sailor's inventory. */
    public Image ITEM_ICON_IMAGE;
    /** Used for spacing out the item icon images. */
    public static final int ITEM_ICON_SPACING = 40;
    /** x coordinate of top left corner of item's image. */
    public final int x;
    /** y coordinate of top left corner of item's image. */
    public final int y;
    /** Stores whether or not the item has been picked up. */
    public boolean pickedUp;
    private static final int ICON_X = 10;
    private static final int ICON_Y = 40;


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
                // Add an entry to the log on the command line
                if (this instanceof Sword) {
                    System.out.println("Sailor finds Sword. Sailor’s damage points increased to " + sailor.damagePoints);
                } else {
                    System.out.println("Sailor finds " + getClass().getSimpleName() + ". Sailor’s current health: " +
                                        sailor.healthBar.getCurrHealthPoints() + "/" + sailor.healthBar.getMaxHealthPoints());
                }
            }
        } else {
            // Draw the item's icon below the sailor's health bar
            drawIcon(ICON_X, ICON_Y + ITEM_ICON_SPACING * sailor.getIndexOfItem(this));
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
