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
    private final Vector2 unitVector;
    private final DrawOptions options;
    private final Pirate firedBy;           // The Pirate object who fired the projectile
    private double x;                       // x coordinate of projectile
    private double y;                       // y coordinate of projectile
    private final int DAMAGE_POINTS;        // damage the projectile inflicts
    private final double SPEED;             // in pixels per frame
    private final double ROTATION_ANGLE;    // in units of radians
    private boolean toBeDeleted;
    /* How close the projectile needs to be to hit its target.
       Through testing, I found that this particular value works well.
       But other choices could also be made. */
    private static final double HIT_THRESHOLD = 20.0;


    /**
     * Creates a projectile.
     */
    public Projectile(Pirate pirate, Position source, Position target, int damagePoints, double speed, Image projectileImage) {
        firedBy = pirate;
        SPEED = speed;
        DAMAGE_POINTS = damagePoints;
        PROJECTILE_IMAGE = projectileImage;
        toBeDeleted = false;

        // Initially, the projectile is drawn at the pirate's position
        x = source.getX();
        y = source.getY();

        // Determine the correct rotation angle (checking all edge cases)
        if (target.getX() - source.getX() != 0.0) {
            // I give all the credit here to Calculus 2
            ROTATION_ANGLE = atan((target.getY() - source.getY()) / (target.getX() - source.getX()));
        }
        // Avoid divide-by-zero errors
        else {
            // Determine if we should rotate 90 or -90 degrees
            if (target.getY() < source.getY()) {
                ROTATION_ANGLE = Math.PI/2.0;
            } else if (target.getY() > source.getY()) {
                ROTATION_ANGLE = -1 * (Math.PI/2.0);
            } else {
                // In this case, the source and target are on top of each other
                ROTATION_ANGLE = 0.0;
            }
        }

        // Calculate the unit vector pointing from the source (pirate) to the target (sailor)
        unitVector = new Vector2(target.getX() - source.getX(),
                                 target.getY() - source.getY()).normalised();
        // This will be used to rotate `PROJECTILE_IMAGE`
        options = new DrawOptions().setRotation(ROTATION_ANGLE);
    }

    /**
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
        // Check collision with sailor (note: the projectile just needs to get close enough)
        else if (new Point(level.sailor.getX(), level.sailor.getY()).distanceTo(new Point(x, y)) <= HIT_THRESHOLD) {
            toBeDeleted = true;
            // Hit the sailor
            level.sailor.getHit(DAMAGE_POINTS);
            // Print out a log entry
            System.out.println(firedBy.getClass().getSimpleName() + " inflicts " + DAMAGE_POINTS + " damage points on Sailor."
                               + " Sailor's current health: " + Math.max(0, level.sailor.healthBar.getCurrHealthPoints())
                               + "/" + level.sailor.healthBar.getMaxHealthPoints());
        }
    }

    private boolean isOutOfBounds(Point topLeft, Point bottomRight) {
        return !(topLeft.x < x && x < bottomRight.x &&
                 topLeft.y < y && y < bottomRight.y);
    }

    /**
     * Returns whether a projectile needs to be deleted from the game.
     */
    public boolean isToBeDeleted() {
        return toBeDeleted;
    }
}
