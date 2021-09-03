/*
Ben Jordan
7/8/21
cache class
*/
import java.util.HashMap;
public class cache{

     private HashMap<String, athlete> localCache = new HashMap<String, athlete>(); // store athlete objects (hashmap)
     
     /** Checks cache for previously accessed athlete objects.*/
     public athlete query(String name){
          if (checkExists(name)){
               return localCache.get(name);
          }
          return null;
     }

     /** Checks if athlete exists in cache */
     public Boolean checkExists(String name){
          return localCache.containsKey(name);
     }

     /** Used to access cache object. Adds key/value pair. */
     public void addAthlete(String name, athlete obj){
          localCache.put(name, obj);
     } 
}
