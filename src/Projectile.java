/* Max Besley. May 2022. */

import static java.lang.Math.atan;
import bagel.util.Point;
import bagel.util.Vector2;
import bagel.DrawOptions;
import bagel.Image;

/*
 * Represents a projectile that can be fired by a pirate.
 */
public class Projectile {
    private final Image PROJECTILE_IMAGE;
    private Vector2 unitVector;
    private DrawOptions options;
    private double x;                       // x coordinate of projectile
    private double y;                       // y coordinate of projectile
    private final int DAMAGE_POINTS;        // damage the projectile inflicts
    private final double SPEED;             // in pixels per frame
    private final double ROTATION_ANGLE;    // in units of radians
    private boolean toBeDeleted;
    private static final double HIT_THRESHOLD = 20.0;    // How close the projectile needs to be to its target to hit


    /*
     * Creates a projectile.
     */
    public Projectile(Position source, Position target, int damagePoints, double speed, Image projectileImage) {
        PROJECTILE_IMAGE = projectileImage;
        DAMAGE_POINTS = damagePoints;
        SPEED = speed;
        toBeDeleted = false;

        // Initially, the projectile is drawn at the pirate's position
        x = source.getX();
        y = source.getY();

        // Determine the correct rotation angle
        if (target.getX() - source.getX() != 0.0) {
            // I give all the credit here to Calculus 2
            ROTATION_ANGLE = atan((target.getY() - source.getY()) / (target.getX() - source.getX()));
        } else {
            // Be careful of divide-by-zero error
            ROTATION_ANGLE = 0.0;
        }

        // Calculate the unit vector pointing from the source (pirate) to the target (sailor)
        unitVector = new Vector2(target.getX() - source.getX(),
                                 target.getY() - source.getY()).normalised();
        // This will be used to rotate `PROJECTILE_IMAGE`
        options = new DrawOptions().setRotation(ROTATION_ANGLE);
    }

    /*
     * Updates the internal state of a moving projectile.
     */
    public void update(Level level) {
        draw();
        updatePosition();
        checkCollisions(level);
    }

    private void draw() {
        // Render the Projectile object to the screen
        PROJECTILE_IMAGE.draw(x, y, options);
    }

    private void updatePosition() {
        // The logic here was inspired by the solution for Question 5 of Workshop 4
        x += unitVector.x * SPEED;
        y += unitVector.y * SPEED;
    }

    private void checkCollisions(Level level) {
        // Check collision with level boundary
        if (isOutOfBounds(level.boundaryTopLeft, level.boundaryBottomRight)) {
            toBeDeleted = true;
        }
        // Check collision with sailor
        else if (new Point(level.sailor.getX(), level.sailor.getY()).distanceTo(new Point(x, y)) <= HIT_THRESHOLD) {
            level.sailor.getHit(DAMAGE_POINTS);
            toBeDeleted = true;
        }
    }

    private boolean isOutOfBounds(Point topLeft, Point bottomRight) {
        return !(topLeft.x < x && x < bottomRight.x &&
                 topLeft.y < y && y < bottomRight.y);
    }

    /*
     * Returns whether the projectile needs to be deleted from the game.
     */
    public boolean isToBeDeleted() {
        return toBeDeleted;
    }
}
