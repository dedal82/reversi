/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.controler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import ua.vynnyk.game.GameCell;

/**
 *
 * @author Admin
 */
class Server implements RemoteMovable {
    private int port = 8686;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private BoardGameControlerInterface controler;
    
    public Server(BoardGameControlerInterface controler) {
        this.controler = controler;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Thread(new Listener()).start();
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
