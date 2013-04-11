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
    
    private BoardGameReversi boardGame;
    
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
        boardGame = new BoardGameReversi();
    }
    
    @After
    public void tearDown() {
        boardGame = null;
    }

    /**
     * Test of newGame method, of class BoardGameReversi.
     */
    @Test
    public void testNewGame() {
        System.out.println("newGame");        
        boardGame.newGame();
        assertEquals("Error count NONE cells", boardGame.getWidth() * boardGame.getHeight() - 4, boardGame.getCount(EnumPlayer.NONE));
        assertEquals("Error count FIRST cells", 2, boardGame.getCount(EnumPlayer.FIRST));
        assertEquals("Error count SECOND cells", 2, boardGame.getCount(EnumPlayer.SECOND));
    }

    /**
     * Test of doMove method, of class BoardGameReversi.
     */
    @Test
    public void testDoMove() {
        System.out.println("doMove");
        GameCell cell = new GameCell(4, 2);
        boardGame.newGame();        
        assertEquals("Can not do move", true, boardGame.doMove(cell));
        assertEquals("Cell is not FIRST", EnumPlayer.FIRST, boardGame.getPlayer(cell));
        assertEquals("Cell was not reversed", EnumPlayer.FIRST, boardGame.getPlayer(new GameCell(4, 3)));
    }

    /**
     * Test of UndoMove method, of class BoardGameReversi.
     */
    @Test
    public void testUndoMove() {
        System.out.println("UndoMove");
        GameCell cell = new GameCell(4, 2);
        boardGame.newGame();
        boardGame.doMove(cell);
        boardGame.UndoMove();
        assertEquals("Cell is not NONE", EnumPlayer.NONE, boardGame.getPlayer(cell));
        assertEquals("Cell is not SECOND" ,EnumPlayer.SECOND, boardGame.getPlayer(new GameCell(4, 3)));
    }

    /**
     * Test of getBestMove method, of class BoardGameReversi.
     */
    @Test
    public void testGetBestMove() {
        System.out.println("getBestMove");        
        boardGame.newGame();
        GameCell cell = boardGame.getBestMove();
        assertNotNull("Null cell returned", cell);
        assertEquals("Incorrect move", EnumPlayer.NONE, boardGame.getPlayer(cell));        
    }

    /**
     * Test of doAIMove method, of class BoardGameReversi.
     */
    @Test
    public void testDoAIMove() {
        System.out.println("doAIMove");
        boardGame.newGame();
        GameCell cell = boardGame.getBestMove();
        assertNotNull("Null cell returned", cell);
        assertEquals("Incorrect move", EnumPlayer.NONE, boardGame.getPlayer(cell));
    }
}