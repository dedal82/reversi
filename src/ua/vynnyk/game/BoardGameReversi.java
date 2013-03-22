/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.game;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vynnyk
 */
public class BoardGameReversi extends AbstractBoardGame {
    
    private static final long serialVersionUID = 1L;
    
    private static final int NONE = 0;
    private static final int LEFT = -1;
    private static final int RIGHT = 1;
    private static final int UP = -1;
    private static final int DOWN = 1;
    
    // List of moves to undo. First element is move to undo and other elements are cells to undo revers
    private List<GameCell> undoList = new LinkedList<>();    
                    
    /**
     *
     */
    public BoardGameReversi() {
        super();        
    }
    
    /**
     *
     * @param x - розмір гри по-горизонталі
     * @param y - розмір гри по-вертикалі
     */
    public BoardGameReversi(int x, int y) {        
        super(x, y);
    }
    
    /**
     * розпочинає нову гру
     */
    @Override
    public void newGame() {
        EmptyBoard();
        int width = getWidth();
        int height = getHeight();                          
        putCoin(new GameCell(width / 2 - 1, height / 2 - 1), EnumPlayer.FIRST);
        putCoin(new GameCell(width / 2, height / 2 - 1), EnumPlayer.SECOND);
        putCoin(new GameCell(width / 2, height / 2), EnumPlayer.FIRST);
        putCoin(new GameCell(width / 2 - 1, height / 2), EnumPlayer.SECOND);
        fireChangeCountEvent(new ChangeCountEvent(this, getCount(EnumPlayer.FIRST), getCount(EnumPlayer.SECOND)));
    }
        
    private void putCoin (GameCell cell, EnumPlayer player) { 
        putCoin(cell.getX(), cell.getY(), player);
    }
    
    private void putCoin (int x, int y, EnumPlayer player) {             
        setPlayer(x, y, player);                
        firePutCoinEvent(new PutCoinEvent(this, x, y, player));                                
    }
    
    /**
     * пробує зробити хід в задані координати
     * @param x - координата клітинки по-горизонталі
     * @param y - координата клітинки по-вертикалі
     * @return - true, якщо хід правильний
     */
    @Override
    public boolean doMove(GameCell cell) {
        final int x = cell.getX();
        final int y = cell.getY();
        if (isInBoard(x, y) && getPlayer(x , y) == EnumPlayer.NONE && isNear(x, y, getNextPlayer())) { 
            undoList.clear();
            undoList.add(new GameCell(x, y));
            putCoin(x, y, getActivePlayer());
            reversCoins(x, y, true);
            if (isChangeble()) {
                setActivePlayer();                
            }    
            fireChangeCountEvent(new ChangeCountEvent(this, getCount(EnumPlayer.FIRST), getCount(EnumPlayer.SECOND)));
            if (isGameOver()) {
                fireGameOverEvent(new GameOverEvent(this, getWinner()));
            }                    
            return true; 
        } 
        return false; 
    }           
    
    private int reversCoins(int x, int y, boolean revers) {
        return reversDirection(x, y, NONE, UP, revers) +    //вверх
               reversDirection(x, y, NONE, DOWN, revers) +  //вниз
               reversDirection(x, y, LEFT, NONE, revers) +  // вліво
               reversDirection(x, y, RIGHT, NONE, revers) + //  вправо
               reversDirection(x, y, LEFT, UP, revers) +    // лівий верх
               reversDirection(x, y, RIGHT, UP, revers) +   // правий верх
               reversDirection(x, y, RIGHT, DOWN, revers) + // правий низ
               reversDirection(x, y, LEFT, DOWN, revers);   // лівий низ   
    }
    
    private int reversDirection(int x, int y,int dX, int dY, boolean revers) {               
        boolean needRevers = false;
        int countRevers = 0;
        int i = x + dX;
        int j = y + dY;
        while (i >= 0 && j>=0 && i < getWidth() && j < getHeight()) {
            if (getPlayer(i, j) == getNextPlayer()) {
                ++countRevers;
            } else if (getPlayer(i, j) == getActivePlayer()) {
                if (countRevers > 0) {
                    needRevers = true;
                    break;
                }                
            } else if (getPlayer(i, j) == EnumPlayer.NONE) {
                countRevers = 0;
                break;
            }
            i = i + dX;
            j = j + dY;
        }
        if (revers && needRevers) {
            for (int k = 1; k <= countRevers; k++) {
                final int xx = x + dX * k;
                final int yy = y + dY * k;
                undoList.add(new GameCell(xx, yy));
                putCoin(xx, yy, getActivePlayer());
            }
        }
        return countRevers;
    }
    
    // визначає чи можна передати хід іншому гравцеві
    private boolean isChangeble() {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (getPlayer(i, j) == EnumPlayer.NONE && isNear(i, j, getActivePlayer())) {
                    return true;
                }
            }
            
        }
        return false;
    }    
    // виначає чи є по-сусідству потрібна фішка
    private boolean isNear(int x, int y, EnumPlayer nearPl) {
        final int w = getWidth();
        final int h = getHeight();        
        return (y > 0 && getPlayer(x, y - 1) == nearPl) || 
               (y < h - 1 && getPlayer(x, y + 1) == nearPl) ||
               (x > 0 && getPlayer(x - 1, y) == nearPl) ||
               (x < w - 1 && getPlayer(x + 1, y) == nearPl) ||
               (x > 0 && y > 0 && getPlayer(x - 1, y - 1) == nearPl) ||
               (x < w - 1 && y > 0 && getPlayer(x + 1, y - 1) == nearPl) ||
               (x < w - 1 && y < h - 1 && getPlayer(x + 1, y + 1) == nearPl) ||
               (x > 0 && y < h - 1 && getPlayer(x - 1 , y + 1) == nearPl);
    }       
    
    private boolean isGameOver() {
        return getCount(EnumPlayer.NONE) == 0;                
    }

    private EnumPlayer getWinner() {
        final int first = getCount(EnumPlayer.FIRST);
        final int second = getCount(EnumPlayer.SECOND);
        if (first > second) {
            return EnumPlayer.FIRST;
        } else if (first < second) {
            return EnumPlayer.SECOND;
        } else {
            return EnumPlayer.NONE;
        }
    }

    // undo last one move
    @Override
    public void UndoMove() {        
        if (undoList.size() > 0) {            
            final EnumPlayer player = getPlayer(undoList.get(0));
            final EnumPlayer undoPlayer = getNextPlayer(player);
            putCoin(undoList.remove(0), EnumPlayer.NONE);            
            for (GameCell gameCell : undoList) {
                putCoin(gameCell, undoPlayer);                
            }
            undoList.clear();
            setActivePlayer(player);
            fireChangeCountEvent(new ChangeCountEvent(this, getCount(EnumPlayer.FIRST), getCount(EnumPlayer.SECOND)));
        }
    }

    @Override
    public GameCell getBestMove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
      
}
