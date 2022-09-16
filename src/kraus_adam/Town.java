/**
 * Firework Program - Java Basic
 * Adam Kraus
 * Programming Languages - CSC426
 * Due: 9/15/2022
 *
 * Additional OOP  requirements
 *     toString properly extended				DONE
 *     Constructors properly handled			DONE
 *     addFirework chaining handled properly 	DONE
 *     Access properly handled 				    DONE
 *
 * Last tier completed: All Completed
 *
 * This file is a town, which has multiple fireworks shows.
 */
package kraus_adam;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class to store information about a town with multiple fireworks shows
 */
public class Town {
    private ArrayList<Show> shows;
    private ArrayList<Integer> warningTimes;
    private int currentTime;

    /**
     * Town constructor
     */
    public Town() {
        shows = new ArrayList<Show>();
        warningTimes = new ArrayList<Integer>();
        currentTime = 0;
    }

    /**
     * Adds firework to show
     * @param showIndex     index of show in array
     * @param companyName   name of company that produced firework
     * @param time          time that firework launches
     * @param duration      length of time that firework is in the air
     * @param cost          cost of firework
     * @return              true if successfully added
     */
    public boolean addFirework(int showIndex, String companyName, int time, int duration, double cost) {
        if(duration < 1 || cost < 0) return false;

        CompanyShow companyShow = (CompanyShow) shows.get(showIndex);
        boolean added = companyShow.addFirework(companyName, time, duration, cost);
        if(added) {
            update(time);
        }

        return added;
    }

    /**
     * Adds firework to show
     * @param showIndex     index of show in array
     * @param companyName   name of company that produced firework
     * @param time          time that firework launches
     * @param duration      length of time that firework is in the air
     * @return              true if successfully added
     */
    public boolean addFirework(int showIndex, String companyName, int time, int duration) {
        if(duration < 1) return false;

        return addFirework(showIndex, companyName, time, duration, Firework.DEFAULT_COST);
    }

    /**
     * Adds firework to show
     * @param showIndex     index of show in array
     * @param companyName   name of company that produced firework
     * @param time          time that firework launches
     * @return              true if successfully added
     */
    public boolean addFirework(int showIndex, String companyName, int time) {
        return addFirework(showIndex, companyName, time, Firework.DEFAULT_DURATION, Firework.DEFAULT_COST);
    }

    /**
     * Adds firework to show
     * @param showIndex     index of show in array
     * @param time          time that firework launches
     * @param duration      length of time that firework is in the air
     * @param cost          cost of firework
     * @return              true if successfully added
     */
    public boolean addFirework(int showIndex, int time, int duration, double cost) {
        if(duration < 1 || cost < 0) return false;

        boolean added = shows.get(showIndex).addFirework(time, duration, cost);
        if(added) {
            update(time);
        }

        return added;
    }

    /**
     * Adds firework to show
     * @param showIndex     index of show in array
     * @param time          time that firework launches
     * @param duration      length of time that firework is in the air
     * @return              true if successfully added
     */
    public boolean addFirework(int showIndex, int time, int duration) {
        if(duration < 1) return false;

        return addFirework(showIndex, time, duration, Firework.DEFAULT_COST);
    }

    /**
     * Adds firework to show
     * @param showIndex     index of show in array
     * @param time          time that firework launches
     * @return              true if successfully added
     */
    public boolean addFirework(int showIndex, int time) {
        return addFirework(showIndex, time, Firework.DEFAULT_DURATION, Firework.DEFAULT_COST);
    }

    /**
     * Adds show to list in town
     * @param show  show to be added
     * @return      index of show in array
     */
    public int add(Show show) {
        shows.add(show);
        return shows.size() - 1;
    }

    /**
     * Gets the show at the given index
     * @param showIndex index of the show
     * @return          show at given index
     */
    public Show getShow(int showIndex) {
        return shows.get(showIndex);
    }

    /**
     * Updates all shows in town to given time
     * @param time  time to set to current
     */
    public void update(int time) {
        if(time < currentTime) return;

        for(Show show : shows) {
            show.Update(time);
        }

        for(int t = currentTime; t <= time; t++) {
            hasWarningAt(t);
        }

        currentTime = time;
    }

    /**
     * Checks if the town has a warning at the current time.
     * The town has a warning if all shows have a warning.
     * @return  true if there is a warning
     */
    public boolean hasWarning() {
        return hasWarningAt(currentTime);
    }

    /**
     * Checks if the town has a warning at the given time.
     * The town has a warning if all shows have a warning.
     * @param time  time to check for a warning
     * @return      true if there is a warning
     */
    public boolean hasWarningAt(int time) {
        if(warningTimes.contains(time)) return true;

        for(Show show : shows) {
            if(!show.hasWarningAt(time)) {
                return false;
            }
        }

        if(!warningTimes.contains(time)) {
            warningTimes.add(time);
        }

        return true;
    }

    /**
     * Gets the number of warnings the town has had
     * @return  number of town warnings
     */
    public int getTotalWarnings() {
        if(warningTimes.size() <= 1) return warningTimes.size();

        int warnings = 1;
        warningTimes.sort(Comparator.naturalOrder());

        for(int i = 1; i < warningTimes.size(); i++) {
            if(warningTimes.get(i-1) != warningTimes.get(i) - 1) {
                warnings++;
            }
        }

        return warnings;
    }

    /**
     * Gets the number of fireworks up in the sky
     * @return  number of fireworks currently up
     */
    public int getFireworksUp() {
        int fireworksUp = 0;

        for(Show show : shows) {
            fireworksUp += show.getFireworksUp();
        }

        return fireworksUp;
    }

    /**
     * Gets the cost of all shows in the town
     * @return  total cost of all shows
     */
    public double getTotalCost() {
        double totalCost = 0;

        for(Show show : shows) {
            totalCost += show.getCost();
        }

        return totalCost;
    }

    /**
     * Prints the status of all shows in town
     * @return  status string
     */
    public String toString() {
        String townString = "Town status:\n";

        for(Show show : shows) {
            townString += show + "\n";
        }

        return townString;
    }
}
