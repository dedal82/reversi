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
    private static final int LEVEL_0 = 0;
    private static final int LEVEL_1 = 1;    
    private static final int LEVEL_2 = 2;
    private static final String OPTION_PLAYERS = "OPTION_PLAYERS";
    private static final String PLAYER_VS_PLAYER = "PL_VS_PL";
    private static final String PLAYER_VS_AI = "PL_VS_AI";
    private static final String AI_VS_AI = "AI_VS_AI";
    private static final String OPTION_RESULT_MOVE = "OPTION_RESULT_MOVE";
    
    // List of moves to undo. First element is move to undo and other elements are cells to undo revers
    private List<GameCell> undoList;   
                  
    /**
     *
     */
    public BoardGameReversi() {
        super();
        init();
    }
    
    /**
     *
     * @param x - розмір гри по-горизонталі
     * @param y - розмір гри по-вертикалі
     */
    public BoardGameReversi(int x, int y) {        
        super(x, y);
        init();
    }
    
    private void init() {
        undoList = new LinkedList<>();
        setOption(OPTION_RESULT_MOVE, true);
        setOption(OPTION_PLAYERS, PLAYER_VS_PLAYER);
    }
            
    /**
     * розпочинає нову гру
     */
    
    @Override
    public void newGame() {
        EmptyBoard();
        final int width = getWidth();
        final int height = getHeight();                          
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
     * try do move into cell     
     * @return - true, if move correct
     */
    @Override
    public boolean doMove(GameCell cell) {
        final int x = cell.getX();
        final int y = cell.getY();
        final EnumPlayer player = getActivePlayer();
        if (isInBoard(x, y) && getPlayer(x , y) == EnumPlayer.NONE && isCorrect(x, y, player)) { 
            undoList.clear();
            undoList.add(cell);
            putCoin(x, y, player);
            reversCoins(x, y, player, true);
            if (isChangeble(getNextPlayer())) {
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
    
    private int reversCoins(int x, int y, EnumPlayer player, boolean revers) {
        return reversDirection(x, y, NONE, UP, player, revers) +    //вверх
               reversDirection(x, y, NONE, DOWN, player, revers) +  //вниз
               reversDirection(x, y, LEFT, NONE, player, revers) +  // вліво
               reversDirection(x, y, RIGHT, NONE, player, revers) + //  вправо
               reversDirection(x, y, LEFT, UP, player, revers) +    // лівий верх
               reversDirection(x, y, RIGHT, UP, player, revers) +   // правий верх
               reversDirection(x, y, RIGHT, DOWN, player, revers) + // правий низ
               reversDirection(x, y, LEFT, DOWN, player, revers);   // лівий низ   
    }
    
    private int reversDirection(int x, int y,int dX, int dY, EnumPlayer player,  boolean revers) {                       
        final int width = getWidth();
        final int height = getHeight();
        final EnumPlayer nextPlayer = getNextPlayer(player);
        boolean needRevers = false;
        int countRevers = 0;
        int i = x + dX;
        int j = y + dY;
        while (i >= 0 && j>= 0 && i < width && j < height) {
            final EnumPlayer tmp_player = getPlayer(i, j);
            if (tmp_player == nextPlayer) {
                ++countRevers;
            } else if (tmp_player == player) {
                if (countRevers > 0) {
                    needRevers = true;                    
                }                
                break;
            } else if (tmp_player == EnumPlayer.NONE) {                
                break;
            }
            i = i + dX;
            j = j + dY;
        }
        
        if (needRevers) {
            if (revers) {
                for (int k = 1; k <= countRevers; k++) {
                    final int xx = x + dX * k;
                    final int yy = y + dY * k;
                    undoList.add(new GameCell(xx, yy));
                    putCoin(xx, yy, player);                    
                }    
            }
        } else {
            countRevers = 0;
        }        
        return countRevers;
    }
    
    // return true if next player can do move
    private boolean isChangeble(EnumPlayer player) {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (getPlayer(i, j) == EnumPlayer.NONE && isCorrect(i, j, player)) {
                    return true;
                }
            }
            
        }
        return false;
    }    
    
    //return true when near is other player and OPTION_RESULT_MOVE is diseble or move will revers one or more coins
    private boolean isCorrect(int x, int y, EnumPlayer player) {
        return isNear(x, y, getNextPlayer(player)) && 
               (getOption(OPTION_RESULT_MOVE) == false || reversCoins(x, y, player, false) > 0);                    
    }
    
    // detecting other player in nearest game cells
    private boolean isNear(int x, int y, EnumPlayer nearPlayer) {
        final int w = getWidth();
        final int h = getHeight();        
        return (y > 0 && getPlayer(x, y - 1) == nearPlayer) || 
               (y < h - 1 && getPlayer(x, y + 1) == nearPlayer) ||
               (x > 0 && getPlayer(x - 1, y) == nearPlayer) ||
               (x < w - 1 && getPlayer(x + 1, y) == nearPlayer) ||
               (x > 0 && y > 0 && getPlayer(x - 1, y - 1) == nearPlayer) ||
               (x < w - 1 && y > 0 && getPlayer(x + 1, y - 1) == nearPlayer) ||
               (x < w - 1 && y < h - 1 && getPlayer(x + 1, y + 1) == nearPlayer) ||
               (x > 0 && y < h - 1 && getPlayer(x - 1 , y + 1) == nearPlayer);
    }       
    
    // return true if game end 
    private boolean isGameOver() {
        return getCount(EnumPlayer.NONE) == 0 || 
               (!isChangeble(EnumPlayer.FIRST) && !isChangeble(EnumPlayer.SECOND));                
    }

    //return winner 
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
        return getAIMove(LEVEL_0);
    }
    
    private GameCell getAIMove(int level) {
        return null;        
    }          
}
