package ua.vynnyk.board;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.util.EnumMap;
import java.util.EventListener;
import java.util.Map;
import javax.swing.JPanel;
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
    private Map<EnumPlayer, CoinInterface> coins = new EnumMap(EnumPlayer.class);    
    private BoardGameInterface game;

    public GameBoard() {
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeCoins();
            }            
        });
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickOnBord(e);
            }             
        });
    }    
    
    public BoardGameInterface getGame() {
        return game;
    }

    public void setGame(BoardGameInterface game) {
        this.game = game;
    }
    
    public void setCoin(EnumPlayer player, CoinInterface coin) {
        coins.put(player, coin);
    }   
    
    public CoinInterface getCoin(EnumPlayer player) {
        return coins.get(player);
    } 
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawCoins(g);
    }          

    private void drawBoard(Graphics g) {
        final int width = getWidth();
        final int height = getHeight();        
        final int cellWH = getCellWH(); 
        g.setColor(boardColor);
        g.fillRect(0, 0, width, height);
        g.setColor(lineColor);
        final Graphics2D g2 = (Graphics2D) g;
        final Stroke stroke = g2.getStroke();
        g2.setStroke(new BasicStroke(3));
        for (int i = 0; i <= cellsX; i++) {
            g2.drawLine(i * cellWH + blank , blank , i * cellWH + blank, cellsY * cellWH + blank);
        }        
        for (int i = 0; i <= cellsY; i++) {
            g2.drawLine(blank , i * cellWH + blank, cellsX * cellWH + blank, i * cellWH + blank);            
        }        
        g2.setStroke(stroke);
    }

    private void drawCoins(Graphics g) {        
        if (game != null) {
            for (int i = 0; i < cellsX; i++) {
                for (int j = 0; j < cellsY; j++) {
                    drawCoin(g, i, j);
                }                
            }
        }
    }
    
    public void drawCoin(Graphics g, int x, int y) {
        final int cellWH = getCellWH();
        CoinInterface coin = getCoin(game.getPlayer(x, y));
        if (coin != null) {
            coin.drawCoin(g , x * cellWH + blank , y * cellWH + blank, cellWH);
        }
    } 
    
    private void resizeCoins() {
        for (CoinInterface coin: coins.values()) {
            coin.setSize((getWidth() - 3 * blank * cellsX) / cellsX);                
        }
    }
        
    private void clickOnBord(MouseEvent e) {                
        final int cellWH = getCellWH(); 
        final int cellX = (e.getX() - blank) / cellWH;
        final int cellY = (e.getY() - blank) / cellWH;
        fireBoardClickEvent(new BoardClickEvent(this, cellX, cellY));
    }

    private int getCellWH() {
        return (getWidth() - blank * 2) / cellsX;
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
}
