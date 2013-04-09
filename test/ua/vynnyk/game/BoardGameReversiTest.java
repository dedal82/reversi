/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.game;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class BoardGameReversiTest {
    
    public BoardGameReversiTest() {
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
     * Test of newGame method, of class BoardGameReversi.
     */
    @Test
    public void testNewGame() {
        System.out.println("newGame");
        BoardGameReversi instance = new BoardGameReversi();
        instance.newGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of doMove method, of class BoardGameReversi.
     */
    @Test
    public void testDoMove() {
        System.out.println("doMove");
        GameCell cell = null;
        BoardGameReversi instance = new BoardGameReversi();
        boolean expResult = false;
        boolean result = instance.doMove(cell);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of UndoMove method, of class BoardGameReversi.
     */
    @Test
    public void testUndoMove() {
        System.out.println("UndoMove");
        BoardGameReversi instance = new BoardGameReversi();
        instance.UndoMove();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBestMove method, of class BoardGameReversi.
     */
    @Test
    public void testGetBestMove() {
        System.out.println("getBestMove");
        BoardGameReversi instance = new BoardGameReversi();
        GameCell expResult = null;
        GameCell result = instance.getBestMove();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of doAIMove method, of class BoardGameReversi.
     */
    @Test
    public void testDoAIMove() {
        System.out.println("doAIMove");
        BoardGameReversi instance = new BoardGameReversi();
        GameCell expResult = null;
        GameCell result = instance.doAIMove();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}