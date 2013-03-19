/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Save and load game to/from file
 * @author vynnyk
 */
public class GameUtility {
    
    /**
     * Save game to file
     * @param game - game to save
     * @param file - saving file
     */
    public static void saveGame(BoardGameInterface game, File file) {
        final AbstractBoardGame g = (AbstractBoardGame) game;  
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(g);
        } catch (IOException ex) {
            Logger.getLogger(GameUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Load game from file
     * @param file - file to load
     * @return - loaded game
     */
    public static BoardGameInterface loadGame(File file) {
        AbstractBoardGame g = null;  
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            g = (AbstractBoardGame) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(GameUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
}
