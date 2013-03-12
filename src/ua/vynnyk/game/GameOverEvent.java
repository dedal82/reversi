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
public class GameOverEvent extends EventObject {
    
    private EnumPlayer winner;

    public GameOverEvent(Object o, EnumPlayer winner) {
        super(o);
        this.winner = winner;
    }

    public EnumPlayer getWinner() {
        return winner;
    }
    
}
