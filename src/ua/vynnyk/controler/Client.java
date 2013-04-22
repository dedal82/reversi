/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.controler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ua.vynnyk.game.GameCell;

/**
 *
 * @author Admin
 */
class Client implements Runnable, RemoteMovable {
    private String host;
    private int port = 8686;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private GameControler controler;

    public Client(String host, GameControler controler) {
        this.host = host;
        this.controler = controler;        
    }

    @Override
    public void run() {
        try {
            socket = new Socket(host, port);            
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Thread(new Listener()).start();
    }

    @Override
    public boolean isConnected() {
        return socket.isConnected();
    }
    
     
    private class Listener implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    GameCell cell = (GameCell) in.readObject();
                    controler.doRemoteMove(cell);
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }        
    }
                         
    @Override
    public void sendMove(GameCell cell) {
        try {
            out.writeObject(cell);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }          
}
