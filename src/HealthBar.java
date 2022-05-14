/* Max Besley. May 2022. */

import bagel.DrawOptions;
import bagel.Font;
import bagel.util.Point;
import bagel.util.Colour;

/**
 * A class that represents the health bar of some entity.
 * If other characters - other than the sailor - are introduced
 * later on in the game's development (for example, game enemies
 * to fight against), they too will need to have a health bar.
 * Hence, it makes sense to create a health bar class.
 */
public class HealthBar {
    // The only two instance variables
    private int currHealthPoints;
    private final int maxHealthPoints;
    // Constants for drawing the health bar
    private static final int HEALTH_BAR_SIZE = 30;
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


    // Constructor
    public HealthBar(int maxHealthPoints) {
        this.currHealthPoints = maxHealthPoints;
        this.maxHealthPoints = maxHealthPoints;
    }

    // Getter
    public int getCurrHealthPoints() {
        return currHealthPoints;
    }

    // Setter
    public void setCurrHealthPoints(int newHealthPoints) {
        currHealthPoints = newHealthPoints;
    }

    private int getHealthPercentage() {
        return (int) Math.round(currHealthPoints * 100.0 / maxHealthPoints);
    }

    /*
     * Draws the amount of health (as a percentage) to the
     * screen at the coordinate specified by `x` and `y`.
     * The health bar image size is passed as a parameter.
     */
    public void draw(int x, int y, int size) {
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
