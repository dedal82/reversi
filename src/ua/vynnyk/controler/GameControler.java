/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.controler;

import java.util.logging.Level;
import java.util.logging.Logger;
import ua.vynnyk.board.GameBoard;
import static ua.vynnyk.controler.BoardGameControlerInterface.PL_VS_AI;
import ua.vynnyk.game.BoardGameInterface;
import ua.vynnyk.game.EnumPlayer;
import ua.vynnyk.game.GameCell;

/**
 *
 * @author vynnyk
 */
public class GameControler implements BoardGameControlerInterface {
    private int status;
    private BoardGameInterface game;
    private GameBoard board;
    
    public GameControler(BoardGameInterface game) {
        this.game = game;
        this.status = 1;
    }

    @Override
    public void newGame() {
        board.clear();
        game.newGame();
        board.validate();
        if (status == PL_VS_AI) {
            if (game.getActivePlayer() == EnumPlayer.SECOND) {                 
                doAIMoveThread();
            }     
        }                     
    }
            
    @Override
    public boolean doMove(GameCell cell) {        
        if (status == PL_VS_PL) {
            return game.doMove(cell);
        } else if (status == PL_VS_AI) {
            if (game.getActivePlayer() == EnumPlayer.FIRST) { 
                if (game.doMove(cell)) {
                    if (game.getActivePlayer() == EnumPlayer.SECOND) {
                        doAIMoveThread();
                    }
                    return true;
                } 
            }    
        }
        return false;        
    }

    @Override
    public void setStatus(int i) {
        this.status = i;
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public BoardGameInterface getGame() {
        return game;
    }

    @Override
    public void setGame(BoardGameInterface game) {
        this.game = game;
    }

    @Override
    public GameBoard getBoard() {
        return board;
    }

    @Override
    public void setBoard(GameBoard board) {
        this.board = board;
    }
        
    private void doAIMove() {
        game.doAIMove();
    }
    
    private void doAIMoveThread() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameControler.class.getName()).log(Level.SEVERE, null, ex);
                }
                game.doAIMove();
                if (game.getActivePlayer() == EnumPlayer.SECOND) {
                    doAIMoveThread();
                }
            }
        });
        t.start();        
    }
    
}
