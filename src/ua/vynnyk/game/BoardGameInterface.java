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
    public boolean doMove(GameCell cell);
    public EnumPlayer getPlayer(GameCell cell);
    public void UndoMove();
    public GameCell getBestMove();
    public GameCell doAIMove();
    public int getWidth();
    public int getHeight();
    public void setOption(String option, Object value);
    public Object getOption(String option);
    public void addGameOverListener(GameOverEventListener listener);
    public void removeGameOverListener(GameOverEventListener listener);
    public void addPutCoinListener(PutCoinEventListener listener);    
    public void removePutCoinListener(PutCoinEventListener listener);
    public void addChangeCountListener(ChangeCountEventListener listener);    
    public void removeChangeCountListener(ChangeCountEventListener listener);
}
