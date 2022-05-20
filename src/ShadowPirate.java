/* Max Besley. 20 May 2022. */

import bagel.*;

/**
 * SWEN20003 Project 2, Semester 1, 2022.
 *
 * @author Max Besley
 */
public class ShadowPirate extends AbstractGame {
    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;
    private static final String GAME_TITLE = "ShadowPirate";
    private static final int REFRESH_RATE = 60;

    // The filenames of the CSV world files
    private static final String WORLD_FILE0 = "res/level0.csv";
    private static final String WORLD_FILE1 = "res/level1.csv";

    // Counts the total number of calls to `update()`
    private static int totalFramesRendered = 0;

    // The game levels (only two)
    private Level0 level0;
    private Level1 level1;
    private boolean inLevel0;
    private boolean inLevel1;


    /**
     * Creates the actual ShadowPirate game.
     */
    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        // Create level 0 and initialise it using the world file
        level0 = new Level0();
        level0.readCSV(WORLD_FILE0);
        inLevel0 = true;
        // Likewise for level 1
        level1 = new Level1();
        level1.readCSV(WORLD_FILE1);
        inLevel1 = false;
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

        // For exiting the game
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        if (inLevel0) {
            level0.update(input);
            if (level0.isReadyToTransition()) {
                inLevel0 = false;
                inLevel1 = true;
            }
        }

        if (inLevel1) {
            level1.update(input);
        }

        // To skip ahead to level1 (shhhhh! this is a secret!)
        if (input.wasPressed(Keys.W)) {
            inLevel0 = false; inLevel1 = true;
        }

        totalFramesRendered++;
    }

    /**
     * Returns the `REFRESH_RATE` constant integer.
     */
    public static int getRefreshRate() {
        return REFRESH_RATE;
    }

    /**
     * Returns the `totalFramesRendered` integer counter.
     */
    public static int getTotalFramesRendered() {
        return totalFramesRendered;
    }
}
