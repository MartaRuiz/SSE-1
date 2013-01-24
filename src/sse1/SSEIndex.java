/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sse1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Marta
 */
public class SSEIndex {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        File f = new File("prueba1"); // Creamos un objeto file
        String path = f.getAbsolutePath(); // Llamamos al método que devuelve la ruta absoluta

            PlainDocument doc = new PlainDocument("prueba1", path);
            PlainDocument doc1 = new PlainDocument("prueba2", path);
            PlainDocument doc2 = new PlainDocument("prueba3", path);
            /*boolean esta = doc.find("kajshdka");
            System.out.println(esta);*/
            
            /*EncryptedDocument edoc = new EncryptedDocument("prueba1", path);
            edoc.cipher(doc);*/
            
            Library lib = new Library();
            lib.addDocument(doc);
            lib.addDocument(doc1);
            lib.addDocument(doc2);
            
           /* for(PlainDocument d : lib.documentList){
                System.out.println(d.getId());
            }*/
            
           /* String[] keywords = {"el", "kajshd", "coronel", "con"};
            Index index = new Index(lib, keywords);
            System.out.println(index.map.toString());*/
            
            /*Index index = new Index(lib);
            System.out.println(index.map.toString());*/

            String[] keywords = {"demostración", "demostracion"};
            Index index = new Index(lib, keywords);
            System.out.println(index.map.toString());
    }
}
