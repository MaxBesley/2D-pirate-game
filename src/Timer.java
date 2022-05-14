/* Max Besley. May 2022. */

/*
 *
 */
public class Timer {
    private double currTime;
    private boolean isRunning;
    private final double START_TIME;    // find a better name? TIMER_LENGTH?
    private static final double MILLISECS_IN_SEC = 1000.0;

    /*
     *
     */
    public Timer(double startTime) {
        START_TIME = startTime;
        currTime = startTime;
        turnOff();
    }

    /*
     * Method that updates the internal state of a Timer object.
     */
    public void update() {
        if (isOn()) {
            // Reduce the amount of time
            currTime -= MILLISECS_IN_SEC / ShadowPirate.getRefreshRate();   // need to check this later
            // Check if the timer has finished ticking down
            if (isOver()) {
                turnOff();
            }
        }
    }

    /*
     * Method that returns the Timer's current time.
     */
    public double getCurrTime() {
        return currTime;
    }

    /*
     * Method that resets a Timer object
     * back to its initial state.
     */
    public void reset() {
        currTime = START_TIME;
        turnOff();
    }

    /*
     * Method that returns whether the Timer is on/running.
     */
    public boolean isOn() {
        return isRunning;
    }

    /*
     * Method that starts the Timer object.
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
