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
    private Socket socket;
    DataInputStream entrada;
    DataOutputStream salida;
    private Entity personajePrincipal;
    private Tablero tablero;

    
    public Hilo(Socket cliente,DataOutputStream salida ,Entity personajePrincipal,Tablero tablero) throws IOException {
        this.socket=cliente;
        this.entrada = new DataInputStream(socket.getInputStream());
        this.salida = salida;
        this.personajePrincipal = personajePrincipal;
        this.tablero=tablero;
        
    }

    @Override
    public void run() {
     while (true){
         try {
             int recibi=this.entrada.readInt();
             switch(recibi){
                 case 1:
                     this.personajePrincipal.setX(this.personajePrincipal.getX() - 1 );                        
                     this.salida.writeInt(recibi);
                     this.salida.flush();
                     break;
                case 2:
                      this.personajePrincipal.setX( this.personajePrincipal.getX() + 1);                        
                      this.salida.writeInt(recibi);
                      this.salida.flush();
                      break;
                    case 3:
                        this.personajePrincipal.setY(this.personajePrincipal.getY() - 1);                         
                        this.salida.writeInt(recibi);
                        this.salida.flush();
                        break;
                    case 4:
                        this.personajePrincipal.setY(1 + this.personajePrincipal.getY());                        
                        this.salida.writeInt(recibi);
                        this.salida.flush();
                        break;
             }
         } catch (IOException ex) {
             Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
         }
     }   
    }
    
}
