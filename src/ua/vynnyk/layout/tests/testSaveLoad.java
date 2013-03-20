/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.layout.tests;

import java.io.File;
import ua.vynnyk.game.BoardGameInterface;
import ua.vynnyk.game.BoardGameReversi;
import ua.vynnyk.game.GameUtility;

/**
 *
 * @author vynnyk
 */
public class testSaveLoad {
    public static void main(String[] args) {
        final File file = new File("tmp");
        BoardGameInterface game = new BoardGameReversi();
        game.newGame();
        System.out.println(game);
        GameUtility.saveGame(game, file);
                
        game = GameUtility.loadGame(file);
        
        System.out.println(game);
    }
    
}
