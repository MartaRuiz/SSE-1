/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sse1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marta
 */
public class IndexTest {
    
    private Index index;
    private String[] keywords = {"el", "kajshd", "coronel", "con"};
    
    public IndexTest() {
        File f = new File("prueba1"); // Creamos un objeto file
        String path = f.getAbsolutePath(); // Llamamos al método que devuelve la ruta absoluta

        PlainDocument doc = new PlainDocument("prueba1", path);
        PlainDocument doc1 = new PlainDocument("prueba2", path);
        PlainDocument doc2 = new PlainDocument("prueba3", path);

        Library lib = new Library();
        lib.addDocument(doc);
        lib.addDocument(doc1);
        lib.addDocument(doc2);
        index = new Index(lib, keywords);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getKeywords method, of class Index.
     */
    @Test
    public void testGetKeywords() {
        System.out.println("getKeywords");
        
        List expResult = new ArrayList<String>();
        expResult.add("con");
        expResult.add("el");
        expResult.add("coronel");
        
        List result = index.getKeywords();
        //assertArrayEquals(expResult, result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }


    /**
     * Test of numberOfKeywords method, of class Index.
     */
    @Test
    public void testNumberOfKeywords() {
        System.out.println("numberOfKeywords");
        int expResult = 3;
        int result = index.numberOfKeywords();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
        //fail("The test case is a prototype.");
    }
}
