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
import java.security.Key;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author Marta
 */
public class SSEIndex {
    
    static KeyGenerator ske1gen, ske2gen;
    static Cipher ske1;
    
    public static void main(String[] args) throws FileNotFoundException, IOException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, BadPaddingException {
        
        //1. Initialization
        int ctr = 1;
        int sizeBytes = 0;
        
        
        File f = new File("prueba1"); // Creamos un objeto file
        String path = f.getAbsolutePath(); // Llamamos al método que devuelve la ruta absoluta
        File f2 = new File("prueba2"); // Creamos un objeto file
        String path2 = f2.getAbsolutePath(); // Llamamos al método que devuelve la ruta absoluta 
        File f3 = new File("prueba1"); // Creamos un objeto file
        String path3 = f3.getAbsolutePath(); // Llamamos al método que devuelve la ruta absoluta

        PlainDocument doc = new PlainDocument("prueba1", path);
        PlainDocument doc1 = new PlainDocument("prueba2", path2);
        PlainDocument doc2 = new PlainDocument("prueba3", path3);
        
        
        
        Library lib = new Library();
        lib.addDocument(doc);
        lib.addDocument(doc1);
        lib.addDocument(doc2);
        
        Index index = new Index(lib);
        
        //2. Build array A
        Map<String, byte[]> array = new TreeMap<String, byte[]>(); 
        
        for (String keyword : index.getKeywords()){//build linked list Li with nodes Ni,j and store it in array A
            Key prevKey = ske1gen.generateKey();
            
            TreeSet<Document> docsKeyword = index.getDocuments(keyword);
            Document lastDoc = docsKeyword.pollLast();
            
            //Creating the list
            for(Document idDoc:docsKeyword){
                Key key = ske1gen.generateKey();
                
                byte[] node = createNode(idDoc.getId(), key, ctr+1);
                
                ske1.init(Cipher.ENCRYPT_MODE, prevKey, new IvParameterSpec(ske1.getIV()));
                byte[] cNode = ske1.doFinal(node);

                byte[] addr = getAddress(ctr);

                sizeBytes += cNode.length;
                
                array.put(Util.hexArray(addr), cNode);

                System.out.println("Node (bytes): " + Util.hexArray(node));
                System.out.print("A[" + Util.hexArray(addr) + "] = " + Util.hexArray(cNode));
                System.out.println();

                ctr++;

            }
            // Last node
            SecretKey key = ske1gen.generateKey();

            byte[] node = createNode(lastDoc.getId(), key, 0);

            ske1.init(Cipher.ENCRYPT_MODE, prevKey);//, new IvParameterSpec(ske1.getIV()));
            byte[] cNode = ske1.doFinal(node);
            byte[] addr = getAddress(ctr);

            sizeBytes += cNode.length;
            
            array.put(Util.hexArray(addr), cNode);

            System.out.println("Node (bytes): " + Util.hexArray(node));
            System.out.print("A[" + Util.hexArray(addr) + "] = " + Util.hexArray(cNode));
            System.out.println();

            ctr++;
        }
        
    }

    private static byte[] createNode(String idDoc, Key key, int i) {
        System.out.println("Node "+i+": "+idDoc+"\n\tKey: "+Util.hexArray(key.getEncoded()));

        int MAX_LENGTH_BYTES = 16;

        byte[] node = null;
        try {

            if (key != null) {

//                System.out.println(idDoc);

                byte[] a = idDoc.getBytes();
                byte[] b = key.getEncoded();//new SecretKeySpec(key.getEncoded(), "AES").getEncoded();


//                System.out.print("KEY: "+Util.hexArray(b));
//                System.out.println();

                // TODO: Random permutation
                byte[] c = getAddress(i);

                node = new byte[MAX_LENGTH_BYTES + b.length + c.length];
                System.arraycopy(a, 0, node, 0, a.length);
                System.arraycopy(b, 0, node, MAX_LENGTH_BYTES, b.length);
                System.arraycopy(c, 0, node, MAX_LENGTH_BYTES + b.length, c.length);


            }

        } finally {
            return node;
        }
    }

    private static byte[] getAddress(int i) {
        byte[] c = new byte[]{
            (byte) (i >>> 24), (byte) (i >>> 16), (byte) (i >>> 8), (byte) i};
        //TODO: Clave
        randomPermutation(c, 1234);
        return c;
    }

    private static void randomPermutation(byte[] array, long seed) {
        Random r = new Random(seed);
        int j;
        for (int i = array.length - 1; i >= 0; i--) {
            j = r.nextInt(i + 1);
            byte aux = array[j];
            array[j] = array[i];
            array[i] = aux;
        }
    }
}
