/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sse1;

import java.io.*;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;



/**
 *
 * @author Marta
 */
public class Keys {
   
   static SecureRandom sr = new SecureRandom();
   static KeyGenerator ske1gen, ske2gen;
   static Cipher ske1, ske2, randF;

    public static Key generateKey(int bits) {
        byte[] b = new byte[bits / 8];
        sr.nextBytes(b);
        return new SecretKeySpec(b, "UNIFORM");
        
        //SecretKeySpec construye clave secreta a partir de un array de bytes
        //UNIFORM clase que trabaja distribuciones uniformes
    }
    
    public static Key generateKey2(int l){
        int b = l/8;
        byte[] aB = new byte[b];
        for(int i=0;i<b;i++){
            aB[i]=1;
        }
        return new SecretKeySpec(aB, "UNIFORM");
    }

    public static Key[] Gen(int k, int l) throws Exception {

        // Initialize key generatos
        
        // "AES" algoritmo de encriptacion
       /* ske1gen = KeyGenerator.getInstance("AES");
        ske1gen.init(k);

        ske2gen = KeyGenerator.getInstance("AES");
        ske2gen.init(k);*/

        Key[] K = new Key[4];

        K[0] = generateKey(k);
        //System.out.println(Arrays.toString(K[0].getEncoded()));

        K[1] = generateKey(k);
        K[2] = generateKey(k);
        K[3] = generateKey2(l);
        
        //generateKey() genera una clave secreta
        

        /*ske1 = Cipher.getInstance("AES/CTR/NoPadding");
        ske2 = Cipher.getInstance("AES/CTR/NoPadding");
        ske2.init(Cipher.ENCRYPT_MODE, K[4]);

        randF = Cipher.getInstance("AES/CTR/NoPadding");*/

        return K;
    }
}
