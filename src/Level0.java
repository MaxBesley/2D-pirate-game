/* Max Besley. May 2022. */

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class Level0 extends Level {
    private static final String LADDER_MESSAGE = "USE ARROW KEYS TO FIND LADDER";
    private static final String COMPLETE_MESSAGE = "LEVEL COMPLETE!";
    private static final int COMPLETE_MSG_LEN = 3000;
    private static final int LADDER_X = 990;
    private static final int LADDER_Y = 630;
    private Sailor sailor;
    private ArrayList<Block> allBlocks = new ArrayList<Block>();
    private ArrayList<Pirate> allPirates = new ArrayList<Pirate>();
    //private Projectile[] allProjectiles;

    public Level0() {
        super();
        BACKGROUND_IMAGE = new Image("res/background0.png");
    }

    @Override
    public void update(Input input) {

        if (!levelOn) {
            drawStartScreen(input);
        }

        if (levelWon) {
            drawEndScreen();
        }

        if (levelOn && !levelWon) {
            renderBackground();
            for (Block b : allBlocks) {
                b.update();
            }
            for (Pirate p : allPirates) {
                p.update(null, null);
            }
            sailor.update(input, allBlocks);
        }

        if (isComplete()) {
            levelWon = true;
        }

    }

    /*
     *
     */
    private void drawStartScreen(Input input) {
        FONT.drawString(START_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(START_MESSAGE)/2.0)),
                        FONT_Y_POS);
        FONT.drawString(ATTACK_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(ATTACK_MESSAGE)/2.0)),
                        FONT_Y_POS + INSTRUCTION_OFFSET);
        FONT.drawString(LADDER_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(LADDER_MESSAGE)/2.0)),
                        FONT_Y_POS + INSTRUCTION_OFFSET + INSTRUCTION_OFFSET);
        if (input.wasPressed(Keys.SPACE)){
            levelOn = true;
        }
    }

    /*
     *
     */
    private void drawEndScreen() {
        FONT.drawString(COMPLETE_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(COMPLETE_MESSAGE)/2.0)), FONT_Y_POS);
    }

    @Override
    public boolean isComplete() {
        // Check whether the sailor has reached the ladder
        return sailor.getX() >= LADDER_X && sailor.getY() > LADDER_Y;
    }

    /*
     *
     */
    public void readCSV(String fileName) {
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
                fields = line.split(",");
                switch (fields[0]) {
                    case "Block":
                        allBlocks.add(new Block(Integer.parseInt(fields[1]), Integer.parseInt(fields[2])));
                        break;
                    case "Pirate":
                        allPirates.add(new Pirate(Integer.parseInt(fields[1]), Integer.parseInt(fields[2])));
                        break;
                    case "Blackbeard":
                        //allPirates.add(new Blackbeard(Integer.parseInt(fields[1]), Integer.parseInt(fields[2])));
                        break;
                    case "TopLeft":
                        break;
                    case "BottomRight":
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