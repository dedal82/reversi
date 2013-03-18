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
public class EmptyCoinPool implements CoinPoolInterface {

    @Override
    public CoinInterface getCoin() {
        return null;
    }

    @Override
    public void setColor(Color color) {
    }

    @Override
    public Color getColor() {
       return null; 
    }
    
}
