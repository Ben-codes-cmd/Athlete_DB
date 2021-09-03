/* 
Ben Jordan
8/2/21
SQL interfacing methods
*/
import java.sql.*;

class sqlMethods {

    // CREATE ATHLETE

    /** Create a new entry given all fields as parameters. Input is expected to be properly formatted.*/
    static void newentry(Connection con, String full_name, int benchpress, int squat, int deadlift){

        // check for existing entry
        try(ResultSet names = fetch(con, full_name)){
            if (names.next()){
                System.out.println("The athlete that you are trying to create already exists. Creation failed.");
                return;
            } 
        }catch(SQLException e){}

        try {
            PreparedStatement stmt = con.prepareStatement("call newEntry(?, ?, ?, ?);");
            stmt.setString(1, full_name);
            stmt.setInt(2, benchpress);
            stmt.setInt(3, squat);
            stmt.setInt(4, deadlift);
            stmt.executeUpdate();
            stmt.close();
            System.out.println("Creation Successful!");

        } catch (SQLException e) { // ignore for now
            e.printStackTrace();
            System.out.println("Entry Unsuccessful. Please try again.");
        }
    }

    // FIND ATHLETE

    /** Query the database for an athlete profile. Returns a ResultSet object regardless
     * of whether record is present. SQLException returns null.
    */
    static ResultSet fetch(Connection con, String fullname){
            try {
                PreparedStatement stmt = con.prepareStatement("call fetchAthlete(?);");
                stmt.setString(1, fullname);
                ResultSet rs = stmt.executeQuery();
                return rs;

            } catch (SQLException e) {
                System.out.println("Connection with the database has been lost. Please refresh your connection.");
                return null;
            }
    }

    /** Immediately follows fetch function. Takes a ResultSet object and tests for empty set.
     * If a profile is present, a usable athlete object is built and returned.
    */
    static athlete build_athlete(ResultSet rs){
        athlete new_athlete = null;
        try{
            rs.next();
            String fullname = rs.getString("fullname");
            int benchpress = rs.getInt("benchpress");
            int squat = rs.getInt("squat");
            int deadlift = rs.getInt("deadlift");
            String[] dates = {rs.getDate("bench_date").toString(), rs.getDate("squat_date").toString(), rs.getDate("dl_date").toString()};
            new_athlete = new athlete(fullname, benchpress, squat, deadlift, dates);
            return new_athlete;
        }catch(Exception e){
            return new_athlete; // no record found
        }
    }

    // UPDATE ATHLETE

    /** Updates database one field per execution. Used in conjuction with Athlete 'update lift' method.*/
    static boolean update(Connection con, String name, int lift, int val){
        String[][] columns = {{"benchpress", "squat", "deadlift"}, {"bench_date", "squat_date", "dl_date"}};
        try{
            PreparedStatement stmt = con.prepareStatement("update athlete_list set " + columns[0][lift] + " = ?, " + columns[1][lift] + " = CURRENT_DATE() where fullname = ?;");
            stmt.setInt(1, val);
            stmt.setString(2, name);
            stmt.executeUpdate();
            stmt.close();
            return true;

        }catch(SQLException e){
            return false;
        }
    }
    
    // DELETE ATHLETE

    /** Deletes an athlete profile from the database. This action is permanent.*/
    static void delete(Connection con, String name){
        try {
            PreparedStatement stmt = con.prepareStatement("call deleteAthlete(?);");
            stmt.setString(1, name);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Deletion Unsuccessful. The database could not be contacted.");
        }
    }

    // ESTABLISH CONNECTION

    /** Will establish a connection with the database by reading fields from the config.properties file.
     * Does not terminate until connection is made.
    */
    static Connection connect(){
        configReader config = new configReader();
        String url = config.getUrl();
        String password = config.getPassword();
        String user = config.getUsername();
        while (true){
            for(int i = 1; i<6; i++){
                try{
                    Connection con = DriverManager.getConnection(url, user, password);
                    System.out.println("Successfully connected!");
                    return con; // must return valid connection to continue
                }
                catch(SQLException e){
                    // unable to connect
                    System.out.println("Failure to connect - " + "Attempt #" + i);
                    try{
                        Thread.sleep(2000);
                    }catch(InterruptedException ex){ // ignore
                        continue;
                    }
                }

            }
            System.out.println("Unable to connect! Please make sure that a mysql server is online @ '"+ url + "' and review configuration parameters. (Press enter to continue)");
            utilities.input.nextLine();
            // refresh config values before trying again
            config = new configReader();
            url = config.getUrl();
            password = config.getPassword();
            user = config.getUsername();
        }
    }

}
