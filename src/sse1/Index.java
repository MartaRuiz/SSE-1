/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sse1;

import java.util.*;

/**
 *
 * @author Marta
 */
public class Index {

    Map<String, SortedSet<Document>> map;
    
    public Index(Library lib, String[] keywords){ 
          for(String str:keywords){
              
          }
    }
    
    public Index(Document doc){
        
    }

    public List<String> getKeywords(){
        return new ArrayList<String>(map.keySet());
    }

    public TreeSet<Document> getDocuments(String keyword){
        return new TreeSet<Document>(map.get(keyword));
    }

    public Map<String, SortedSet<Document>> getMap(){
        return map;
    }

    public int numberOfKeywords(){
        return map.size();
    }
}

