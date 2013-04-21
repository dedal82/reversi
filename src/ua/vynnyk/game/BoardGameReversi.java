/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.game;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author vynnyk
 */
public class BoardGameReversi extends AbstractBoardGame {
    
    private static final long serialVersionUID = 1L;    
    
    static final int LEVEL_0 = 0;
    static final int LEVEL_1 = 1;    
    static final int LEVEL_2 = 2;    
    
    public static final String OPTION_RESULT_MOVE = "OPTION_RESULT_MOVE";
    public static final String OPTION_AI_LEVEL = "OPTION_AI_LEVEL";
        
    // List of moves to undo. First element is move to undo and other elements are cells to undo revers
    private List<GameCell> undoList; 
    private AIInterface ai;
                  
    /**
     *
     */
    public BoardGameReversi() {
        super();
        init();
    }
    
    /**
     *
     * @param x - game size by horizontal
     * @param y - game size by vertical
     */
    public BoardGameReversi(int x, int y) {        
        super(x, y);
        init();
    }
    
    private void init() {
        ai = new AIReversi(this);
        undoList = new LinkedList<>();
        setOption(OPTION_RESULT_MOVE, true);        
        setOption(OPTION_AI_LEVEL, LEVEL_2);                
    }        
    
    Set<GameCell> getNearCells(GameCell cell) {        
        Set<GameCell> cellsSet = new HashSet<>();      
        for (int i = 0; i < 360; i += 45) {
            int dx = (int) Math.round(Math.sin(Math.toRadians(i)));
            int dy = (int) Math.round(Math.cos(Math.toRadians(i)));
            if (isInBoard(cell.getX() + dx, cell.getY() + dy)) {
                cellsSet.add(new GameCell(cell, dx, dy));
            }
        }                
        return cellsSet;
    }        
            
    /**
     * Start new game
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
        if (isCorrect(cell.getX(), cell.getY(), getActivePlayer())) { 
            doCorrectMove(cell);
            return true; 
        } 
        return false; 
    }   
    
    private void doCorrectMove(GameCell cell) {
        final EnumPlayer player = getActivePlayer();
        final int x = cell.getX();
        final int y = cell.getY();        
        undoList.clear();
        undoList.add(cell);
        putCoin(x, y, player);
        reversCoins(x, y, player, true);
        boolean gameOver = isGameOver();
        if (!gameOver && isChangeble(getNextPlayer())) {
            setActivePlayer();                  
        }
        fireChangeCountEvent(new ChangeCountEvent(this, getCount(EnumPlayer.FIRST), getCount(EnumPlayer.SECOND)));
        if (gameOver) {
            fireGameOverEvent(new GameOverEvent(this, getWinner()));
        }
    }
    
    int reversCoins(int x, int y, EnumPlayer player, boolean revers) {
        int count = 0;
        for (int i = 0; i < 360; i += 45) {
            int dx = (int) Math.round(Math.sin(Math.toRadians(i)));
            int dy = (int) Math.round(Math.cos(Math.toRadians(i)));
            
            count += reversDirection(x, y, dx, dy, player, revers);
        }         
        return count;
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
                if (isCorrect(i, j, player)) {
                    return true;
                }
            }
            
        }
        return false;
    }    
    
    //return true when near is other player and OPTION_RESULT_MOVE is diseble or move will revers one or more coins
    boolean isCorrect(int x, int y, EnumPlayer player) {
        return isInBoard(x, y) && getPlayer(x , y) == EnumPlayer.NONE && isNear(x, y, getNextPlayer(player)) &&
               (getOption(OPTION_RESULT_MOVE) == false || reversCoins(x, y, player, false) > 0);                    
    }
    
    // detecting other player in nearest game cells
    private boolean isNear(int x, int y, EnumPlayer nearPlayer) {
        final int w = getWidth();
        final int h = getHeight();        
        Set<GameCell> set = getNearCells(new GameCell(x, y));
        for (GameCell gameCell : set) {
            if (getPlayer(gameCell) == nearPlayer) {
                return true;
            }
        }
        return false;
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
        return ai.getAIMove(LEVEL_2);
    }
        
    @Override
    public GameCell doAIMove() {
        GameCell cell = ai.getAIMove((int) getOption(OPTION_AI_LEVEL));
        doCorrectMove(cell);
        return cell;
    }                            
}
