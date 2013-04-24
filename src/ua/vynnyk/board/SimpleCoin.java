/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;

/**
 *
 * @author dedal
 */
public class SimpleCoin extends JComponent implements CoinInterface {
    private Color color = Color.BLUE;
        
    private SimpleCoin() {        
    }
    
    public SimpleCoin(Color color) {
        this.color = color;
    }

    @Override
    protected void paintComponent(Graphics g) { 
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.fillOval(0, 0, getWidth(), getHeight());
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
