/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.board;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author dedal
 */
public class SimpleCoin implements CoinInterface {
    private int size = 50;
    private Color color = Color.BLUE;
    
    public SimpleCoin() {        
    }
    
    public SimpleCoin(Color color) {
        this.color = color;
    }

    public SimpleCoin(int size, Color color) {
        this.size = size;
        this.color = color;
    }
            
    @Override
    public void drawCoin(Graphics g, int x, int y) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
        //g.setColor(Color.BLACK);
        //g.drawOval(x, y, size, size);
    }
    
    @Override
    public void drawCoin(Graphics g, int x, int y, int cellWH) {
        final int i = (cellWH - size) / 2;
        drawCoin(g, x + i, y + i);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    } 

    @Override
    public String toString() {
        return "SimpleCoin{" + "size=" + size + ", color=" + color + '}';
    }            

    @Override
    public int getSize() {
        return size;
    }
}
