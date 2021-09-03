/* 
Ben Jordan
7/12/2021
Utility Functions
*/
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class utilities{

    /** Universal Scanner used to take user input*/
    static Scanner input = new Scanner(System.in); 
    
    /** A boolean verification method to be used in the context of a yes/no question.*/
    static boolean yn(){
        String choice;
        while(true){
            choice = input.nextLine().toLowerCase();
            if (choice.equals("y") || choice.equals("yes")){
                return true;
            }else if(choice.equals("n") || choice.equals("no")){
                return false;
            }else{
   
                System.out.println("Please enter either Y or N.");
            }
        } // end while
        
    }

    /** Gets the current date formatted similarly to those stored in SQL*/
    static String getDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd"); // create new formatter
        LocalDateTime current = LocalDateTime.now(); // get the current date
        return formatter.format(current); // format and return 
    }

    /** Primary function for user input control. Provides consistency for athlete naming by returning
     * input in conventional title casing.
    */
    static String title(String name){
        StringBuilder formatted = new StringBuilder();
        boolean convert = true;
        for(Character letter: name.toCharArray()){
            if(letter.equals(' ') || letter.equals('-')){
                if(convert == true){ // remove extra separators in the middle
                    continue;
                }
                convert = true;
            }else if(convert == true){
                letter = Character.toTitleCase(letter);
                convert = false;
            }else{
                letter = Character.toLowerCase(letter);
            }
            formatted.append(letter);
        }
        return formatted.toString().trim();
    }

    /** Handles exceptions for improper user input. To be used whenever integer values are required.*/
    static int getint(){ // finish this + implement 
        int num = 0;
        boolean valid = false;
        while(!valid){
            try{
                num = input.nextInt();
                input.nextLine(); // prepare scanner for next input
                valid = true;
            }catch(Exception e){
                input.nextLine(); // use up token to prevent infinite loop
                System.out.println("Please input an integer value.");
            }
        }
        return num;
    }
}
