/**
 * Adam Kraus
 *
 * This is used to store information about a single firework for shows.
 */

package kraus_adam;

/**
  Class to store information about a single firework
 */
public class Firework {
    /**
     * Time that firework launches
     */
    public int time;
    /**
     * Length of time that firework is in the air
     */
    public int duration;
    /**
     * Cost of firework
     */
    public double cost;

    /**
     * Default duration for a firework
     */
    public static final int DEFAULT_DURATION = 1;
    /**
     * Default cost for a firework
     */
    public static final double DEFAULT_COST = 20.0;

    /**
     * Firework constructor
     * @param time      time that firework launches
     * @param duration  length of time that firework is in the air
     * @param cost      cost of firework
     */
    public Firework(int time, int duration, double cost)
    {
        this.time = time;
        this.duration = duration;
        this.cost = cost;
    }
}
