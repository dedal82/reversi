/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.board;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author vynnyk
 */
public class SimpleCoinPool implements CoinPoolInterface {
    private Color color = Color.BLUE;;
    private ArrayList<SimpleCoin> pool = new ArrayList<>();

    public SimpleCoinPool() {
    }

    public SimpleCoinPool(Color color) {
        this.color = color;
    }
    
    @Override
    public CoinInterface getCoin() {
        for (SimpleCoin coin: pool) {
            if (coin.getParent() == null) {
                return coin;
            }
        }
        final SimpleCoin coin = new SimpleCoin(color);
        pool.add(coin);
        return coin;        
    }

    @Override
    public void setColor(Color color) {
        pool.clear();
        this.color = color;        
    }  

    @Override
    public Color getColor() {
       return color; 
    }
}
