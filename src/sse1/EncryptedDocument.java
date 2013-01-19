/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sse1;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Marta
 */
public class EncryptedDocument implements Document{

   String id;
   String path;
   SecretKeySpec skey;
   Cipher cipher;
   byte[] r = new byte[] {0x0,0x1,0x2,0x3,0x4,0x5,0x6,0x7,0x8,0x9,0xA,0xB,0xC,0xD,0xE,0xF };
   byte[] iv = new byte[] {0x0,0x1,0x2,0x3,0x4,0x5,0x6,0x7,0x8,0x9,0xA,0xB,0xC,0xD,0xE,0xF };


   public EncryptedDocument(String identifier, String route) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException{
       id = identifier;
       path = route;
       skey = new SecretKeySpec(r, "AES");
       IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
       
        // Usa la clase engine para obtener una implementación del AES
       cipher = Cipher.getInstance("AES/OFB/PKCS5Padding");

        //cifra en un solo paso
        cipher.init(Cipher.ENCRYPT_MODE, skey,ivParameterSpec);
   }
   
       public String getId(){
      
        return id;
    }
    
    public String getPath(){
        return path;
    }
    
    public void cipher(PlainDocument doc){
        try{
           
           
            byte [] encryptedData;
  
            // Se abre el fichero original para lectura
            File f = new File(doc.getId() + ".txt"); 
            byte [] plainData = new byte[(int)f.length()] ;
            
            FileInputStream in = new FileInputStream(f);
            BufferedInputStream bufferedInput = new BufferedInputStream(in);
			
            // Se abre el fichero donde se hará la copia
            FileOutputStream out = new FileOutputStream (doc.getId() +"-cipher.txt");
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(out);
            
            int leidos = bufferedInput.read(plainData);
            
            while (leidos > 0)
            {
                    encryptedData = cipher.doFinal(plainData);
                    bufferedOutput.write(encryptedData,0,leidos);
                    leidos=bufferedInput.read(plainData);
            }

            // Cierre de los ficheros
            bufferedInput.close();
            bufferedOutput.close();

        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(IllegalBlockSizeException e){
            e.printStackTrace();
        }
        catch(BadPaddingException e){
            e.printStackTrace();
        }
        
    }
}
