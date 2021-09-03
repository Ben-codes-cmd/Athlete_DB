// Ben Jordan
// 8/27/21
// Main menu class

import java.sql.*;

public class menu {
    /** Current selected athlete */
    private athlete current;

    /** Established JDBC */
    private Connection con = sqlMethods.connect();

    /** Volatile cache */
    private cache athleteCache = new cache();

    /** Option #1 - gather information to fill athlete fields. Execute an update on the database.*/
    private void newAthlete(){
        System.out.println("Please enter your first and last name below:");
        String fullname = utilities.title(utilities.input.nextLine());
        System.out.println("Maximum Bench Press:");
        int bench = utilities.getint();
        System.out.println("Maximum Squat:");
        int squat = utilities.getint();
        System.out.println("Maximum Deadlift:");
        int dead = utilities.getint();

        // create entry in database
        sqlMethods.newentry(con, fullname, bench, squat, dead);
    }

    /** Option #2 - Calls both fetch and build functions from sql_functions file to return an athlete object if found.*/
    private athlete findAthlete(){
        // find desired athlete profile
        while(true){
            System.out.println("Please input an athlete name below: (type 'Exit' to return to the menu)"); // work on exit
            String search = utilities.title(utilities.input.nextLine()); // user input
            if(search.equals("Exit")){
                return null; // exit command, return to menu
            }
            // check cache for athlete
            athlete local = athleteCache.query(search);
            if(local != null){
                return local;
            }
            // otherwise query database
            ResultSet rs = sqlMethods.fetch(con, search); // fetch record
            if (rs == null){
                return null; // db connection lost, return to menu
            }
            athlete new_athlete = sqlMethods.build_athlete(rs); // build object
            if(new_athlete == null){
                System.out.println("The athlete that you searched for was not found. Please try again or create a new athlete profile.");
            }
            else{
                return new_athlete;
            }
        }
    }
    /** Option #2 continued - Used to access attributes of the current athlete. Controls the execution of object methods*/
    private void athlete_menu(){
        while(true){
            System.out.println("\nSelected Athlete: " + current.getName() + "\n");
            System.out.println("1. View lift summary\n2. Update a lift\n3. Delete this athlete profile\n4. Exit Session");
            int selection = utilities.getint();
                switch(selection){
                    case 1:
                        current.checkMyLift();
                        break;
                    case 2:
                        current.updateLift(con);
                        break;
                    case 3:
                        if (current.deleteSelf(con)){
                            return;
                        }else{
                            break;
                        }
                    case 4:
                        // cache athlete before returning to main menu
                        if(athleteCache.checkExists(current.getName())){
                            athleteCache.addAthlete(current.getName(), current);
                        }
                        return;
                    default:
                        System.out.println("Please select an option 1-4.");
                }
        }
    }
    /** Option #3 - refreshes jdbc to allow dynamic updates to config.properties*/
    private void refresh_connection(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        con = sqlMethods.connect();
    }
    /** Primary program logic. Only method to be executed in main.*/
    void main_menu(){
        while(true){
        System.out.println("Welcome to the fitness tracking database.\n-----------------------------------------\nPlease select an option below\n1. Create a new athlete profile\n2. Search for an existing athlete profile\n3. Refresh Connection\n4. Exit");
            int selection = utilities.getint();
            switch(selection){
                case 1:
                    newAthlete();
                    break;
                case 2:
                    current = findAthlete();
                    if (current == null){
                        break;
                    }
                    athlete_menu();
                    break;
                case 3:
                    refresh_connection();
                    break;
                case 4:
                    System.out.println("Thank you for using the athlete tracking database. Goodbye.");
                    System.exit(0);
                default:
                    System.out.println("Please select an option 1-3.");
                    break;
            }
        }
    }
}
