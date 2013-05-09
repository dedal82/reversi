/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.translations;

import ua.vynnyk.l10n.TranslateHelper;
import java.util.Locale;
import java.util.Map;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author vynnyk
 */
public class TranslateHelperTest {
    
    public TranslateHelperTest() {
    }
        
    @BeforeClass
    public static void setUpClass() {
        TranslateHelper.setResources(Locale.ROOT);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     * Test of getString method, of class TranslateHelper.
     */
    @Test
    public void testGetString() {
        System.out.println("getString");
        String resource = "main.menu.game";
        String expResult = "Game";
        String result = TranslateHelper.getString(resource);
        assertEquals(expResult, result);       
    }

    /**
     * Test of setResources method, of class TranslateHelper.
     */
    @Test
    public void testSetResources() {
        System.out.println("setResources");
        Locale l = Locale.ROOT;
        TranslateHelper.setResources(l);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getLocalizations method, of class TranslateHelper.
     */
    @Test
    public void testGetLocalizations() {
        System.out.println("getLocalizations");        
        Locale[] result = TranslateHelper.getLocalizations();                        
        assertEquals(result[0], Locale.US);                
    }
}
