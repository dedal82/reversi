/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.game;

import java.io.Serializable;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.EventListenerList;

/**
 *
 * @author dedal
 */
abstract class AbstractBoardGame implements BoardGameInterface, Serializable {
    
    private int cellsX = 8;
    private int cellsY = 8;
    private EnumPlayer[][] board;
    private EnumPlayer activePlayer = EnumPlayer.FIRST;    
    private Map<EnumPlayer, Integer> score = new EnumMap(EnumPlayer.class);    
    private EventListenerList listenerList = new EventListenerList();    
    private Map<String, Object> gameOptions = new HashMap<>();

    AbstractBoardGame() {
        board = new EnumPlayer[cellsX][cellsY]; 
        EmptyBoard();
    }
    
    AbstractBoardGame(int x, int y) {        
        cellsX = x;
        cellsY = y;            
        board = new EnumPlayer[cellsX][cellsY];
        EmptyBoard();
    }
        
    @Override
    public EnumPlayer getPlayer(GameCell cell) {
        return getPlayer(cell.getX(), cell.getY());          
    }
    
    EnumPlayer getPlayer(int x, int y) {        
        return (isInBoard(x, y)) ? board[x][y] : null;                  
    } 
        
    void setPlayer(GameCell cell , EnumPlayer player) {
        setPlayer(cell.getX(), cell.getY(), player);
    } 
    
    void setPlayer(int x, int y , EnumPlayer player) {
        if (isInBoard(x, y)) { 
            decCount(getPlayer(x, y));
            board[x][y] = player; 
            incCount(player);            
        }
    }
    
    private void incCount(EnumPlayer player) {        
        setCount(player, getCount(player) + 1);
    }
    
    private void decCount(EnumPlayer player) {
        setCount(player, getCount(player) - 1);
    }
    
    boolean isInBoard (int x, int y) {
        return (x >= 0) && (x < getWidth()) && 
               (y >= 0) && (y < getHeight()); 
    } 
    
    final void EmptyBoard() {
        for (EnumPlayer[] row: board) {
            Arrays.fill(row, EnumPlayer.NONE);
        }   
        setCount(EnumPlayer.NONE, cellsX * cellsY);  
        setCount(EnumPlayer.FIRST, 0); 
        setCount(EnumPlayer.SECOND, 0); 
    }
    
    @Override
    public EnumPlayer getActivePlayer() {
        return activePlayer;
    }
    
    void setActivePlayer(EnumPlayer player) {
        this.activePlayer = player;
    }
    
    void setActivePlayer() {
        activePlayer = getNextPlayer();
    }

    EnumPlayer getNextPlayer() {
        return activePlayer == EnumPlayer.FIRST ? EnumPlayer.SECOND : EnumPlayer.FIRST;       
    }
    
    EnumPlayer getNextPlayer(EnumPlayer player) {
        return player == EnumPlayer.FIRST ? EnumPlayer.SECOND : EnumPlayer.FIRST;       
    }
    
    @Override
    public int getCount(EnumPlayer player) {
        return score.get(player);
    }
    
    private void setCount(EnumPlayer player, int count) {
        score.put(player, count);
    }
                                    
    @Override
    public int getWidth() {
        return cellsX;
    }

    @Override
    public int getHeight() {
        return cellsY;
    }

    @Override
    public void setOption(String option, Object value) {
        gameOptions.put(option, value);
    }

    @Override
    public Object getOption(String option) {
        return gameOptions.get(option);
    }
            
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AbstractBoardGame{").append("cellsX=").append(cellsX)
          .append(", cellsY=").append(cellsY).append(", board=")
          .append(Arrays.deepToString(board)).append(", activePlayer=")
          .append(activePlayer).append(", score=").append(score);      
        return  sb.toString();
    }
    
    @Override
    public void addGameOverListener(GameOverEventListener listener) {
        listenerList.add(GameOverEventListener.class, listener);
    }
    
    @Override
    public void removeGameOverListener(GameOverEventListener listener) {
        listenerList.remove(GameOverEventListener.class, listener);
    }
    
    void fireGameOverEvent(GameOverEvent e) {
        EventListener listeners[] = listenerList.getListeners(GameOverEventListener.class);
        
        for (int i = 0; i < listeners.length; i++) {
            ((GameOverEventListener) listeners[i]).GameOver(e);            
       }                    
    }              
    
    @Override
    public void addPutCoinListener(PutCoinEventListener listener) {
        listenerList.add(PutCoinEventListener.class, listener);
    }
    
    @Override
    public void removePutCoinListener(PutCoinEventListener listener) {
        listenerList.remove(PutCoinEventListener.class, listener);
    }
    
    void firePutCoinEvent(PutCoinEvent e) {
        EventListener listeners[] = listenerList.getListeners(PutCoinEventListener.class);
        
        for (int i = 0; i < listeners.length; i++) {
            ((PutCoinEventListener) listeners[i]).PutCoin(e);            
       }                    
    } 
    
    @Override
    public void addChangeCountListener(ChangeCountEventListener listener) {
        listenerList.add(ChangeCountEventListener.class, listener);
    }
    
    @Override
    public void removeChangeCountListener(ChangeCountEventListener listener) {
        listenerList.remove(ChangeCountEventListener.class, listener);
    }
    
    void fireChangeCountEvent(ChangeCountEvent e) {        
        EventListener listeners[] = listenerList.getListeners(ChangeCountEventListener.class);
        
        for (int i = 0; i < listeners.length; i++) {
            ((ChangeCountEventListener) listeners[i]).ChangeCount(e);            
       }                    
    }         
}
