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
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Marta
 */
public class SSEIndex {
    
    static KeyGenerator ske1gen, ske2gen;
    static Cipher ske1, randF, ske2;
    
    public static void main(String[] args) throws FileNotFoundException, IOException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, BadPaddingException, Exception {
        
        //1. Initialization
        int ctr = 1;
        int sizeBytes = 0;
        int l= 128;
        int k = 128;
        
        
        ske1gen = KeyGenerator.getInstance("AES");
        ske1gen.init(k);
        
        ske1 = Cipher.getInstance("AES/CTR/NoPadding");
        
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
        
        Keys K = new Keys();
        Key[] aKeys = K.gen(k, l);
        
        byte[][] tw = null;
        
        //2. Build array A
        Map<String, byte[]> array = new TreeMap<String, byte[]>(); 
        Map<String, byte[]> table = new TreeMap<String, byte[]>();
        
        for (String keyword : index.getKeywords()){//build linked list Li with nodes Ni,j and store it in array A
            System.out.println("#####################");
            System.out.println(keyword);
            System.out.println("#####################");
            
            byte[] firstAddress = getAddress(ctr);
            
            Key prevKey = ske1gen.generateKey(); 
            
            
            byte[] entry = tableEntry(keyword,aKeys[1],firstAddress, prevKey);
            table.put(Util.hexArray(getAddress(keyword)), entry);
            
            TreeSet<Document> docsKeyword = index.getDocuments(keyword);
            
            //Creating the list
            for(Document idDoc:docsKeyword){
                SecretKey key = ske1gen.generateKey();
                
                byte[] node = createNode(idDoc.getId(), key, ctr+1);
                
                ske1.init(Cipher.ENCRYPT_MODE, prevKey);
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
            Document lastDoc = docsKeyword.pollLast();
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
        System.out.println("Num keywords: "+index.numberOfKeywords());
        System.out.println("Num nodos del indice: " + ctr);
        System.out.println("Size array KBytes: " + (sizeBytes/1024));
        
        tw = trapdoor("la",aKeys[2], aKeys[1]);
        search(array, table, tw);
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
    
    public static byte[] getAddress(String w) {

        byte[] c = Arrays.copyOf(w.getBytes(), 32);
        //TODO: Clave
        randomPermutation(c, 4321);
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

    private static byte[] tableEntry(String w_i, Key y, byte[] addr, Key initKey) throws Exception {
        // Pasamos la clave a un array de bytes
        byte[] k_i_0 = initKey.getEncoded();

        // Concatenación:
        // -    Inicializamos value: Array de tamaño addr + k_i_0
        byte[] primeraMitadValue = new byte[addr.length + k_i_0.length];
        
        // -    value[0,addr.length] = addr[0,addr.length]
        System.arraycopy(addr, 0, primeraMitadValue, 0, addr.length);
        // -    value[addr.length, addr.length+k_i_0.length] = k_i_0[0,k_i_0.length]
        System.arraycopy(k_i_0, 0, primeraMitadValue, addr.length, k_i_0.length);


        // Calcular f_y(wi):
        // -    Pasar w_i a bytes
        byte[] w_i_bytes = w_i.getBytes();

        // -    ?
        byte[] keyword = Arrays.copyOf(w_i_bytes, primeraMitadValue.length);

        // -    Funcion aleatorio f con clave y
        byte[] f_w_i = funcionPseudoAleatoria(y, keyword);

        // value = primeraMitadValue XOR f_w_i
        byte[] value = new byte[addr.length + k_i_0.length];
        for(int i=0; i<f_w_i.length; i++){
            value[i] = (byte) (f_w_i[i] ^ primeraMitadValue[i]);
        }
        return value;
    }

    public static byte[] funcionPseudoAleatoria(Key k, byte[] x) throws Exception{
        
        randF = Cipher.getInstance("AES/CTR/NoPadding");
        randF.init(Cipher.ENCRYPT_MODE, k);//new IvParameterSpec(randF.getIV()));
        return randF.doFinal(x);
        //return x;
    }
    
    public static byte[][] trapdoor(String word, Key z, Key y) throws Exception{
        byte[] wordToB = word.getBytes();
        
        byte[] fy = funcionPseudoAleatoria(y, wordToB);
        
        byte[] piz = getAddress(word);
        
        byte[][] tw = {piz, fy};
        
        return tw;
    }
    
    public static void search(Map<String, byte[]> array, Map<String, byte[]> table, byte[][] tw) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{
        byte[] gamma = tw[0];
        byte[] neta = tw[1];
        
        String stgamma = Util.hexArray(gamma);
        
        byte[] tita = table.get(stgamma);
        
        byte[]value = new byte[tita.length];
        
        for(int i=0; i<neta.length; i++){
            value[i] = (byte) (neta[i] ^ tita[i]);
        }
        
        byte[] alfa = new byte[4];
        for(int i=0; i<4; i++){
            alfa[i] = value[i];
        }
        
        byte[] bkey = new byte[value.length-4];
        for(int i=4; i<value.length;i++){
            bkey[i-4] = value[i];
        }
        
        int size = bkey.length;
        
        byte[] id = null;
        
        while(alfa!=null){
            ArrayList<String> list = new ArrayList<String>();
            
            Key key = new SecretKeySpec(bkey, "AES");
            
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, key);
            
            byte[] enode = array.get(Util.hexArray(alfa));
            byte[] node = c.doFinal(enode);
            
            /*for(int i = node.length-4; i<node.length;i++){
                    int j=0;
                    alfa[j] = node[i];
                    j++;
            }*/   
            
            System.arraycopy(node, node.length-4, alfa, 0, 4);
            System.arraycopy(node, node.length-4-size, bkey, 0, size);
            System.arraycopy(node, 0, id, 0, node.length-size-4);
      
            list.add( id.toString());
            
            for(String w:list){
                System.out.println(w + "--");
            }
            
        }  
        
    }
    
}
