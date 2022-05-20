/* Max Besley. 20 May 2022. */

/**
 * Represents a timer that counts down to zero.
 * Milliseconds are used as the unit of time.
 */
public class Timer {
    private double currTime;
    private boolean isRunning;
    private final double TIMER_LENGTH;
    private static final double MILLISECS_IN_SEC = 1000.0;


    /**
     * Creates a timer that will run for a
     * total of `timerLength` milliseconds.
     */
    public Timer(double timerLength) {
        TIMER_LENGTH = timerLength;
        currTime = timerLength;
        turnOff();
    }

    /**
     * Updates the internal state of a Timer object.
     */
    public void update() {
        // If the timer is off then exit immediately
        if (!isOff()) {
            // Reduce the amount of time
            currTime -= MILLISECS_IN_SEC / ShadowPirate.getRefreshRate();
            // Check if the timer has finished ticking down
            if (isOver()) {
                reset();
            }
        }
    }

    /**
     * Returns the timer's current time.
     */
    public double getCurrTime() {
        return currTime;
    }

    /**
     * Resets a Timer object back
     * to its initial state.
     */
    public void reset() {
        turnOff();
        currTime = TIMER_LENGTH;
    }

    /**
     * Returns whether the timer is on/running or off.
     */
    public boolean isOff() {
        return !isRunning;
    }

    /**
     * Starts the timer.
     */
    public void turnOn() {
        isRunning = true;
    }

    private void turnOff() {
        isRunning = false;
    }

    private boolean isOver() {
        return currTime <= 0.0;
    }
}
