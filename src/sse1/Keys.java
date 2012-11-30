/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sse1;

import java.io.*;
import java.security.*;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Marta
 */
public class Keys {
   
   static SecureRandom sr = new SecureRandom();
   /*static KeyGenerator ske1gen, ske2gen;
   static Cipher ske1, ske2, randF;*/
   
    public static Key generateKey(int bits) {
        byte[] b = new byte[bits / 8];
        sr.nextBytes(b);
        return new SecretKeySpec(b, "UNIFORM");
        
        //SecretKeySpec construye clave secreta a partir de un array de bytes
        //UNIFORM clase que trabaja distribuciones uniformes
    }
    
   public static byte[] int2byte(int[] values) throws IOException
   {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        for(int i=0; i < values.length; ++i){
             dos.writeInt(values[i]);
        }

        return baos.toByteArray();
   }
    
   public static Key generateKey2(int l) throws IOException{
        //Cada 1 (int) ocupa 32 bits en java
        int b = l/32;
        int [] unos = new int[b];
        for(int i=0;i<b;i++){
            unos[i] = 1;
        }
        byte [] aB = int2byte(unos);

        return new SecretKeySpec(aB, "UNIFORM");
    }

    public static Key[] Gen(int k, int l) throws Exception {

        // Initialize key generatos

        Key[] K = new Key[4];

        K[0] = generateKey(k);
        K[1] = generateKey(k);
        K[2] = generateKey(k);
        K[3] = generateKey2(l);
        
        return K;
    }
    
}
