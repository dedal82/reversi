/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.board;

import java.util.EventObject;

/**
 *
 * @author vynnyk
 */
public class BoardClickEvent extends EventObject {
    
    private int x;
    private int y;

    public BoardClickEvent(Object o, int x, int y) {
        super(o);
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
