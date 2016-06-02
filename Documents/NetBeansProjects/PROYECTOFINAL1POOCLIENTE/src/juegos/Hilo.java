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
    private Entity player;
    
    public Hilo(Socket socket,Entity personajePrincipal) throws IOException{
      this.socket = socket;
      this.entrada = new DataInputStream(socket.getInputStream());
      this.salida = new DataOutputStream(this.socket.getOutputStream());
      this.player = personajePrincipal;
    }

    

    @Override
    public void run() {
        while(true){
            try {                
               // int recibidos = this.entrada.readInt();
             String Movimiento=this.entrada.readUTF();
             System.out.println(Movimiento);
             String ax[]=Movimiento.split(",");
             if(ax[0].equalsIgnoreCase("Mover")){
                 System.out.println("ss");
                 float x=Float.parseFloat(ax[1]);
                 float dx=x*38*0.003f;
                 float y=Float.parseFloat(ax[2]);
                 float dy=y*38*0.003f;
                 this.player.move(dx,dy);
            
             }
               /** System.out.println(recibidos);
                switch (recibidos) {
                case 1: this.player.setX(this.player.getX() - 1); break;
                case 2: this.player.setX(this.player.getX() + 1); break;
                case 3: this.player.setY(this.player.getY() + 1); break;
                case 4: this.player.setY(this.player.getY()-1); break;
              }*/
                
            } catch (IOException ex) {
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}