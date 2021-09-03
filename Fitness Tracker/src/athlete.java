/* 
Ben Jordan
7/7/21
athlete class
*/
import java.sql.*;

class athlete {
    private String fullname;
    private int benchpress;
    private int squat;
    private int deadlift;
    private String[] dates = new String[3];


    athlete(String fname, int bench, int sq, int dl, String[] datelist){
        fullname = utilities.title(fname);
        benchpress = bench;
        squat = sq;
        dates = datelist;
        deadlift = dl;
    }
    // sets and gets
    void setName(String name){
        this.fullname = name;
    }
    String getName(){
        return this.fullname;
    }
    void setBench(int weight){
        this.benchpress = weight;
    }
    int getBench(){
        return this.benchpress;
    }
    void setSquat(int weight){
        this.squat = weight;
    }
    int getSquat(){
        return this.squat;
    }
    void setDeadlift(int weight){
        this.deadlift = weight;
    }
    int getDeadlift(){
        return this.deadlift;
    }
    void setDate(int index, String val){
        this.dates[index] = val;
    }
    String[] getDates(){
        return this.dates;
    }

    /** Outputs a summary of most recent lifts */
    void checkMyLift(){
        // return a summary
        System.out.println("\nAthlete Name: " + fullname + "\nMaximum Bench Press: " + benchpress +  "lbs." + " (" + dates[0] + ") " + "\nMaximum Squat: " + squat + "lbs." + " (" + dates[1] + ") " + "\nMaximum Deadlift: " + deadlift + "lbs." + " (" + dates[2] + ") " + "\n"); // clean this up
    }
    /** updates local object and db */
    void updateLift(Connection con){
        String[] lifts = {"bench press", "squat", "deadlift"};
        System.out.println("Which lift would you like to update?\n1. " + lifts[0] + "\n2. " + lifts[1] + "\n3. " + lifts[2]);
        int selection;
        while(true){ 
            selection = utilities.getint() - 1;
            if(selection >= (-1) && selection < 3){
                break;
            }else{
                System.out.println("Please select an option 1-3.");
            }
        }//end while
        // gather new lift info
        System.out.println("Please enter the updated " + lifts[selection] + " (do not include lbs):");
        int newval = utilities.getint();
        System.out.println("Are you sure that you would like to update your " + lifts[selection] + " to " + newval + "lbs?[Y/N]");
        if (utilities.yn()){
            // update db
            if(sqlMethods.update(con, fullname, selection, newval)){
                // reassign object attribute
                switch(selection){
                    case 0:
                        benchpress = newval;
                        break;
                    case 1:
                        squat = newval;
                        break;
                    case 2:
                        deadlift = newval;
                        break;
                }
            dates[selection] = utilities.getDate();//update the date that the lift was performed
            System.out.println("Your " + lifts[selection] + " has been successfully updated!");
            }else{
                System.out.println("Update unsuccessful. The database could not be contacted."); // refresh connection option?
            }
        }    
    }
    /** Removes the athlete record from the db */
    boolean deleteSelf(Connection con){
        boolean delete;
        System.out.println("Are you sure that you would like to delete this profile?[Y/N]");
        if(utilities.yn()){
            // delete record
            sqlMethods.delete(con, this.getName());
            System.out.println("The athlete profile has been successfully deleted.");
            delete = true;
        }else{
            delete = false;
        }
        return delete;
    }
}