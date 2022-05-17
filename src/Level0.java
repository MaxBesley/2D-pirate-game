/* Max Besley. May 2022. */

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The first level of the ShadowPirate game.
 */
public class Level0 extends Level {
    private static final String LADDER_MESSAGE = "USE ARROW KEYS TO FIND LADDER";
    private static final String COMPLETE_MESSAGE = "LEVEL COMPLETE!";
    private static final int COMPLETE_MSG_LEN = 3000;
    private static final int LADDER_X = 990;
    private static final int LADDER_Y = 630;


    /*
     * Creates the first level of ShadowPirate.
     */
    public Level0() {
        super();
        BACKGROUND_IMAGE = new Image("res/background0.png");
    }

    /*
     * Updates the internal state of level 1
     * based on the game user's keyboard inputs.
     */
    @Override
    public void update(Input input) {
        // in next commit say "fully implemented projectiles and pirate attack as well as sailor game over screen"

        if (!levelOn) {
            drawStartScreen(input, LADDER_MESSAGE);
        }

        if (levelWon) {
            drawEndScreen(COMPLETE_MESSAGE);
        }

        if (levelLost) {
            drawEndScreen(LOSE_MESSAGE);
        }

        if (levelOn && !levelWon && !levelLost) {
            // Draw the background image
            renderBackground();

            for (Block b : allBlocks) { // can we shorten this into a single line?
                b.update(sailor);
            }
            // Remove any bombs that have exploded and should now vanish
            //allBlocks.removeIf(Block::isToBeDeleted);

            for (Pirate p : allPirates) {
                p.update(this);
            }
            // Remove/delete any pirates that should no longer be updated
            allPirates.removeIf(Pirate::isToBeDeleted);

            // Update the sailor
            sailor.update(input, this);

            for (Projectile p : allProjectiles) {
                p.update(this);
            }
            // For performance reasons remove redundant projectiles
            allProjectiles.removeIf(Projectile::isToBeDeleted);
        }

        // Check if the player completed level0
        if (isComplete()) {
            levelWon = true;
        }
        // Otherwise, check if the player died
        else if (isLost()) {
            levelLost = true;
        }

    }

    /*
     * Determines if the player has complete/won the first level (i.e. level0).
     */
    @Override
    public boolean isComplete() {
        // Check whether the sailor has reached the ladder
        return sailor.getX() >= LADDER_X && sailor.getY() > LADDER_Y;
    }

    /*
     *
     */
    public void readCSV(String fileName) {
        int x, y;
        String line;
        String[] fields;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            // Get the first file entry and create the Sailor object
            if ((line = br.readLine()) != null) {
                fields = line.split(",");
                if (fields[0].equals("Sailor")) {
                    sailor = new Sailor(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
                }
            }

            // Process the rest of the CSV file and create various objects
            while ((line = br.readLine()) != null) {
                // Get the fields and store the second and third fields
                fields = line.split(",");
                x = Integer.parseInt(fields[1]);
                y = Integer.parseInt(fields[2]);
                // Create and store the appropriate object
                switch (fields[0]) {
                    case "Block":
                        allBlocks.add(new Block(x, y));
                        break;
                    case "Pirate":
                        allPirates.add(new Pirate(x, y));
                        break;
                    case "TopLeft":
                        boundaryTopLeft = new Point(x, y);
                        break;
                    case "BottomRight":
                        boundaryBottomRight = new Point(x, y);
                        break;
                    default:
                        System.err.println("Error: the CSV file has an invalid field in the first column");
                        System.exit(1);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
