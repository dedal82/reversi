/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.game;

/**
 *
 * @author vynnyk
 */
public class BoardGameRevers extends AbstractBoardGame {
    static int NONE = 0;
    static int LEFT = -1;
    static int RIGHT = 1;
    static int UP = -1;
    static int DOWN = 1;
                    
    /**
     *
     */
    public BoardGameRevers() {
        super();        
    }
    
    /**
     *
     * @param x - розмір гри по-горизонталі
     * @param y - розмір гри по-вертикалі
     */
    public BoardGameRevers(int x, int y) {        
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
        putCoin(width / 2 - 1, height / 2 - 1, EnumPlayer.FIRST);
        putCoin(width / 2, height / 2 - 1, EnumPlayer.SECOND);
        putCoin(width / 2, height / 2, EnumPlayer.FIRST);
        putCoin(width / 2 - 1, height / 2, EnumPlayer.SECOND);
        fireChangeCountEvent(new ChangeCountEvent(this, getCount(EnumPlayer.FIRST), getCount(EnumPlayer.SECOND)));
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
    public boolean doMove(int x, int y) {
        if (isInBoard(x, y) && getPlayer(x, y) == EnumPlayer.NONE && isNear(x, y, getNextPlayer())) { 
            putCoin(x, y, getActivePlayer());
            reversCoins(x, y);
            fireChangeCountEvent(new ChangeCountEvent(this, getCount(EnumPlayer.FIRST), getCount(EnumPlayer.SECOND)));
            if (isGameOver()) {
                fireGameOverEvent(new GameOverEvent(this, getWinner()));
            }
            else if (isChangeble()) {
                changePlayer();                 
            }           
            return true; 
        } 
        return false; 
    }           
    
    private void reversCoins(int x, int y) {
        reversDirection(x, y, NONE, UP);    //вверх
        reversDirection(x, y, NONE, DOWN);  //вниз
        reversDirection(x, y, LEFT, NONE);  // вліво
        reversDirection(x, y, RIGHT, NONE); //  вправо
        reversDirection(x, y, LEFT, UP);    // лівий верх
        reversDirection(x, y, RIGHT, UP);   // правий верх
        reversDirection(x, y, RIGHT, DOWN); // правий низ
        reversDirection(x, y, LEFT, DOWN);  // лівий низ   
    }
    
    private void reversDirection(int x, int y,int dX, int dY) {       
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
                }                
            } else if (getPlayer(i, j) == EnumPlayer.NONE) {
                break;
            }
            i = i + dX;
            j = j + dY;
        }
        if (needRevers) {
            for (int k = 1; k <= countRevers; k++) {
                putCoin(x + dX * k, y + dY * k, getActivePlayer());
            }
        }
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
}
