/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sse1;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marta
 */
public class EncryptedDocumentTest {
    
    public EncryptedDocumentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class EncryptedDocument.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        EncryptedDocument instance = null;
        String expResult = "";
        String result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPath method, of class EncryptedDocument.
     */
    @Test
    public void testGetPath() {
        System.out.println("getPath");
        EncryptedDocument instance = null;
        String expResult = "";
        String result = instance.getPath();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cipher method, of class EncryptedDocument.
     */
    @Test
    public void testCipher() {
        System.out.println("cipher");
        PlainDocument doc = null;
        EncryptedDocument instance = null;
        instance.encryptFile(doc);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class EncryptedDocument.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        EncryptedDocument instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
