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
public class Library {
    ArrayList<PlainDocument> documentList;
    
    public Library(){
        documentList = new ArrayList<PlainDocument>();
        
    }
    
     public void addDocument(PlainDocument doc){
         
        if (!documentList.contains(doc)){
            documentList.add(doc);
        } 
        else{
            System.out.println("El documento ya estaba");
        }
    }
}
