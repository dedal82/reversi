/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.board;

import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 *
 * @author vynnyk
 */
public class Cell extends JPanel {
    private int cellX;
    private int cellY;

    public Cell(int cellX, int cellY, LayoutManager lm) {
        super(lm);
        this.cellX = cellX;
        this.cellY = cellY;
    }    
    
    public int getCellX() {
        return cellX;
    }

    public int getCellY() {
        return cellY;
    }  
      /* for testing only*/ 
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawString(cellX +"," +cellY, 0, 10);
//    }        
}
