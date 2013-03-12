/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.board;

import java.util.EventListener;

/**
 *
 * @author vynnyk
 */
public interface BoardClickEventListener extends EventListener {
    public void BoardClick(BoardClickEvent e);    
}
