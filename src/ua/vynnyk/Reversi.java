/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk;

import java.util.Locale;
import ua.vynnyk.controler.BoardGameControlerInterface;
import ua.vynnyk.controler.GameControler;
import ua.vynnyk.game.BoardGameInterface;
import ua.vynnyk.game.BoardGameReversi;
import ua.vynnyk.translations.TranslateHelper;

/**
 *
 * @author vynnyk
 */
public class Reversi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {            
        final BoardGameInterface game = new BoardGameReversi();  
        final BoardGameControlerInterface controler = new GameControler(game);
                        
        TranslateHelper.setResources(new Locale(Options.getOption("Locale")));
                        
        setLookAndFeel();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameForm(game, controler).setVisible(true);
            }
        });                                              
    }
    
    private static void setLookAndFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reversi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
