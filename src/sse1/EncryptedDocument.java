/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sse1;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author Marta
 */
public class EncryptedDocument implements Document{

   private String id;
   private String path;
   
   private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B',
			'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };
   
   private Key key;
   
   public EncryptedDocument(String identifier, String route) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException{
       id = identifier;
       path = route;
 
       key = new SecretKeySpec(keyValue, "AES");

   }
   
       public String getId(){
      
        return id;
    }
    
    public String getPath(){
        return path;
    }
    
    public void encryptFile(PlainDocument doc){
        try {
                BufferedReader br = new BufferedReader(new FileReader(doc.getId() + ".txt"));
                StringBuilder sb = new StringBuilder();

                File file = new File(doc.getId() + "-cipher.txt");

                // if file doesnt exists, then create it
                if (!file.exists()) {
                        file.createNewFile();
                }
                
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);

                String line = br.readLine();

                while (line != null) {
                        sb.append(line);
                        sb.append("\n");
                        line = br.readLine();

                }
                
                String everything = sb.toString();
                everything = encrypt(everything);
                
                bw.write(everything);
                bw.close();
                br.close();
                System.out.println("Archivo Encriptado");
                
            } 
            catch (FileNotFoundException e) {
                    e.printStackTrace();
            } 
            catch (Exception e) {
                    e.printStackTrace();
            }
    }
    
    	private String encrypt(String Data) throws Exception {
            
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(Data.getBytes());
            String encryptedValue = Base64.encode(encVal);
            // String encryptedValue = new BASE64Encoder().encode(encVal);
            return encryptedValue;
	}
    
    public void decryptFile(EncryptedDocument doc){
    
       try {
            BufferedReader br = new BufferedReader(new FileReader(doc.getId()+".txt"));
            StringBuilder sb = new StringBuilder();

            File file = new File(doc.getId()+"-original.txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                    file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            String line = br.readLine();

            while (line != null) {
                    sb.append(line);
                    sb.append("\n");
                    line = br.readLine();

            }
            String everything = sb.toString();
            everything = decrypt(everything);
            bw.write(everything);
            bw.close();
            br.close();
            System.out.println("Archivo Desencriptado");
            
            } catch (FileNotFoundException e) {
                    e.printStackTrace();
            } catch (Exception e) {
                    e.printStackTrace();
            }
    }

    
    private String decrypt(String encryptedData) throws Exception {
		
		Cipher c = Cipher.getInstance("AES");
                c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = Base64.decode(encryptedData);
		// byte[] decordedValue = new
		// BASE64Decoder().decodeBuffer(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;        
    }  
    
    public String toString(){
        return (getId());
    }
}
