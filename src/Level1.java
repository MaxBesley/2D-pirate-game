/* Max Besley. May 2022. */

import bagel.Input;
import bagel.Image;
import bagel.util.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The second level of the ShadowPirate game.
 */
public class Level1 extends Level {
    private static final String WIN_MESSAGE = "CONGRATULATIONS!";
    private static final String TREASURE_MESSAGE = "FIND THE TREASURE";
    private Treasure treasure;
    private ArrayList<Item> allItems;


    /**
     * Creates the second level of ShadowPirate.
     */
    public Level1() {
        super();
        allItems = new ArrayList<Item>();
        backgroundImage = new Image("res/background1.png");
    }

    /**
     * Updates the internal state of level 1
     * based on the game user's keyboard inputs.
     */
    public void update(Input input) {
        if (!levelOn) {
            drawStartScreen(input, TREASURE_MESSAGE);
        }

        if (levelWon) {
            drawEndScreen(WIN_MESSAGE);
        }

        if (levelLost) {
            drawEndScreen(LOSE_MESSAGE);
        }

        if (levelOn && !levelWon && !levelLost) {
            renderBackground();

            for (Block b : allBlocks) {
                b.update(sailor);
            }
            // Remove any bombs that have exploded and should now vanish
            allBlocks.removeIf(Block::isToBeDeleted);

            for (Pirate p : allPirates) {
                p.update(this);
            }
            allPirates.removeIf(Pirate::isToBeDeleted);

            for (Projectile p : allProjectiles) {
                p.update(this);
            }
            allProjectiles.removeIf(Projectile::isToBeDeleted);

            // Update all the stationary items
            for (Item i : allItems) {
                i.update(sailor);
            }
            allItems.removeIf(Item::isToBeDeleted);

            // Draw the treasure
            treasure.draw();

            sailor.update(input, this);

            if (isComplete()) {
                levelWon = true;
            }
            else if (isLost()) {
                levelLost = true;
            }
        }
    }

    /**
     * Determines if the player has won the second level (i.e. level1).
     */
    public boolean isComplete() {
        // Check if the sailor has collided with the treasure
        return sailor.getHitbox().intersects(treasure.getHitbox());
    }

    /**
     * Method used to process world files and create objects.
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
                        allBlocks.add(new Bomb(x, y));
                        break;
                    case "Pirate":
                        allPirates.add(new Pirate(x, y));
                        break;
                    case "Blackbeard":
                        allPirates.add(new Blackbeard(x, y));
                        break;
                    case "Potion":
                        allItems.add(new Potion(x, y));
                        break;
                    case "Elixir":
                        allItems.add(new Elixir(x, y));
                        break;
                    case "Sword":
                        allItems.add(new Sword(x, y));
                        break;
                    case "Treasure":
                        treasure = new Treasure(x, y);
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
