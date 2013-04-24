/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static ua.vynnyk.game.BoardGameReversi.LEVEL_1;
import static ua.vynnyk.game.BoardGameReversi.LEVEL_2;

/**
 *
 * @author Admin
 */
class AIReversi implements AIInterface {
    private static final Integer CELL_CORNERS = 0;    
    private static final Integer CELL_NEAR_CORNERS = 1;    
    private static final Integer CELL_BORDERS = 2;
    private static final Integer CELL_NEAR_BORDERS = 3;
    private Map<Integer, Set<GameCell>> cells;
    private Map<GameCell, Set<GameCell>> corners;
    
    private BoardGameReversi game;
    
    public AIReversi(BoardGameReversi game) {
        this.game = game;
        cells = new HashMap<>();
        corners = new HashMap<>();
        addCorners();        
        addBorders();
        addNearBorders();        
    }
    
    private void addCorners() {
        final int width = game.getWidth();
        final int height = game.getHeight();
        Set tmpSet = new HashSet(4);  
        
        cells.put(CELL_NEAR_CORNERS, new HashSet<GameCell>());
        
        addCorner(tmpSet, new GameCell(0, 0));
        addCorner(tmpSet, new GameCell(0, width - 1));
        addCorner(tmpSet, new GameCell(height - 1, width - 1));
        addCorner(tmpSet, new GameCell(height - 1, 0));
                  
        cells.put(CELL_CORNERS, tmpSet);                        
    }
    
    private void addCorner(Set set, GameCell cell) {
        final Set<GameCell> nearSet = game.getNearCells(cell);
        
        set.add(cell);
        corners.put(cell, nearSet);
        cells.get(CELL_NEAR_CORNERS).addAll(nearSet);
    }
    
    private void addBorders() {
        final int width = game.getWidth();
        final int height = game.getHeight();
        Set<GameCell> set = new HashSet<>();
        
        for (int x = 0; x < width; x++) {
            set.add(new GameCell(x, 0));
            set.add(new GameCell(x, height - 1));            
        }
        for (int y = 1; y < height - 1; y++) {
            set.add(new GameCell(0, y));
            set.add(new GameCell(width - 1, y));            
        }  
        cells.put(CELL_BORDERS, set);
    }

    private void addNearBorders() {
        final int width = game.getWidth();
        final int height = game.getHeight();
        Set<GameCell> set = new HashSet<>();
        
        for (int x = 1; x < width - 1; x++) {
            set.add(new GameCell(x, 1));
            set.add(new GameCell(x, height - 2));            
        }
        for (int y = 2; y < height - 2; y++) {
            set.add(new GameCell(1, y));
            set.add(new GameCell(width - 2, y));            
        }  
        cells.put(CELL_NEAR_BORDERS, set);
    }
    
    private int getMoveLevel(GameCell cell, int maxLevel) {
        final EnumPlayer activePlayer = game.getActivePlayer();
        int level = 0;  
        
        if (maxLevel >= LEVEL_1) {
            if (cells.get(CELL_BORDERS).contains(cell)) {
                level = 1;
            } else if (cells.get(CELL_NEAR_BORDERS).contains(cell)) {
                //Nead to add more checks (Not yet implemented)
                //level = -1;
            }
            if (level >= 0 && !isReversMove(cell)) {
                level = 2;
            }
        }        
        if (maxLevel >= LEVEL_2) {
            if (corners.containsKey(cell)) {
                level = 3;                        
            } else if (cells.get(CELL_NEAR_CORNERS).contains(cell)) {
                for (Map.Entry<GameCell, Set<GameCell>> entry : corners.entrySet()) {
                    if (entry.getValue().contains(cell)) {
                        if (game.getPlayer(entry.getKey()) == EnumPlayer.NONE) {
                            int count = 0;
                            for (GameCell nearCornerCell : entry.getValue()) {
                                if (game.getPlayer(nearCornerCell) == activePlayer) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                level = -2;
                            }
                        }                    
                        break;
                    }                    
                }               
            }
        }
        return level;
    }

    private boolean isReversMove(GameCell cell) {
        final EnumPlayer activePlayer = game.getActivePlayer();     
        final EnumPlayer otherPlayer = game.getNextPlayer(activePlayer);
        final int width = game.getWidth();
        final int height = game.getHeight();
        final int cX = cell.getX();
        final int cY = cell.getY();   
        
        for (int i = 0; i < 360; i += 45) {
            int dx = (int) Math.round(Math.sin(Math.toRadians(i)));
            int dy = (int) Math.round(Math.cos(Math.toRadians(i)));
            int x = cX + dx;            
            int y = cY + dy;
            // search for empty cell;
            boolean isEppty = false;
            while (game.isInBoard(x, y)) {
                EnumPlayer tmpPl = game.getPlayer(x, y);
                if (tmpPl == otherPlayer) {                    
                    break;
                } else if (tmpPl == EnumPlayer.NONE) {
                    isEppty = true;
                }
                x += dx;
                y += dy;
            }
            //search for enemy cell in reverse direction
            if (isEppty) {
                x = cX - dx;            
                y = cY - dy;
                boolean isEnemy = false;
                while (game.isInBoard(x, y)) {
                    EnumPlayer tmpPl = game.getPlayer(x, y);
                    if (tmpPl == EnumPlayer.NONE) {                    
                        break;
                    } else if (tmpPl == otherPlayer) {
                        isEnemy = true;
                    }
                    x += dx;
                    y += dy;
                }
                if (isEnemy) {
                    return true;
                }
            }
            
        }                 
        return false;
    }
    
    @Override
    public GameCell getAIMove(int level) {        
        if (level < 0 || level > 2) {
            throw new IllegalArgumentException("AI level " + level + " doesn't exists");       
        } 
        
        List<GameCell> moves = new ArrayList<>();
        final EnumPlayer activePlayer = game.getActivePlayer();
        final int widht = game.getWidth();
        final int height = game.getHeight();
        int maxCountReversed = 0;
        int maxLevel = -2;
        int countReversed;
        
        for (int i = 0; i < widht; i++) {
            for (int j = 0; j < height; j++) {                
                if (game.isCorrect(i, j, activePlayer)) {
                    final GameCell cell = new GameCell(i, j);
                    countReversed = game.reversCoins(i, j, activePlayer ,false);                    
                    final int tmpLevel = getMoveLevel(cell, level);                                                
                    if (tmpLevel > maxLevel || (tmpLevel == maxLevel && countReversed >= maxCountReversed)) {
                        if (tmpLevel > maxLevel || (tmpLevel == maxLevel && countReversed > maxCountReversed)) {                                                        
                            moves.clear();                            
                        }
                        moves.add(cell);
                        maxLevel = tmpLevel;
                        maxCountReversed = countReversed;
                    }                      
                }
            }
        }
        return moves.get((int) Math.floor(Math.random() * (moves.size())));
    }                       
}
