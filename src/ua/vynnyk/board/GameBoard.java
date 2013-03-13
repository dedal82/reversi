package ua.vynnyk.board;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.EnumMap;
import java.util.EventListener;
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
    private JPanel[][] cells;
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
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }          

    private void drawBoard(Graphics g) {
          g.setColor(lineColor);
          g.fillRect(0, 0, getWidth(), getHeight());
    }    
    
    public void drawCoin(int x, int y) {        
        CoinInterface coin = getCoin(game.getPlayer(x, y));        
        if (coin != null) {
            cells[x][y].removeAll();
            cells[x][y].add((Component) coin, 0);
        }
    } 
        
    private void clickOnBord(MouseEvent e) {                        
        final int cellX = (e.getX() - blank) / getCellW();
        final int cellY = (e.getY() - blank) / getCellH();
        fireBoardClickEvent(new BoardClickEvent(this, cellX, cellY));
    }

    private int getCellW() {
        return (getWidth() - blank * 2) / cellsX;
    }
    
    private int getCellH() {
        return (getHeight() - blank * 2) / cellsY;
    }
            
    public void addBoardClickListener(BoardClickEventListener listener) {
        listenerList.add(BoardClickEventListener.class, listener);
    }
    
    public void removeBoardClickListener(BoardClickEventListener listener) {
        listenerList.remove(BoardClickEventListener.class, listener);
    }
    
    private void fireBoardClickEvent(BoardClickEvent e) {
        EventListener listeners[] = listenerList.getListeners(BoardClickEventListener.class);
        for (int i = 0; i < listeners.length; i++) {
            ((BoardClickEventListener) listeners[i]).BoardClick(e);            
        }
    }  

    private void initComponents() {        
        final Border border = BorderFactory.createEmptyBorder(blank, blank, blank, blank);
        setBorder(border);
        setLayout(new GridLayout(cellsX, cellsY, blank, blank));
        cells = new JPanel[cellsX][cellsY];
        for (int i = 0; i < cellsX; i++) {
            for (int j = 0; j < cellsY; j++) {
                final JPanel cell = new JPanel(new GridLayout());
                cell.setBackground(boardColor);
                cell.setBorder(border);
                cells[i][j] = cell;
                add(cell);
            }
            
        }
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickOnBord(e);
            }             
        });
    }

    public void clear() {
        for (int i = 0; i < cellsX; i++) {
            for (int j = 0; j < cellsY; j++) {
                cells[i][j].removeAll();
            }
        }    
    }
}
