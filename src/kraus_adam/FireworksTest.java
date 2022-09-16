package kraus_adam;

public class FireworksTest {
    private static final String ERROR_MARK = ">>>>>>>>>>";

    public static void main(String[] args) {
        testSmallDefaultsShow();
        System.out.println();

        testCleanShow();
        System.out.println();

        testShowWithGlitches();
        System.out.println();

        testCompanyShowOldStuff();
        System.out.println();

        testCompanyShowOldGlitchyStuff();
        System.out.println();

        testCompanyShowNewStuff();
        System.out.println();

        testTinyTown();
        System.out.println();

        testTown();
        System.out.println();

        testCompanyTown();
        System.out.println();

        System.out.println("All tests finished.");

    }


    /**
     * Test creating a show with a single firework.
     */
    public static void testSmallDefaultsShow() {
        System.out.println("Tier 1: Testing a Show with one firework at a time.............................................");

        Show defaultshow = new Show(1);
        if (!defaultshow.getName().equals("test"))
            System.out.println(ERROR_MARK + " .getName() not working on default name");

        Show downtown = new Show("Downtown", 1);
        if (!downtown.getName().equals("Downtown"))
            System.out.println(ERROR_MARK + " .getName() not working");
        if (Math.abs(Show.WARNING_THRESHOLD - 80) > 0.01)
            System.out.println(ERROR_MARK + " Incorrect threshold for warnings.");
        if (downtown.hasWarning())
            System.out.println(ERROR_MARK + " 0 fireworks up should not have a warning.");
        downtown.addFirework(5);
        if (!downtown.hasWarning())
            System.out.println(ERROR_MARK + " Downtown with one firework up should have a warning.");
        if (downtown.getFireworksUp() != 1)
            System.out.println(ERROR_MARK + " Downtown should have one firework up.");
        if (!downtown.toString().equals("Status for Downtown show: 1 fireworks up (WARNING)"))
            System.out.println(ERROR_MARK + " Incorrect status: " + downtown);
        downtown.Update(400);
        if (downtown.getFireworksUp() != 0)
            System.out.println(ERROR_MARK + " Ended show should have no fireworks up.");
        if (downtown.getTotalWarnings() != 1)
            System.out.println(ERROR_MARK + " Wrong number of warnings.");
        if (downtown.getCost() != 20)
            System.out.println(ERROR_MARK + " There should be $20 cost for one firework.");
        if (!downtown.toString().equals("Status for Downtown show: 0 fireworks up (0%)"))
            System.out.println(ERROR_MARK + " Incorrect output string. Output:\n\t" +downtown);
        System.out.println(downtown);
    }

    public static void testCleanShow() {
        System.out.println("\nTier 2: Testing shows with multiple fireworks cleanly added...........................");
        Show show = new Show(5);
        show.addFirework(5, 15);
        show.addFirework(10, 10);
        show.addFirework(12, 8);
        if ( show.hasWarning() )
            System.out.println(ERROR_MARK + " Error: Company show should not have a warning at time 12");
        show.addFirework(15, 5);
        if ( !show.hasWarning() )
            System.out.println(ERROR_MARK + " Error: Company show should not have a warning at time 15");
        if ( show.getFireworksUp() != 4 )
            System.out.println(ERROR_MARK + " Error: Company show should have four fireworks at time 15");
        if (!show.toString().equals("Status for test show: 4 fireworks up (WARNING)"))
            System.out.println(ERROR_MARK + " Incorrect output string. Output:\n\t" +show);

        show.addFirework(15, 5);
        if ( show.getFireworksUp() != 5 )
            System.out.println(ERROR_MARK + " Error: Company show should have five fireworks at time 15");

        show.Update(23);
        if ( show.getFireworksUp() != 0 )
            System.out.println(ERROR_MARK + " Error: Company show should have zero fireworks at time 23");
        if ( show.getTotalWarnings() != 1 )
            System.out.println(ERROR_MARK + " Error: Company show should have had 1 warning 23");
        if ( show.hasWarning() )
            System.out.println(ERROR_MARK + " Error: Company show should not have a warning at time 23");

        if ( show.getTotalWarnings() != 1 )
            System.out.println(ERROR_MARK + " Error: Company show should have had 1 warning thus far");
        if ( show.getCost() != 100 )
            System.out.println(ERROR_MARK + " Error: Company show should have cost 100 by now");

        show.addFirework(35, 5, 40.5);
        show.addFirework(35, 4, 30.75);
        show.addFirework(35, 2);
        show.addFirework(36, 1);
        if ( show.getFireworksUp() != 4 )
            System.out.println(ERROR_MARK + " Error: Company show should have four fireworks at time 36");
        show.Update(38);
        if ( show.getFireworksUp() != 2 )
            System.out.println(ERROR_MARK + " Error: Company show should have two fireworks at time 38");
        show.Update(39);
        if ( show.getFireworksUp() != 2 )
            System.out.println(ERROR_MARK + " Error: Company show should have two fireworks at time 39");
        show.Update(40);
        if ( show.getFireworksUp() != 1 )
            System.out.println(ERROR_MARK + " Error: Company show should have one fireworks at time 40");

        if ( show.getTotalWarnings() != 2 )
            System.out.println(ERROR_MARK + " Error: Company show should have had 2 warnings");
        if (Math.abs(show.getCost() - 211.25) > 0.001 )
            System.out.println(ERROR_MARK + " Error: Company show should have cost 211.25 at the end");
        System.out.println(show);
    }

    public static void testShowWithGlitches() {
        System.out.println("\nTier 3: Testing a Glitchy show..................");
        Show show = new Show(6);

        show.addFirework(10);
        if (show.getFireworksUp() != 1)
            System.out.println(ERROR_MARK + " Error: expecting 1 firework at time 10.");
        if(show.addFirework(5))
            System.out.println(ERROR_MARK + " Error: should fail to add firework in earlier time.");
        if ( show.getFireworksUp() != 1 )
            System.out.println(ERROR_MARK + " Error: Invalid time, so no new firework should be added");

        // try update in past
        show.Update(5);
        if (show.getFireworksUp() != 1)
            System.out.println(ERROR_MARK + " Error: should ignore a backwards update.");
        show.Update(10);
        if (show.getFireworksUp() != 1)
            System.out.println(ERROR_MARK + " Error: removed a non-existent firework");

        // test bad duration and costs
        if(show.addFirework(50, -1))
            System.out.println(ERROR_MARK + " Error: negative duration is not possible");
        if(show.addFirework(50, 0))
            System.out.println(ERROR_MARK + " Error: 0 duration is not possible");
        if(show.addFirework(50, 2, -20))
            System.out.println(ERROR_MARK + " Error: negative duration is not possible");

        if(!show.addFirework(20, 2, 20))
            System.out.println(ERROR_MARK + " Error: failed adds should not update time, so a new firework should be added");

        show.addFirework(20, 2, 0);
        show.addFirework(20, 2, 1.25);
        show.addFirework(20);

        if (Math.abs(show.getCost() - 81.25) < 0.001 )
            System.out.println(ERROR_MARK + " Error: Glitchy show should have cost 81.25 at the end");

        if (!show.toString().equals("Status for test show: 4 fireworks up (66.7%)"))
            System.out.println(ERROR_MARK + " Incorrect output string. Output:\n\t" +show);
        
        System.out.println(show);
        
    }

    private static void testCompanyShowOldStuff() {
        System.out.println("\nTier 4: Testing company shows for items derived from parent...........................");

        String result = "Status for Basic Company show: 0 fireworks up (0%)\n" +
                "--UNKNOWN $100.00";

        CompanyShow companyShow = new CompanyShow(5);
        if ( !companyShow.getName().equals("company") )
            System.out.println(ERROR_MARK + " Default name for a company show is company");

        //recheck all the element from the parent still work
        companyShow = new CompanyShow("Basic Company", 5);
        if ( !companyShow.getName().equals("Basic Company") )
            System.out.println(ERROR_MARK + " Name for a company show is was not maintained");
        
        companyShow.addFirework(5, 15);
        companyShow.addFirework(10, 10);
        companyShow.addFirework(12, 8);
        if ( companyShow.hasWarning() )
            System.out.println(ERROR_MARK + " Error: Company show should not have a warning at time 12");
        companyShow.addFirework(15, 5);
        if ( !companyShow.hasWarning() )
            System.out.println(ERROR_MARK + " Error: Company show should not have a warning at time 15");
        if ( companyShow.getFireworksUp() != 4 )
            System.out.println(ERROR_MARK + " Error: Company show should have four fireworks at time 15");

        companyShow.addFirework(15, 5);
        if ( companyShow.getFireworksUp() != 5 )
            System.out.println(ERROR_MARK + " Error: Company show should have five fireworks at time 15");

        companyShow.Update(23);
        if ( companyShow.getFireworksUp() != 0 )
            System.out.println(ERROR_MARK + " Error: Company show should have zero fireworks at time 23");
        if ( companyShow.getTotalWarnings() != 1 )
            System.out.println(ERROR_MARK + " Error: Company show should have had 1 warning 23");
        if ( companyShow.hasWarning() )
            System.out.println(ERROR_MARK + " Error: Company show should not have a warning at time 23");

        if ( companyShow.getTotalWarnings() != 1 )
            System.out.println(ERROR_MARK + " Error: Company show should have had 1 warning thus far");
        if ( companyShow.getCost() != 95 )
            System.out.println(ERROR_MARK + " Error: Company show should have cost 95 by now due to the discount");
        if ( !companyShow.toString().equals(result) ) {
            System.out.println(ERROR_MARK + " Error: Company base bill should be 100.");
            showBeforeAfter(companyShow.toString(), result);
        }

        companyShow.addFirework(35, 5, 40.5);
        companyShow.addFirework(35, 4, 30.75);
        companyShow.addFirework(35, 2);
        companyShow.addFirework(36, 1);
        if ( companyShow.getFireworksUp() != 4 )
            System.out.println(ERROR_MARK + " Error: Company show should have four fireworks at time 36");
        companyShow.Update(38);
        if ( companyShow.getFireworksUp() != 2 )
            System.out.println(ERROR_MARK + " Error: Company show should have two fireworks at time 38");
        companyShow.Update(39);
        if ( companyShow.getFireworksUp() != 2 )
            System.out.println(ERROR_MARK + " Error: Company show should have two fireworks at time 39");
        companyShow.Update(40);
        if ( companyShow.getFireworksUp() != 1 )
            System.out.println(ERROR_MARK + " Error: Company show should have one fireworks at time 40");

        if ( companyShow.getTotalWarnings() != 2 )
            System.out.println(ERROR_MARK + " Error: Company show should have had 2 warnings");
        System.out.println(companyShow);
    }

    private static void testCompanyShowOldGlitchyStuff() {
        System.out.println("\nTier 5: Testing a Glitchy company show for items derived from parent..................");
        CompanyShow show = new CompanyShow(6);

        show.addFirework(10);
        if (show.getFireworksUp() != 1)
            System.out.println(ERROR_MARK + " Error: expecting 1 firework at time 10.");
        if(show.addFirework(5))
            System.out.println(ERROR_MARK + " Error: should fail to add firework in earlier time.");
        if ( show.getFireworksUp() != 1 )
            System.out.println(ERROR_MARK + " Error: Invalid time, so no new firework should be added");

        // try update in past
        show.Update(5);
        if (show.getFireworksUp() != 1)
            System.out.println(ERROR_MARK + " Error: should ignore a backwards update.");
        show.Update(10);
        if (show.getFireworksUp() != 1)
            System.out.println(ERROR_MARK + " Error: removed a non-existent firework");

        // test bad duration and costs
        if(show.addFirework(50, -1))
            System.out.println(ERROR_MARK + " Error: negative duration is not possible");
        if(show.addFirework(50, 0))
            System.out.println(ERROR_MARK + " Error: 0 duration is not possible");
        if(show.addFirework(50, 2, -20))
            System.out.println(ERROR_MARK + " Error: negative duration is not possible");

        if(!show.addFirework(20, 2, 20))
            System.out.println(ERROR_MARK + " Error: failed adds should not update time, so a new firework should be added");

        show.addFirework(20, 2, 0);
        show.addFirework(20, 2, 1.25);
        show.addFirework(20);

        System.out.println(show);
    }

    private static void testCompanyShowNewStuff() {
        System.out.println("\nTier 6: Testing a company show new elements..................");

        String result ="Status for company show: 5 fireworks up (41.7%)\n" +
                "--ABC Company $90.00\n" +
                "--Firework $60.50\n" +
                "--UNKNOWN $70.00";

        String result2 ="Status for company show: 3 fireworks up (25%)\n" +
                "--ABC Company $90.00\n" +
                "--Firework $126.00\n" +
                "--UNKNOWN $70.00";

        String result3 ="Status for company show: 2 fireworks up (16.7%)\n" +
                "--ABC Company $165.00\n" +
                "--Firework $126.00\n" +
                "--UNKNOWN $70.00";

        String result4 ="Status for company show: 9 fireworks up (75%)\n" +
                "--ABC Company $165.00\n" +
                "--Firework $126.00\n" +
                "--UNKNOWN $70.00\n" +
                "--Underdog $95.00";

        String result5 ="Status for company show: 10 fireworks up (WARNING)\n" +
                "--ABC Company $165.00\n" +
                "--Firework $126.00\n" +
                "--UNKNOWN $70.00\n" +
                "--Underdog $100.00";

        CompanyShow companyShow = new CompanyShow(12);

        //add companies
        companyShow.addFirework( 5);
        companyShow.addFirework( 5, 2);
        companyShow.addFirework(5, 6, 30);
        companyShow.addFirework("ABC Company", 5);
        companyShow.addFirework("ABC Company", 5, 2);
        companyShow.addFirework("ABC Company", 5, 10, 30);
        companyShow.addFirework("Firework", 5, 8, 30);
        companyShow.addFirework("Firework", 7, 5, 30.5);
        companyShow.addFirework("ABC Company", 10, 2, 20);

        //before discount
        if (!companyShow.toString().equals(result)) {
            System.out.println(ERROR_MARK + "Incorrect toString after initial building of company show");
            showBeforeAfter(companyShow.toString(), result);
        }
        if (Math.abs(companyShow.getCost() - 220.5) > 0.001 )
            System.out.println(ERROR_MARK + " Error: company show should have cost 220.5 before any discounts are available." +
                    " Instead, this was returned: " + companyShow.getCost());


        //check one company for a discount
        companyShow.addFirework("Firework", 15, 2, 15.5);
        companyShow.addFirework("Firework", 15, 5, 50);
        if (!companyShow.toString().equals(result2)) {
            System.out.println(ERROR_MARK + "Incorrect toString after adding two fireworks");
            showBeforeAfter(companyShow.toString(), result2);
        }
        if (Math.abs(companyShow.getCost() - 279.7) > 0.001 )
            System.out.println(ERROR_MARK + " Error: show should have cost 279 after Firework company give a discount" +
                    " Instead, this was returned: " + companyShow.getCost());

        companyShow.addFirework("ABC Company", 18, 2, 75);
        if (!companyShow.toString().equals(result3)) {
            System.out.println(ERROR_MARK + "Incorrect toString after adding a firework that results in a discount");
            showBeforeAfter(companyShow.toString(), result3);
        }
        if (Math.abs(companyShow.getCost() - 346.45) > 0.001 )
            System.out.println(ERROR_MARK + " Error: show should have cost 346.45 now that AND and Firework give discounts" +
                    " Instead, this was returned: " + companyShow.getCost());


        companyShow.addFirework("Underdog", 20, 2, 65);
        companyShow.addFirework("Underdog", 20, 2, 5);
        companyShow.addFirework("Underdog", 20, 2, 5);
        companyShow.addFirework("Underdog", 20, 2, 5);
        companyShow.addFirework("Underdog", 20, 2, 5);
        companyShow.addFirework("Underdog", 20, 2, 5);
        companyShow.addFirework("Underdog", 20, 2, 5);

        if (!companyShow.toString().equals(result4)) {
            System.out.println(ERROR_MARK + "Incorrect toString after a new firework company");
            showBeforeAfter(companyShow.toString(), result4);
        }
        if (Math.abs(companyShow.getCost() - 441.45) > 0.001 )
            System.out.println(ERROR_MARK + " Error: show should have cost 441.45 now that AND and Firework give discounts" +
                    " Instead, this was returned: " + companyShow.getCost());


        companyShow.addFirework("Underdog", 20, 2, 5);
        if (!companyShow.toString().equals(result5)) {
            System.out.println(ERROR_MARK + "Incorrect toString at end of company show");
            showBeforeAfter(companyShow.toString(), result5);
        }
        if (Math.abs(companyShow.getCost() - 441.45) > 0.001 )
            System.out.println(ERROR_MARK + " Error: show should have cost 441.45 at the end of the show" +
                    " Instead, this was returned: " + companyShow.getCost());

        System.out.println(companyShow);

    }

    public static void testTinyTown() {
        System.out.println("\nTier 7: Testing a Town with 3 tiny Shows......................................");
        String initalOutput = "Town status:\n" +
                "Status for red show: 0 fireworks up (0%)\n" +
                "Status for green show: 0 fireworks up (0%)\n" +
                "Status for blue show: 0 fireworks up (0%)\n";

        String finalOutput = "Town status:\n" +
                "Status for red show: 1 fireworks up (WARNING)\n" +
                "Status for green show: 1 fireworks up (WARNING)\n" +
                "Status for blue show: 1 fireworks up (50%)\n";

        Town ourTown = new Town();
        int redShow = ourTown.add(new Show("red", 1));
        int greenShow = ourTown.add(new Show("green", 1));
        int blueShow = ourTown.add(new Show("blue", 2));
        if (!ourTown.toString().equals(initalOutput))
            System.out.println(">>>>>>>>>>>>>> incorrect Town toString(). Got:\n" +ourTown);
        if (ourTown.getFireworksUp() != 0)
            System.out.println(ERROR_MARK + " Expected 0 fireworks up in tiny Town");
        ourTown.addFirework(greenShow, 5, 10);
        ourTown.addFirework(redShow, 9, 4);
        ourTown.addFirework(blueShow, 10, 6);
        ourTown.addFirework(blueShow, 12, 5);
        if (ourTown.getFireworksUp() != 4)
            System.out.println(ERROR_MARK + " Expected 4 fireworks up in tiny Town");
        if (!ourTown.hasWarning())
            System.out.println(ERROR_MARK + " Error in tiny Town at 12: should have a warning");
        ourTown.update( 15);
        if (ourTown.hasWarning())
            System.out.println(ERROR_MARK + " Error in tiny Town at 15: should have a warning.");
        if (ourTown.getFireworksUp() != 3)
            System.out.println(ERROR_MARK + " Expected 3 fireworks up in tiny own at time 15");
        ourTown.update(17);
        ourTown.addFirework(greenShow, 18);
        if (ourTown.hasWarning())
            System.out.println(ERROR_MARK + " Error in tiny Town at 18: should not have a warning.");
        if (ourTown.getTotalWarnings()!=1)
            System.out.println(ERROR_MARK + " Error in tiny Town at 18: should have 1 warning total.");

        ourTown.addFirework(greenShow, 20, 3);
        ourTown.addFirework(redShow, 20, 3);
        ourTown.addFirework(blueShow, 20, 3);
        ourTown.addFirework(blueShow, 20, 1);

        if (ourTown.getTotalWarnings()!=2)
            System.out.println(ERROR_MARK + " Error in tiny Town at 20: should have 2 warning total.");

        ourTown.update(22);
        if (ourTown.getTotalWarnings()!=2)
            System.out.println(ERROR_MARK + " Error in tiny Town at 22: should have 2 warning total.");
        if (ourTown.hasWarning())
            System.out.println(ERROR_MARK + " Error in tiny Town at 22: should not currently have a warning.");

        if (!ourTown.toString().equals(finalOutput)) {
            System.out.println(ERROR_MARK + " Final output of town show is incorrect");
            showBeforeAfter(ourTown.toString(), finalOutput);
        }
        System.out.println(ourTown);
    }

    public static void testTown() {
        System.out.println("Tier 8: Testing a Town with 3 normal Shows...................................");

        String result1 = "Town status:\n" +
                "Status for brown show: 7 fireworks up (70%)\n" +
                "Status for green show: 14 fireworks up (WARNING)\n" +
                "Status for yellow show: 7 fireworks up (58.3%)\n";

        String result2 = "Town status:\n" +
                "Status for brown show: 8 fireworks up (WARNING)\n" +
                "Status for green show: 14 fireworks up (WARNING)\n" +
                "Status for yellow show: 10 fireworks up (WARNING)\n";

        String result3 = "Town status:\n" +
                "Status for brown show: 8 fireworks up (WARNING)\n" +
                "Status for green show: 14 fireworks up (WARNING)\n" +
                "Status for yellow show: 12 fireworks up (WARNING)\n";

        String result4 = "Town status:\n" +
                "Status for brown show: 8 fireworks up (WARNING)\n" +
                "Status for green show: 12 fireworks up (WARNING)\n" +
                "Status for yellow show: 10 fireworks up (WARNING)\n";

        Town town = new Town();
        int brown = town.add(new Show("brown", 10));
        int green = town.add(new Show("green", 15));
        int yellow = town.add(new Show("yellow", 12));
        for (int i = 0; i < 7; i++) {
            town.addFirework(brown, i, 15);
            town.addFirework(green, i, 20);
            town.addFirework(green, i, 15);
            town.addFirework(yellow, i, 15);
            if (town.hasWarning())
                System.out.println(ERROR_MARK + " Error: town should not have a warning at time 7.");
        }
        if (town.getFireworksUp() != 28)
            System.out.println(ERROR_MARK + " Expected 28 fireworks up in the town");

        if (!result1.equals(town.toString())) {
            System.out.println(ERROR_MARK + " At time 7: toString is incorrect.");
            showBeforeAfter(town.toString(), result1);
        }

        town.addFirework(brown, 8, 3);
        town.addFirework(yellow, 8, 3);
        town.addFirework(yellow, 8, 3 );
        if (town.hasWarning())
            System.out.println(ERROR_MARK + " Error: town should not have warning at time 8.");
        town.addFirework(yellow, 9);
        if (!town.hasWarning())
            System.out.println(ERROR_MARK + " Error: town should have warning at time 9.");

        if (!result2.equals(town.toString())) {
            System.out.println(ERROR_MARK + " At time 8: toString is incorrect.");
            showBeforeAfter(town.toString(), result2);
        }

        Show yellowShow = town.getShow(yellow);
        town.addFirework(yellow, 9, 10);
        town.addFirework(yellow, 10, 10);
        town.addFirework(yellow, 10, 10);

        if (yellowShow.getFireworksUp() != 12)
            System.out.println(ERROR_MARK + " Expecting 12 fireworks up in yellow Show at time 10.");
        if (town.getFireworksUp() != 34)
            System.out.println(ERROR_MARK + " Expecting 34 fireworks up in town at time 10.");
        if (!town.hasWarning())
            System.out.println(ERROR_MARK + " Error: town should a warning at time 10.");
        if (town.getTotalWarnings() != 1)
            System.out.println(ERROR_MARK + " Expected 1 warning for the shows at this point");
        if (!result3.equals(town.toString())) {
            System.out.println(ERROR_MARK + " At time 10: toString is incorrect.");
            showBeforeAfter(town.toString(), result3);
        }

        town.update( 13);
        if (yellowShow.getFireworksUp() != 9)
            System.out.println(ERROR_MARK + " Expecting 9 fireworks up in yellow Show at time 13.");
        town.update(11);
        if (yellowShow.getFireworksUp() != 9)
            System.out.println(ERROR_MARK + " Sensor glitch. Removed non-existent firework.");
        if (town.getFireworksUp() != 30)
            System.out.println(ERROR_MARK + " Expected 30 fireworks up");

        town.addFirework(yellow, 14, 10);
        town.addFirework(yellow, 14, 10);
        town.addFirework(yellow, 14, 10);
        town.update( 14);
        town.addFirework(brown, 15);
        town.update( 16);
        town.addFirework(brown, 14);
        town.update( 17);
        town.addFirework(brown, 17);
        town.addFirework(brown, 17);
        town.addFirework(brown, 17);

        if (town.getTotalWarnings() != 3)
            System.out.println(ERROR_MARK + " Expected 3 warning for the shows at the end");

        if (!result4.equals(town.toString())) {
            System.out.println(ERROR_MARK + " At end of town show: toString is incorrect.");
            showBeforeAfter(town.toString(), result4);
        }

        System.out.println(town);
    }

    public static void testCompanyTown() {
        System.out.println("\nTier 10: Testing a Subway with 2 normal, and 3 paid Shows.......................");
        String result = "Town status:\n" +
                "Status for pink show: 2 fireworks up (WARNING)\n" +
                "Status for blue show: 3 fireworks up (WARNING)\n" +
                "Status for red company show: 2 fireworks up (WARNING)\n" +
                "--ABC $145.75\n" +
                "--UNKNOWN $20.00\n" +
                "Status for gray company show: 3 fireworks up (WARNING)\n" +
                "--ABC $20.00\n" +
                "--Big Burst $70.00\n" +
                "--UNKNOWN $20.00\n" +
                "--Underdog $20.75\n" +
                "Status for green company show: 4 fireworks up (WARNING)\n" +
                "--ABC $90.20\n" +
                "--Big Burst $20.00\n" +
                "--UNKNOWN $60.00\n" +
                "--Underdog $40.00\n";

        Town town = new Town();
        int pink = town.add(new Show("pink", 2));
        int blue = town.add(new Show("blue", 3));
        int red = town.add(new CompanyShow("red company", 2));
        int gray = town.add(new CompanyShow("gray company", 3));
        int green = town.add(new CompanyShow("green company", 4));

        //many entries
        town.addFirework(pink, 1, 15);
        town.addFirework(blue, 2, 16, 50);
        town.addFirework(red, 3, 5);
        town.addFirework(gray, 4, 5);
        town.addFirework(green, 5, 5);
        town.addFirework(blue, 6, 10);
        town.addFirework(blue, 7, 7);
        town.addFirework(blue, 7, 7);
        town.addFirework(blue, 7, 7);
        town.addFirework(pink, 8, 5);
        town.addFirework(red, "ABC", 9);
        town.addFirework(red, "ABC",10);
        town.addFirework(gray, "ABC",11, 5);
        town.addFirework(gray, "Big Burst",11, 50);
        town.addFirework(green, 11);
        town.addFirework(green, "ABC",11, 5, 40);
        town.addFirework(green, 11);
        town.addFirework(green, "Big Burst", 12, 75);
        town.addFirework(green, "Big Burst", 10, 75);
        town.addFirework(gray, 11);

        town.update( 13);

        if (town.getFireworksUp() != 9)
            System.out.println(ERROR_MARK + " Expected 9 fireworks up in town");
        if(town.getTotalWarnings()!=0)
            System.out.println(ERROR_MARK + " There should be no warnings yet! "
                    + town.getTotalWarnings() + " warnings.");
        if(Math.abs(town.getTotalCost()-370.0)>0.001)
            System.out.println(ERROR_MARK + " The total cost of should be XX at the moment. " +
                    "Got $" + town.getTotalCost());

        //toggle warnings
        town.addFirework(red, "ABC", 14, 4);
        town.addFirework(red, "ABC", 14, 10);
        town.addFirework(gray, "Underdog", 14, 6, 20.75);
        town.addFirework(green, "Underdog", 14, 7);
        town.addFirework(green, "Underdog", 14, 7);
        town.addFirework(green,  14, 7);
        town.addFirework(pink,  14, 10);

        town.update(15);
        town.addFirework(blue,  16, 4, 50);
        town.update(17);
        town.addFirework(pink,  18, 6);
        town.addFirework(blue,  18, 4, 65.5);
        town.addFirework(gray, "Big Burst",  18, 4, 50);
        town.addFirework(green,  "ABC", 18, 4, 50.2);
        town.update(19);
        town.addFirework(blue,  20, 20, 100);
        town.addFirework(red,  "ABC", 20, 4, 65.75);

        if(town.getTotalWarnings()!=4)
            System.out.println(ERROR_MARK + " At end of day, there should have been 4 warnings. There were "
                    + town.getTotalWarnings() + " warnings.");

        if((Math.abs(town.getTotalCost()- 884.9124999999999) > 0.001 ))
        {
                System.out.println(ERROR_MARK + " At end of day, the total cost should have been $884.91 Instead, $"
                        + town.getTotalCost() + " was billed.");
        }

        if (!town.toString().equals(result)) {
            System.out.println(ERROR_MARK + " Incorrect final string output");
            showBeforeAfter(town.toString(), result);
        }

        System.out.println(town);

    }


    private static void showBeforeAfter(String companyShow, String result) {
        
        String indent = "    ";

        System.out.println("...................................................");
        System.out.println(" Got this (indented added for readability:");
        System.out.println(indent + companyShow.replace("\n", "\n"+indent));
        System.out.println("\nNeeded this:");
        System.out.println(indent + result.replace("\n", "\n"+indent));
        System.out.println("..................................................");

    }


}
