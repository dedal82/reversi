/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.game;

import java.util.EventObject;

/**
 *
 * @author vynnyk
 */
public class PutCoinEvent extends EventObject {
    
    private int x;
    private int y;
    private EnumPlayer player;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public EnumPlayer getPlayer() {
        return player;
    }

    public PutCoinEvent(Object o, int x, int y, EnumPlayer player) {
        super(o);
        this.x = x;
        this.y = y;
        this.player = player;
    }

    @Override
    public String toString() {
        return "PutCoinEvent{" + "x=" + x + ", y=" + y + ", player=" + player + '}';
    }
    
}
