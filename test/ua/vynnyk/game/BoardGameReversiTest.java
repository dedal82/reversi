/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.game;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    
    /**
     * Test of addCorners method, of class BoardGameReversi.
     * Used reflections to test private field
     */
    @Test
    public void testAddCorners() throws Exception {
        System.out.println("addCorners");                      
        Field field = BoardGameReversi.class.getDeclaredField("corners");
        field.setAccessible(true);
        
        Map<GameCell, Set<GameCell>> corners = (Map<GameCell, Set<GameCell>>) field.get(boardGame);
        GameCell cell = new GameCell(0, 0);
        Set<GameCell> set = corners.get(cell);
        assertNotNull("Corner cell is null", cell);
        assertTrue(set.contains(new GameCell(0, 1)));
        assertTrue(set.contains(new GameCell(1, 1)));
        assertTrue(set.contains(new GameCell(1, 0)));        
    }
    
    /**
     * Test of addBorders method, of class BoardGameReversi.
     * Used reflections to test private field
     */
    @Test
    public void testAddBorders() throws Exception {
        System.out.println("addBorders");                      
        Field field = BoardGameReversi.class.getDeclaredField("cells");
        field.setAccessible(true);
        
        Map<Integer, Set<GameCell>> cells = (Map<Integer, Set<GameCell>>) field.get(boardGame);        
        Set<GameCell> set = cells.get(1);        
        assertNotNull("Corner cell is null", set);        
    }
    
    /**
     * Test of addBorders method, of class BoardGameReversi.
     * Used reflections to test private field
     */
    @Test
    public void testAddNearBorders() throws Exception {
        System.out.println("addNearBorders");                      
        Field field = BoardGameReversi.class.getDeclaredField("cells");
        field.setAccessible(true);
        
        Map<Integer, Set<GameCell>> cells = (Map<Integer, Set<GameCell>>) field.get(boardGame);        
        Set<GameCell> set = cells.get(2);        
        assertNotNull("Corner cell is null", set);        
    }
    
}