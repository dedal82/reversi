/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.controler;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ua.vynnyk.board.GameBoard;
import static ua.vynnyk.controler.BoardGameControlerInterface.*;
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
    private static final int AI_TIMEOUT = 1000;
    private BoardGameInterface game;
    private GameBoard board;
    private Integer oldStatus;
    private volatile Integer status;
    private volatile boolean gameEnd;
    private RemoteMovable remote;    
    
    public GameControler(BoardGameInterface game) {
        this.game = game;
        this.status = PL_VS_AI;
        addListeners();
    }
    
    @Override
    public void newGame() { 
        if (status == AI_VS_AI || status == PL_VS_PL_CLIENT || status == PL_VS_PL_SERVER) {
            status = oldStatus;
        }
        gameEnd = false;
        board.clear();
        game.newGame();
        board.validate();
                
        if (status == PL_VS_AI && game.getActivePlayer() == EnumPlayer.SECOND) {
            doAIMoveThread();                                           
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
        } else if ((status == PL_VS_PL_SERVER && game.getActivePlayer() == EnumPlayer.FIRST) ||
                   (status == PL_VS_PL_CLIENT && game.getActivePlayer() == EnumPlayer.SECOND)) {
            boolean isMoved = false;
            if (remote.isConnected()) {
                isMoved = game.doMove(cell);
                if (isMoved) {
                   remote.sendMove(cell);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Remote side is not connected");
            }            
            return isMoved;
        }
        return false;        
    }     
     
    boolean doRemoteMove(GameCell cell) {
        if (status == PL_VS_PL_SERVER || status == PL_VS_PL_CLIENT) {
            return game.doMove(cell); 
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
    
    private void startServer () {
        remote = new Server(this);
        new Thread((Runnable) remote).start();
    }
    
    private void connectToServer() {
        String host = "localhost";
        host = JOptionPane.showInputDialog("Input server host", host);
        if (host != null) {
            remote = new Client(host, this);
            new Thread((Runnable) remote).start();
        } else {
            newGame();
        }
    }

    @Override
    public void setStatus(int i) {
        synchronized (this) {
            if (status == PL_VS_PL || status == PL_VS_AI) {
                this.oldStatus = this.status;
                this.status = i;
            } else {
                this.oldStatus = i;
            }
        }
        switch (status) {
            case PL_VS_AI: doAIMoveThread();
                           break;
            case PL_VS_PL_SERVER: startServer();
                           break;
            case PL_VS_PL_CLIENT: connectToServer();
                           break;    
        }                
    }

    @Override
    public int getStatus() {
        synchronized (this) {
            if (status == PL_VS_PL || status == PL_VS_AI) {
                return status;
            } else {
                return oldStatus;
            }
        }        
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

    @Override
    public GameCell getBestMove() {
        if (!gameEnd) {
            synchronized (game) {
                final GameCell cell = game.getBestMove();
                board.blinkCell(cell.getX(), cell.getY());
                return cell;
            }
        }
        return null;
    }
            
    private void doAIMoveThread() {        
        if (!gameEnd) {          
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    pause(AI_TIMEOUT);
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
    
    @Override
    public void startAIBattle() { 
        newGame();        
        oldStatus = status;            
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                setStatus(AI_VS_AI);
                while (!gameEnd) {
                    pause(AI_TIMEOUT);                
                    synchronized (status) {
                        if (status == AI_VS_AI) {
                            game.doAIMove();
                        } else {
                            break;
                        }                    
                    }
                }
                status = oldStatus;
            }
        });
        t.start();          
    }
    
    @Override
    public void stopAIBattle() {
        status = oldStatus;
    }
    
    private void pause(int ms) {
        try {
            Thread.sleep(ms);                    
        } catch (InterruptedException ex) {
            Logger.getLogger(GameControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
