/* Max Besley. May 2022. */

import bagel.*;
import bagel.util.Point;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * SWEN20003 Project 2, Semester 1, 2022
 *
 * @author Max Besley
 */
public class ShadowPirate extends AbstractGame {
    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;
    private static final int REFRESH_RATE = 60;
    private static final String GAME_TITLE = "ShadowPirate";
    //private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

    // The filenames of the CSV world files
    private static final String WORLD_FILE0 = "res/level0.csv";
    private static final String WORLD_FILE1 = "res/level1.csv";

    private Sailor sailor;
    private boolean gameHasEnded;
    private boolean playerHasWon;
    private boolean inLevel0;
    private boolean inLevel1;

    private Level level0 = new Level0();
    //private Level1 level1


    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        // Initialise level 0 using the world file
        level0.readCSV(WORLD_FILE0);
        // Set the initial state of the game
        gameHasEnded = false;
        playerHasWon = false;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        level0.update(input);


    }

    /*
     * Method that returns the `REFRESH_RATE` constant integer.
     */
    public static int getRefreshRate() {
        return REFRESH_RATE;
    }
}
