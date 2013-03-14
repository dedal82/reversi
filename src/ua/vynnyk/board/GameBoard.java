package ua.vynnyk.board;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import ua.vynnyk.game.BoardGameInterface;
import ua.vynnyk.game.EnumPlayer;

/**
 *
 * @author vynnyk
 */
public class GameBoard extends JPanel {
    private int cellsX = 8;
    private int cellsY = 8;    
    private Color boardColor = Color.GRAY;
    private Color lineColor = Color.BLACK;    
    private final int lineWidth = 3;
    private final int blank = 5;
    private Map<EnumPlayer, CoinPoolInterface> coins = new EnumMap(EnumPlayer.class);    
    private Cell[][] cells;
    private BoardGameInterface game;

    public GameBoard() {        
        initComponents();
    }
    
    public GameBoard(int x, int y) {         
        this.cellsX = x;
        this.cellsY = y;       
        initComponents();
    }
    
    public BoardGameInterface getGame() {
        return game;
    }

    public void setGame(BoardGameInterface game) {
        this.game = game;
    }
    
    public void setPoolCoin(EnumPlayer player, CoinPoolInterface coin) {
        coins.put(player, coin);
    }   
    
    public CoinInterface getCoin(EnumPlayer player) {       
        return coins.get(player).getCoin();
    } 
                       
    public void drawCoin(int x, int y) {        
        CoinInterface coin = getCoin(game.getPlayer(x, y));        
        if (coin != null) {
            cells[x][y].removeAll();
            cells[x][y].add((Component) coin);
            cells[x][y].repaint();
        }
    } 
            
    private void initComponents() {        
        setBackground(lineColor);
        final Border border = BorderFactory.createEmptyBorder(blank, blank, blank, blank);
        setBorder(border);
        setLayout(new GridLayout(cellsY, cellsX, blank, blank));
        cells = new Cell[cellsX][cellsY];
        
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final Cell cell = (Cell) e.getSource();
                game.doMove(cell.getCellX(), cell.getCellY());
            }             
        };        
        for (int y = 0; y < cellsY; y++) {
            for (int x = 0; x < cellsX; x++) {
                addCell(x, y, border, listener);
            }            
        }                 
    }

    public void clear() {
        for (int i = 0; i < cellsX; i++) {
            for (int j = 0; j < cellsY; j++) {
                cells[i][j].removeAll();
                cells[i][j].repaint();
            }
        }    
    }

    private void addCell(int x, int y, Border border, MouseAdapter listener) {       
        final Cell cell = new Cell(x, y, new GridLayout());
        cell.setBackground(boardColor);
        cell.setBorder(border);
        cell.addMouseListener(listener);
        cells[x][y] = cell;
        add(cell);
    }

    @Override
    public void setBounds(int i, int i1, int i2, int i3) {
        super.setBounds(i, i1, i2, i2);
    }    
}
