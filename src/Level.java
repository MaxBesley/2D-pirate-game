/* Max Besley. 20 May 2022. */

import bagel.Keys;
import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Window;
import bagel.util.Point;
import java.util.ArrayList;

/**
 * Represents an abstract level of the ShadowPirate game.
 */
public abstract class Level {
    /** Game's background image. */
    public Image backgroundImage;
    /** Top left corner of level boundary. */
    public Point boundaryTopLeft;
    /** Bottom right corner of level boundary. */
    public Point boundaryBottomRight;
    private final int MESSAGE_SIZE = 55;
    private final int INSTRUCTION_OFFSET = 70;
    private final int FONT_Y_POS = 402;
    private final Font FONT = new Font("res/wheaton.otf", MESSAGE_SIZE);
    /** Top message of the start screen. */
    public static final String START_MESSAGE = "PRESS SPACE TO START";
    /** Middle message of the start screen. */
    public static final String ATTACK_MESSAGE = "PRESS S TO ATTACK";
    /** Message for when the player dies. */
    public static final String LOSE_MESSAGE = "GAME OVER";
    /** Records whether or not the game has started. */
    public boolean levelOn;
    /** Records whether or not the player has won. */
    public boolean levelWon;
    /** Records whether or not the player has lost/died. */
    public boolean levelLost;
    /** The sailor controlled by the player. */
    public Sailor sailor;
    /** All the blocks/bombs contained in the level. */
    public ArrayList<Block> allBlocks;
    /** All the enemies of the game (regular pirates and Blackbeard). */
    public ArrayList<Pirate> allPirates;
    /** All the projectiles currently in the air. */
    public ArrayList<Projectile> allProjectiles;


    /**
     * Constructor for the abstract class `Level`.
     */
    public Level() {
        levelOn = false;
        levelWon = false;
        levelLost = false;
        allBlocks = new ArrayList<Block>();
        allPirates = new ArrayList<Pirate>();
        allProjectiles = new ArrayList<Projectile>();
    }

    /**
     * Renders the level's background image to the screen.
     */
    public void renderBackground() {
        backgroundImage.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
    }

    /**
     * Renders the start screen for the player.
     * @param goalMessage This is the third message to be displayed.
     */
    public void drawStartScreen(Input input, String goalMessage) {
        FONT.drawString(START_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(START_MESSAGE)/2.0)),
                        FONT_Y_POS);
        FONT.drawString(ATTACK_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(ATTACK_MESSAGE)/2.0)),
                     FONT_Y_POS + INSTRUCTION_OFFSET);
        FONT.drawString(goalMessage, (Window.getWidth()/2.0 - (FONT.getWidth(goalMessage)/2.0)),
                     FONT_Y_POS + INSTRUCTION_OFFSET + INSTRUCTION_OFFSET);
        if (input.wasPressed(Keys.SPACE)){
            levelOn = true;
        }
    }

    /**
     * Renders the end screen for the player.
     * @param message This is the text to be drawn.
     */
    public void drawEndScreen(String message) {
        FONT.drawString(message, (Window.getWidth()/2.0 - (FONT.getWidth(message)/2.0)), FONT_Y_POS);
    }

    /**
     * Determines and returns whether
     * the player has lost the game.
     */
    public boolean isLost() {
        // Ask: is the sailor dead or not?
        return !sailor.isAlive();
    }

    /**
     * Returns whether the level has
     * been won by the player.
     */
    public boolean isWon() {
        return levelWon;
    }

    /**
     * Reads the CSV world file and creates objects.
     */
    public abstract void readCSV(String fileName);

    /**
     * Updates the internal state of a level.
     */
    public abstract void update(Input input);

    /**
     * Returns whether the level is complete or not.
     */
    public abstract boolean isComplete();
}
