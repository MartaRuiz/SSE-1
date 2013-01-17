/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sse1;

import java.io.*;

/**
 *
 * @author Marta
 */
public class PlainDocument implements Document {
    String id;
    String path;
    
    public PlainDocument(String identifier, String route){
        id = identifier;
        path = route;
    }
    
    public String getId(){
      
        return id;
    }
    
    public String getPath(){
        return path;
    }

    public boolean find(String keyword){
        boolean ok = false;
        String toLowerCase = keyword.toLowerCase();
       
        try {
            
            BufferedReader  bufferedReader = new BufferedReader (new FileReader (id + ".txt"));
            //Buscar si existe una palabra
            String  line;
            while((line = bufferedReader.readLine())!=null && !ok){
                    if(line.toLowerCase().indexOf(toLowerCase)!= -1){
                           ok = true;
                    }
            }
            
        }catch(FileNotFoundException e) {
        }catch(IOException e) {
        }
        
        return ok;
    }
}
