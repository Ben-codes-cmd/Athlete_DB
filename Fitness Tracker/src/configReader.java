// Ben Jordan
// 8/13/21
// Configuration file reader class

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;

public class configReader {
    /** Properties object stores property key/value pairs */
    Properties properties;

    /** reads inputs from properties file*/
    configReader(){
        properties = new Properties();
        try(FileInputStream reader = new FileInputStream("config\\config.properties")){
            properties.load(reader);
            reader.close();
        }catch(FileNotFoundException e){
            throw new RuntimeException("The config file was not found.");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    /** Retrieves url field from config.properties */
    public String getUrl(){
        String url = properties.getProperty("url");
        if(url != null) return url;
        else{
            throw new RuntimeException("Please fill in the url field in the config.properties file.");
        }
    }
    /** Retrieves username field from config.properties */
    public String getUsername(){
        String username = properties.getProperty("username");
        if(username != null) return username;
        else{
            throw new RuntimeException("Please fill in the username field in the config.properties file.");
        }
    }
    /** Retrieves password field from config.properties */
    public String getPassword(){
        String password = properties.getProperty("password");
        if(password != null) return password;
        else{
            throw new RuntimeException("Please fill in the password field in the config.properties file.");
        }
    }
}
