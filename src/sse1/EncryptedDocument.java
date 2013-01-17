/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sse1;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author Marta
 */
public class EncryptedDocument implements Document{

   String id;
   String path;
   SecretKeySpec skey;

   public EncryptedDocument(String identifier, String route, SecretKeySpec secretkey ){
       id = identifier;
       path = route;
       skey = secretkey;
   }
   
       public String getId(){
      
        return id;
    }
    
    public String getPath(){
        return path;
    }
    
    
}
