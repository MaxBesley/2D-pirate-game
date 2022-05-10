/* Max Besley. May 2022. */

import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Window;
import bagel.util.Point;

/**
 *
 */
public abstract class Level {
    public Image BACKGROUND_IMAGE;
    public Point boundaryTopLeft;
    public Point boundaryBottomRight;
    public final int MESSAGE_SIZE = 55;
    public final int INSTRUCTION_OFFSET = 70;
    final int FONT_Y_POS = 402;
    final Font FONT = new Font("res/wheaton.otf", MESSAGE_SIZE);
    public final String START_MESSAGE = "PRESS SPACE TO START";
    public final String ATTACK_MESSAGE = "PRESS S TO ATTACK";
    public final String LOSE_MESSAGE = "GAME OVER";
    public boolean levelOn;
    public boolean levelWon;


    public Level() {
        levelOn = false;
        levelWon = false;
    }

    public void renderBackground() {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
    }


    /**
    *   Method used to read file and create objects
    */
    public abstract void readCSV(String fileName);

    public abstract void update(Input input);

    public abstract boolean isComplete();

}
