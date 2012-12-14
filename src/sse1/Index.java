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
    String longest = "";
    Map<String, SortedSet<Document>> map;
       
    public Index(String keyword, Document doc){ 
        if(keyword.length() > longest.length()){
            longest = keyword;
        }

        SortedSet<Document> list = map.get(keyword);
        if(list==null){
            //TreeSet no permite elementos duplicados
            list = new TreeSet<Document>();
        }
        list.add(doc);

        map.put(keyword, list);
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

