/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.game;

import java.util.EventListener;

/**
 *
 * @author vynnyk
 */
public interface PutCoinEventListener extends EventListener {
    public void PutCoin(PutCoinEvent e);
}
