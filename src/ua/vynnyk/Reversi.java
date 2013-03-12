/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk;

import javax.swing.JFrame;
import ua.vynnyk.game.BoardGameInterface;
import ua.vynnyk.game.BoardGameRevers;

/**
 *
 * @author vynnyk
 */
public class Reversi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {              
        final BoardGameInterface game = new BoardGameRevers();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameForm(game).setVisible(true);
            }
        });                                              
    }
}
