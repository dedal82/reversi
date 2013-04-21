/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.controler;

import ua.vynnyk.game.GameCell;

/**
 *
 * @author Admin
 */
interface RemoteMovable {
    void sendMove(GameCell cell);    
}
