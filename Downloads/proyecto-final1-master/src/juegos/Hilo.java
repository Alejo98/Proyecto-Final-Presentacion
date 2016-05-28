/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class Hilo implements Runnable{
    private Socket socket ; 
    DataInputStream entrada;
    DataOutputStream salida;
    private Entity personajePrincipal;
    
    public Hilo(Socket socket,Entity personajePrincipal) throws IOException{
      this.socket = socket;
      this.entrada = new DataInputStream(socket.getInputStream());
      this.salida = new DataOutputStream(this.socket.getOutputStream());
      this.personajePrincipal = personajePrincipal;
    }

    

    @Override
    public void run() {
        while(true){
            try {                
                int recibidos = this.entrada.readInt();
                switch (recibidos) {
                case 1: this.personajePrincipal.setX(this.personajePrincipal.getX() - 1 ); break;
                case 2: this.personajePrincipal.setX( this.personajePrincipal.getX() + 1); break;
                case 3: this.personajePrincipal.setY(this.personajePrincipal.getY() - 1); break;
                case 4: this.personajePrincipal.setY(1 + this.personajePrincipal.getY()); break;
              }
                
            } catch (IOException ex) {
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}