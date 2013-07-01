/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sse1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author Marta
 */
public class Index {

    Map<String, Set<Document>> map;
    
    public Index(Library lib, String[] keywords){ 
        map = new HashMap<String, Set<Document>>();
          for(String str:keywords){
              for(PlainDocument doc : lib.documentList){
                  if(doc.find(str)){
                      if(!map.containsKey(str)){
                          Set set = new HashSet<PlainDocument>();
                          set.add(doc);
                          map.put(str, set);
                      }
                      else{
                          map.get(str).add(doc);
                      }
                  }
              }
          }
    }
   
    public Index(Library lib) throws FileNotFoundException, IOException{
        try{
            map = new HashMap<String, Set<Document>>();
            for(PlainDocument doc : lib.documentList){
                BufferedReader  bufferedReader = new BufferedReader (new FileReader (doc.id + ".txt"));
                //Buscar si existe una palabra
                String  line;
                while((line = bufferedReader.readLine())!=null){
                    StringTokenizer st = new StringTokenizer(line.toLowerCase(), ". \t,:;?!¡¿-·<>«»");
                    while(st.hasMoreTokens()){
                        String token = st.nextToken();
                        if(!map.containsKey(token)){
                                  Set set = new HashSet<PlainDocument>();
                                  set.add(doc);
                                  map.put(token, set);
                              }
                              else{
                                  map.get(token).add(doc);
                              }
                        } 
                }
            }
        }
        
        
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getKeywords(){
        return new ArrayList<String>(map.keySet());
    }

    public TreeSet<Document> getDocuments(String keyword){
        return new TreeSet<Document>(map.get(keyword));
    }

    public Map<String, Set<Document>> getMap(){
        return map;
    }

    public int numberOfKeywords(){
        return map.size();
    }
   
}

