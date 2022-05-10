/* Max Besley. May 2022. */

import bagel.Image;
import bagel.Input;

import java.util.ArrayList;

public class Pirate extends Person {
    private final Image SAILOR_LEFT;
    private final Image SAILOR_RIGHT;


    public Pirate(int xCoord, int yCoord) {
        SAILOR_LEFT = new Image("res/pirate/pirateLeft.png");;
        SAILOR_RIGHT = new Image("res/pirate/pirateRight.png");;
        currentImage = SAILOR_RIGHT;
        damagePoints = 10;
        maxHealthPoints = 45;
        healthBar = new HealthBar(maxHealthPoints);
        position = new Position((int) (xCoord + SAILOR_LEFT.getWidth()/2), (int) (yCoord + SAILOR_LEFT.getHeight()/2));
        oldPosition = null;
    }

    public void update(Input input, ArrayList<Block> allBlocks) {
        draw();
    }

    public void move(Input input) {

    }
}
