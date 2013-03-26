/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.controler;

import ua.vynnyk.board.GameBoard;
import ua.vynnyk.game.BoardGameInterface;
import ua.vynnyk.game.GameCell;

/**
 *
 * @author vynnyk
 */
public interface BoardGameControlerInterface {
    public static final String[] STATUS = {"Player VS Player", 
                                           "Player VS AI",
                                           "AI VS AI",
                                           "Player VS Player (Network Game)" };
    public static final int PL_VS_PL = 0;
    public static final int PL_VS_AI = 1;
    public static final int AI_VS_AI = 2;
    public static final int PL_VS_PL_NET = 3;
    
    public void newGame();
    public boolean doMove(GameCell cell);
    public void undoMove();
    public void setGame(BoardGameInterface game);
    public BoardGameInterface getGame();
    public void setBoard(GameBoard board);
    public GameBoard getBoard();
    public void setStatus(int i);
    public int getStatus();
}