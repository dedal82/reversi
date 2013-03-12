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
public interface CoinInterface {
    public void drawCoin(Graphics g, int x, int y);
    public void drawCoin(Graphics g, int x, int y, int cellWH);
    public void setColor(Color color);
    public void setSize(int size);
    public int getSize();
}
