/**
 * Adam Kraus
 *
 * This file is to store information about a fireworks show where fireworks are produced by given companies
 */
package kraus_adam;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Class to store information about a fireworks show with fireworks produced by companies.
 */
public class CompanyShow extends Show {
    /**
     * Default company show name
     */
    public final static String DEFAULT_COMPANY_SHOW_NAME = "company";
    /**
     * Default name for a firework making company
     */
    public final static String DEFAULT_COMPANY_NAME = "UNKNOWN";

    private HashMap<String, Double> companyBills;

    /**
     * CompanyShow constructor
     * @param name          name of the show
     * @param maxFireworks  max fireworks allowed up at a given time
     */
    public CompanyShow(String name, int maxFireworks) {
        super(name, maxFireworks);
        companyBills = new HashMap<String, Double>();
    }

    /**
     * CompanyShow constructor
     * @param maxFireworks  max fireworks allowed up at a given time
     */
    public CompanyShow(int maxFireworks) {
        this(DEFAULT_COMPANY_SHOW_NAME, maxFireworks);
    }

    /**
     * Adds firework to show
     * @param companyName   name of company that produced firework
     * @param time          time that firework launches
     * @param duration      length of time that firework is in the air
     * @param cost          cost of firework
     * @return              true if successfully added
     */
    public boolean addFirework(String companyName, int time, int duration, double cost) {
        if(duration < 1 || cost < 0) return false;

        if(super.addFirework(time, duration, cost)) {
            if(companyBills.containsKey(companyName)) {
                companyBills.put(companyName, companyBills.get(companyName) + cost);
            } else {
                companyBills.put(companyName, cost);
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * Adds firework to show
     * @param companyName   name of company that produced firework
     * @param time          time that firework launches
     * @param duration      length of time that firework is in the air
     * @return              true if successfully added
     */
    public boolean addFirework(String companyName, int time, int duration) {
        if(duration < 1) return false;

        return addFirework(companyName, time, duration, Firework.DEFAULT_COST);
    }

    /**
     * Adds firework to show
     * @param companyName   name of company that produced firework
     * @param time          time that firework launches
     * @return              true if successfully added
     */
    public boolean addFirework(String companyName, int time) {
        return addFirework(companyName, time, Firework.DEFAULT_DURATION, Firework.DEFAULT_COST);
    }

    /**
     * Adds firework to show
     * @param time          time that firework launches
     * @param duration      length of time that firework is in the air
     * @param cost          cost of firework
     * @return              true if successfully added
     */
    @Override
    public boolean addFirework(int time, int duration, double cost) {
        if(duration < 1 || cost < 0) return false;

        return addFirework(DEFAULT_COMPANY_NAME, time, duration, cost);
    }

    /**
     * Adds firework to show
     * @param time          time that firework launches
     * @param duration      length of time that firework is in the air
     * @return              true if successfully added
     */
    @Override
    public boolean addFirework(int time, int duration) {
        if(duration < 1) return false;

        return addFirework(DEFAULT_COMPANY_NAME, time, duration, Firework.DEFAULT_COST);
    }

    /**
     * Adds firework to show
     * @param time          time that firework launches
     * @return              true if successfully added
     */
    @Override
    public boolean addFirework(int time) {
        return addFirework(DEFAULT_COMPANY_NAME, time, Firework.DEFAULT_DURATION, Firework.DEFAULT_COST);
    }

    /**
     * Gets the cost of all the fireworks in the show.
     * If the bill for a company is $100 or more, a 5% discount is applied.
     * @return  total cost of fireworks
     */
    @Override
    public double getCost() {
        double totalCost = 0;

        for(double cost : companyBills.values()) {
            if(cost >= 100) {
                totalCost += cost * .95;
            } else {
                totalCost += cost;
            }
        }

        return totalCost;
    }

    /**
     * Prints the show status, which includes the show name, fireworks up, and percent of max fireworks.
     * If the percent of max fireworks is above the warning threshold, WARNING is printed instead of the percent.
     * It also prints the total cost of fireworks for each company with no discount applied.
     */
    @Override
    public String toString() {
        String showString = super.toString();

        for(String key : companyBills.keySet()) {
            DecimalFormat format = new DecimalFormat("##.00");
            showString += "\n--" + key + " $" + format.format(companyBills.get(key));
        }

        return showString;
    }
}
