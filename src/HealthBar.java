/* Max Besley. May 2022. */

import bagel.DrawOptions;
import bagel.Font;
import bagel.util.Colour;

/**
 * Represents the health bar of some game entity.
 */
public class HealthBar {
    // The only two instance variables
    private int currHealthPoints;
    private int maxHealthPoints;
    // Constants that define when the health bar should change colour
    private static final int GREEN_THRESHOLD = 65;
    private static final int ORANGE_THRESHOLD = 35;
    // The three colours the health bar can be displayed in
    private static final double GREEN_R = 0.0;
    private static final double GREEN_G = 0.8;
    private static final double GREEN_B = 0.2;
    private static final Colour GREEN = new Colour(GREEN_R, GREEN_G, GREEN_B);
    private static final double ORANGE_R = 0.9;
    private static final double ORANGE_G = 0.6;
    private static final double ORANGE_B = 0.0;
    private static final Colour ORANGE = new Colour(ORANGE_R, ORANGE_G, ORANGE_B);
    private static final double RED_R = 1.0;
    private static final double RED_G = 0.0;
    private static final double RED_B = 0.0;
    private static final Colour RED = new Colour(RED_R, RED_G, RED_B);


    /**
     * Creates a health bar with the specified
     * maximum amount of health points.
     * A health bar always starts "full".
     */
    public HealthBar(int maxHealthPoints) {
        this.currHealthPoints = maxHealthPoints;
        this.maxHealthPoints = maxHealthPoints;
    }

    /**
     * Returns the current amount of
     * health points in the health bar.
     */
    public int getCurrHealthPoints() {
        return currHealthPoints;
    }

    /**
     * Returns the maximum amount of
     * health points in the health bar.
     */
    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    /**
     * Sets the current amount of health points
     * to the integer `newHealthPoints`.
     */
    public void setCurrHealthPoints(int newHealthPoints) {
        currHealthPoints = newHealthPoints;
    }

    /**
     * Sets the maximum amount of health points
     * to the integer `newMaxHealthPoints`.
     */
    public void setMaxHealthPoints(int newMaxHealthPoints) {
        maxHealthPoints = newMaxHealthPoints;
    }

    private int getHealthPercentage() {
        return (int) Math.round(currHealthPoints * 100.0 / maxHealthPoints);
    }

    /**
     * Draws the amount of health (as a percentage) to the
     * screen at the specified x, y coordinate.
     * The health bar image size is passed as a parameter.
     */
    public void draw(double x, double y, int size) {
        Font font = new Font("res/wheaton.otf", size);
        DrawOptions options = new DrawOptions();
        int percentage = this.getHealthPercentage();

        if (percentage >= GREEN_THRESHOLD) {
            // Set colour to green
            options.setBlendColour(GREEN);
        } else if (percentage >= ORANGE_THRESHOLD) {
            // Set colour to orange
            options.setBlendColour(ORANGE);
        } else {
            // Set colour to red
            options.setBlendColour(RED);
        }

        // Now draw the text to the screen
        font.drawString(percentage + "%", x, y, options);
    }
}
