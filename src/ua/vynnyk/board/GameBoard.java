package ua.vynnyk.board;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import ua.vynnyk.controler.BoardGameControlerInterface;
import ua.vynnyk.game.BoardGameInterface;
import ua.vynnyk.game.EnumPlayer;
import ua.vynnyk.game.GameCell;
import ua.vynnyk.l10n.TranslateHelper;

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
    private BoardGameControlerInterface controler;

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

    public BoardGameControlerInterface getControler() {
        return controler;
    }

    public void setControler(BoardGameControlerInterface controler) {
        this.controler = controler;
    }
            
    public void setPoolCoin(EnumPlayer player, CoinPoolInterface coin) {
        coins.put(player, coin);
    }   
    
    public CoinInterface getCoin(EnumPlayer player) {       
        return coins.get(player).getCoin();
    } 
                       
    public void drawCoin(int x, int y) {        
        CoinInterface coin = getCoin(game.getPlayer(new GameCell(x, y)));
        cells[x][y].removeAll();
        if (coin != null) {            
            cells[x][y].add((Component) coin);            
        }
        cells[x][y].repaint();
    } 
            
    private void initComponents() {  
        setAlignmentY(TOP_ALIGNMENT);
        setBackground(lineColor);
        final Border border = BorderFactory.createEmptyBorder(blank, blank, blank, blank);
        setBorder(border);
        setLayout(new GridLayout(cellsY, cellsX, blank, blank));
        cells = new Cell[cellsX][cellsY];
        
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actionCellClicked((Cell) e.getSource());                                                
            }             
        };        
        for (int y = 0; y < cellsY; y++) {
            for (int x = 0; x < cellsX; x++) {
                addCell(x, y, border, listener);
            }            
        }                 
    }
        
    private void actionCellClicked(Cell cell) {
        if (!controler.doMove(new GameCell(cell.getCellX(), cell.getCellY()))) {
            JOptionPane.showMessageDialog(null, TranslateHelper.getString("main.errormove"));
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

    public Color getBoardColor() {
        return boardColor;
    }

    public void setBoardColor(Color boardColor) {
        this.boardColor = boardColor;
        for (int i = 0; i < cellsX; i++) {
            for (int j = 0; j < cellsY; j++) {
                cells[i][j].setBackground(boardColor);
            }            
        }        
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        setBackground(lineColor);
    }    
    
    public Color getCoinColor(EnumPlayer player) {
        return coins.get(player).getColor();
    }
    
    public void setCoinColor(EnumPlayer player, Color color) {
        coins.get(player).setColor(color);
    }

    public void refreshCoins() {
        for (int i = 0; i < cellsX; i++) {
            for (int j = 0; j < cellsY; j++) {
                drawCoin(i, j);
            }            
        }
    }
    
    public void blinkCell(int x, int y) {
        final Cell cell = cells[x][y]; 
                
        ActionListener actLs = new ActionListener() {
            private Timer tm  = new Timer(200, this);
            private int loop = 10;
            
            {
                tm.start();
            }            
            @Override
            public void actionPerformed(ActionEvent ae) {
                loop--;
                if (loop % 2 == 0) {
                    cell.setBackground(cell.getBackground().brighter());
                } else {
                    cell.setBackground(boardColor);
                }
                if (loop <= 0) {
                    tm.stop();
                    cell.setBackground(boardColor);
                }
            }
        };                
    }
}
