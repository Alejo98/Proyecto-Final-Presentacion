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
    private Entity player;
    private Tablero tablero;
    private Juegos juegos;

    
    public Hilo(Socket cliente,DataOutputStream salida ,Entity personajePrincipal,Tablero tablero) throws IOException {
        this.socket=cliente;
        this.entrada = new DataInputStream(socket.getInputStream());
        this.salida = salida;
        this.player = personajePrincipal;
        this.tablero=tablero;
        this.juegos=juegos;
        
    }

    @Override
    public void run() {
     while (true){
         try {
            //long recibi=this.entrada.readLong();
            //int recibi=this.entrada.readInt();
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
             this.salida.writeUTF(Movimiento);
             this.salida.flush();
             }else if(ax[0].equalsIgnoreCase("CN")){
                  int a=Integer.parseInt(ax[1]);
                  System.out.println(a);
          
             }
             
             /**System.out.println(recibi);
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
                        this.personajePrincipal.setY(this.personajePrincipal.getY() +1);                         
                        this.salida.writeInt(recibi);
                        this.salida.flush();
                        break;
                    case 4:
                        this.personajePrincipal.setY( this.personajePrincipal.getY()-1);                        
                        this.salida.writeInt(recibi);
                        this.salida.flush();
                        break;
             }*/
         } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
         }
     }   
    }
    

    
}

       
