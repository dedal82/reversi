/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.controler;

import java.util.logging.Level;
import java.util.logging.Logger;
import ua.vynnyk.board.GameBoard;
import ua.vynnyk.game.BoardGameInterface;
import ua.vynnyk.game.EnumPlayer;
import ua.vynnyk.game.GameCell;
import ua.vynnyk.game.GameOverEvent;
import ua.vynnyk.game.GameOverEventListener;

/**
 *
 * @author vynnyk
 */
public class GameControler implements BoardGameControlerInterface {
    private static final int AI_TIMEOUT = 3000;
    private int status;
    private BoardGameInterface game;
    private GameBoard board;
    private boolean gameEnd;
    
    public GameControler(BoardGameInterface game) {
        this.game = game;
        this.status = 1;
        addListeners();
    }
    
    
    
    @Override
    public void newGame() {
        gameEnd = false;
        board.clear();
        game.newGame();
        board.validate();
        if (status == PL_VS_AI) {
            synchronized (game) {
                if (game.getActivePlayer() == EnumPlayer.SECOND) {               
                    doAIMoveThread();
                }     
            }
        }                     
    }
    
    private void addListeners() {
        game.addGameOverListener(new GameOverEventListener() {
            @Override
            public void GameOver(GameOverEvent e) {
                gameEnd = true;
            }
        });
    }
            
    @Override
    public boolean doMove(GameCell cell) {        
        if (status == PL_VS_PL) {
            return game.doMove(cell);
        } else if (status == PL_VS_AI) {
            return doPlayerVsAi(cell);
        }
        return false;        
    }
    
    private boolean doPlayerVsAi(GameCell cell) {
        synchronized (game) {
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
    public void undoMove() {        
        synchronized (game) {
            if (status == PL_VS_PL || (status == PL_VS_AI && game.getActivePlayer() == EnumPlayer.SECOND)) { 
                game.UndoMove();              
            } 
        } 
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
        addListeners();
    }

    @Override
    public GameBoard getBoard() {
        return board;
    }

    @Override
    public void setBoard(GameBoard board) {
        this.board = board;
    }
                
    private void doAIMoveThread() {        
        if (!gameEnd) {          
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(AI_TIMEOUT);                    
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameControler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    synchronized (game) {
                        if (status == PL_VS_AI && game.getActivePlayer() == EnumPlayer.SECOND) {
                            game.doAIMove();
                        }                        
                    }
                    // recursive do AI move while active player is SECOND
                    if (game.getActivePlayer() == EnumPlayer.SECOND) {
                        doAIMoveThread();
                    }
                }
            });
            t.start();  
        }
    }            
}
