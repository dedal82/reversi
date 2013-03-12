/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.game;
/**
 *
 * @author dedal
 */
public interface BoardGameInterface {
    public EnumPlayer getActivePlayer();
    public int getCount(EnumPlayer player);
    public void newGame();
    public boolean doMove(int x, int y);
    public EnumPlayer getPlayer(int x, int y);
    public int getWidth();
    public int getHeight();
    public void addGameOverListener(GameOverEventListener listener);
    public void removeGameOverListener(GameOverEventListener listener);
    public void addPutCoinListener(PutCoinEventListener listener);    
    public void removePutCoinListener(PutCoinEventListener listener);
    public void addChangeCountListener(ChangeCountEventListener listener);    
    public void removeChangeCountListener(ChangeCountEventListener listener);
}
