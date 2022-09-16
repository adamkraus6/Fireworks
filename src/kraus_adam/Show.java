/**
 * Adam Kraus
 *
 * This file is for a fireworks show. You can add fireworks, track how many fireworks are up, if the show is in
 * warning, and the general status of the show.
 */
package kraus_adam;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class to store information about a fireworks show
 */
public class Show {
    /**
     * Default name for a fireworks show
     */
    public final static String DEFAULT_NAME = "test";
    /**
     * Percent of max fireworks when there should be a warning
     */
    public final static int WARNING_THRESHOLD = 80;

    /**
     * Name of the fireworks show
     */
    private String showName;
    /**
     * Maximum number of fireworks allowed up at one time
     */
    private int maxFireworks;
    /**
     * List of fireworks in the show
     */
    private ArrayList<Firework> fireworks;
    /**
     * Current time of the show
     */
    private int currentTime;
    /**
     * Times that the show has a warning
     */
    private ArrayList<Integer> warningTimes;

    /**
     * Show constructor
     * @param showName      name of the show
     * @param maxFireworks  max fireworks allowed up at a given time
     */
    public Show(String showName, int maxFireworks) {
        this.showName = showName;
        this.maxFireworks = maxFireworks;
        fireworks = new ArrayList<Firework>();
        currentTime = 0;
        warningTimes = new ArrayList<Integer>();
    }

    /**
     * Show constructor
     * @param maxFireworks  max fireworks allowed up at a given time
     */
    public Show(int maxFireworks) {
        // GRADING: CONSTRUCTION
        this(DEFAULT_NAME, maxFireworks);
    }

    /**
     * Adds firework to show
     * @param time      time that firework launches
     * @param duration  length of time that firework is in the air
     * @param cost      cost of firework
     * @return          true if successfully added
     */
    public boolean addFirework(int time, int duration, double cost) {
        if(duration < 1 || cost < 0) return false;
        if(time < currentTime) return false;
        if(getFireworksUpAt(time) >= maxFireworks) return false;

        fireworks.add(new Firework(time, duration, cost));

        Update(time);

        return true;
    }

    /**
     * Adds firework to show
     * @param time      time that firework launches
     * @param duration  length of time that firework is in the air
     * @return          true if successfully added
     */
    public boolean addFirework(int time, int duration) {
        if(duration < 1) return false;
        return addFirework(time, duration, Firework.DEFAULT_COST);
    }

    /**
     * Adds firework to show
     * @param time      time that firework launches
     * @return          true if successfully added
     */
    public boolean addFirework(int time) {
        return addFirework(time, Firework.DEFAULT_DURATION, Firework.DEFAULT_COST);
    }

    /**
     * Updates the show to a certain time
     * @param time  time the show is at
     */
    public void Update(int time) {
        if(time < currentTime) return;

        for(int t = currentTime; t <= time; t++) {
            hasWarningAt(t);
        }

        currentTime = time;
    }

    /**
     * Gets the number of fireworks up in the sky
     * @return  number of fireworks currently up
     */
    public int getFireworksUp() {
        return getFireworksUpAt(currentTime);
    }

    /**
     * Gets the number of fireworks up in the sky
     * @param time  time to check the sky
     * @return      number of fireworks up
     */
    public int getFireworksUpAt(int time) {
        int fireworksUp = 0;

        for(Firework firework : fireworks) {
            if(firework.time <= time && time <= firework.time + firework.duration) {
                fireworksUp++;
            }
        }

        return fireworksUp;
    }

    /**
     * Checks if the show has a warning at the current time
     * @return  true if there is currently a warning
     */
    public boolean hasWarning() {
        return hasWarningAt(currentTime);
    }

    /**
     * Checks if the show has a warning at the given time
     * @param time  time to check the show
     * @return      true if there is a warning at that time
     */
    public boolean hasWarningAt(int time) {
        if(warningTimes.contains(time)) return true;

        if((double)getFireworksUpAt(time) / (double)maxFireworks * 100 >= WARNING_THRESHOLD) {
            if(!warningTimes.contains(time)) {
                warningTimes.add(time);
            }
            return true;
        }

        return false;
    }

    /**
     * Gets the number of warnings throughout the show
     * @return  number of warnings
     */
    public int getTotalWarnings() {
        if(warningTimes.size() <= 1) return warningTimes.size();

        // counts groups of consecutive numbers in the array
        int warnings = 1;
        // needs to be sorted before checking
        warningTimes.sort(Comparator.naturalOrder());

        for(int i = 1; i < warningTimes.size(); i++) {
            if(warningTimes.get(i-1) != warningTimes.get(i) - 1) {
                warnings++;
            }
        }

        return warnings;
    }

    /**
     * Gets the cost of all the fireworks in the show
     * @return  total cost of fireworks
     */
    public double getCost() {
        double totalCost = 0;

        for(Firework firework : fireworks) {
            totalCost += firework.cost;
        }

        return totalCost;
    }

    /**
     * Gets the name of the show
     * @return  name of the show
     */
    public String getName() {
        return showName;
    }

    /**
     * Prints the show status, which includes the show name, fireworks up, and percent of max fireworks.
     * If the percent of max fireworks is above the warning threshold, WARNING is printed instead of the percent.
     * @return  status string
     */
    public String toString() {
        String string = "Status for " + showName + " show: " + getFireworksUp() + " fireworks up (";

        double percent = (double)getFireworksUp() / (double)maxFireworks * 100;
        if(percent >= WARNING_THRESHOLD) {
            string += "WARNING";
        } else {
            DecimalFormat format = new DecimalFormat("##.#");
            string += format.format(percent) + "%";
        }

        string += ")";
        return string;
    }
}
