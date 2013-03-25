/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.controler;

import ua.vynnyk.game.BoardGameInterface;
import ua.vynnyk.game.EnumPlayer;
import ua.vynnyk.game.GameCell;

/**
 *
 * @author vynnyk
 */
public class GameControler implements BoardGameControlerInterface {
    private int status;
    private BoardGameInterface game;

    public GameControler(BoardGameInterface game) {
        this.game = game;
        this.status = 1;
    }
            
    @Override
    public boolean doMove(GameCell cell) {        
        if (status == PL_VS_PL) {
            return game.doMove(cell);
        } else if (status == PL_VS_AI) {
            if (game.getActivePlayer() == EnumPlayer.FIRST) { 
                if (game.doMove(cell)) {
                    doAIMove();
                    return true;
                }
            }    
        }
        return false;        
    }

    @Override
    public void setStatus(int i) {
        this.status = i;
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    private void doAIMove() {
        game.doAIMove();
    }
    
}
