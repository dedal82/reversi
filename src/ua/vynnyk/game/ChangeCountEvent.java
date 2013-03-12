/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.game;

import java.util.EventObject;

/**
 *
 * @author Admin
 */
public class ChangeCountEvent extends EventObject {
    private int first;
    private int second;

    public ChangeCountEvent(Object o, int first, int second) {
        super(o);
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }        
}
