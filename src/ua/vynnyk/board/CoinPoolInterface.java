/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.board;

import java.awt.Color;

/**
 *
 * @author vynnyk
 */
public interface CoinPoolInterface {
    public CoinInterface getCoin();
    public void setColor(Color color);
    public Color getColor();
}
