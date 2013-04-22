/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.exceptions;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import javax.swing.JOptionPane;

/**
 *
 * @author vynnyk
 */
public class EventQueueProxy extends EventQueue {
 
    @Override
    protected void dispatchEvent(AWTEvent newEvent) {
        try {
            super.dispatchEvent(newEvent);
        } catch (Throwable t) {
            t.printStackTrace();
            String message = t.getMessage();
 
            if (message == null || message.length() == 0) {
                message = "Fatal: " + t.getClass();
            }
 
            JOptionPane.showMessageDialog(null, message, "General Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
