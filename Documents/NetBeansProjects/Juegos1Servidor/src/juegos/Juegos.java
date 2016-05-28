/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;
import juegos.*;
        

/**
 *
 * @author USER
 */
public class Juegos extends Canvas implements ActionListener{

    
   
    //a diferencia del cliente el cual tienee entrada y salida este solo exporta la salida
    //en la linea 66 se ve como se envia la inormacion a cada cliente 
    private Tablero tablero;
    private Timer timer;
    private BufferStrategy strategy;
    private Entity personajeJugador1;
    private Entity personajeJugador2;
    private Image sprite;
    private ArrayList<Esfera> esfera;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private  ServerSocket serverSocket;
        private Socket Jugador1;
        private Socket Jugador2;
        private int puntajeJugador1 = 0;
        private int puntajeJugador2 = 0;
        DataOutputStream salida1;
        DataOutputStream salida2;
        
        
    public Juegos() throws IOException{
        sprite = loadImage("Personaje.png");
        JFrame frame=new JFrame();
        frame.setSize(800,750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1000,1000);
        frame.add(this);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        tablero=new TableroNivel1();
        this.personajeJugador1 = new Entity(sprite,tablero,1.5f, 1.5f);
        this.personajeJugador2= new Entity(sprite,tablero,1.5f, 1.5f);
        this.serverSocket=new ServerSocket(8000);
        this.esfera=new ArrayList<Esfera>();
        this.esfera.add(new Esfera(20,50));
        Jugador1=this.serverSocket.accept(); // Acepta el jugador 
            //Esperando la conexion del jugador
            this.salida1=new DataOutputStream(Jugador1.getOutputStream());
            System.out.println("CLIENTE 1: CONECTADO");
        Jugador2=this.serverSocket.accept();//Acepta el jugador 
            //Esperando la conexion del jugador
            this.salida2=new DataOutputStream(Jugador2.getOutputStream());
            System.out.println("CLIENTE 2: CONECTADO");
        
        Thread proceso1=new Thread(new Hilo(Jugador1,salida2,personajeJugador1,tablero));
        proceso1.start();
        Thread proceso2=new Thread(new Hilo(Jugador2,salida1,personajeJugador2,tablero));
        proceso2.start();
        this.timer = new Timer(100,this);
         this.timer.start();
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        gameLoop();
    }
    public void gameLoop() {
		boolean gameRunning = true;
		long last = System.nanoTime();
                int level = 1;
		
		// keep looking while the game is running
		while (gameRunning) {
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			
			// clear the screen
			g.setColor(Color.lightGray);
			g.fillRect(0,0,1000,800);
			
			// render our game objects
			g.translate(0,0);
                        
			tablero.paint(g, (int)(personajeJugador1.getX()), (int)(personajeJugador1.getY()));
                        tablero.paint(g, (int)(personajeJugador2.getX()), (int)(personajeJugador2.getY()));
                        personajeJugador1.paint(g,this);
                        personajeJugador2.paint(g,this);
                        if (esfera!=null){
                             for(Esfera esfera:esfera)
                             esfera.dibujar(g,this);
                        }
                       
                        

			// flip the buffer so we can see the rendering
			g.dispose();
			strategy.show();
			
			// pause a bit so that we don't choke the system
			try { Thread.sleep(4); } catch (Exception e) {};
			
			// calculate how long its been since we last ran the
			// game logic
			long delta = (System.nanoTime() - last) / 1000000;
			last = System.nanoTime();
		
			// now this needs a bit of explaining. The amount of time
			for (int i=0;i<delta / 5;i++) {
				logic(5);
			}
			// after we've run through the segments if there is anything
			// left over we update for that
			if ((delta % 5) != 0) {
				logic(delta % 5);
			}
                        
                        if(validarColisiones()) {
                            if(level == 1) {
                                level++;
                                tablero = new TableroNivel2();
                                 this.esfera=new ArrayList<Esfera>();
                                  
                                  this.esfera.add(new Esfera(300,500));
                                  this.esfera.add(new Esfera(20,50));
                            }
                            else if(level==2){
                            level++;
                            tablero=new TableroNivel3();
                             this.esfera=new ArrayList<Esfera>();
                             this.esfera.add(new Esfera(300,200));
                        }
                            
                            personajeJugador1 = new Entity(sprite, tablero, 1.5f, 1.5f);
                            personajeJugador2 = new Entity(sprite, tablero, 1.5f, 1.5f);
                        }
			
		}
	}
    public void logic(long delta) {
		// check the keyboard and record which way the player
		// is trying to move this loop
                 validarColisiones();
		float dx = 0;
		float dy = 0;
		if (left) {
			dx -= 1;
		}
		if (right) {
			dx += 1;
		}
		if (up) {
			dy -= 1;
		}
		if (down) {
			dy += 1;
		}
		
		// if the player needs to move attempt to move the entity
		// based on the keys multiplied by the amount of time thats
		// passed
		if ((dx != 0) || (dy != 0)) {
			personajeJugador1.move(dx * delta * 0.003f, dy * delta * 0.003f);
                        personajeJugador2.move(dx * delta * 0.003f, dy * delta * 0.003f);
		}
	}
	
    
    public static void main(String[] args) throws IOException {
        new Juegos();
    }

    protected Image loadImage(String imageName) {
             ImageIcon ii = new ImageIcon(imageName);
             Image image = ii.getImage();
             return image;
         } 
   
    public boolean validarColisiones(){
                Rectangle recPersonaje=this.personajeJugador1.obRectangle();
                for (Esfera esfera: esfera){
                Rectangle RecEsfera=esfera.obRectangle();
                ArrayList<Esfera> copia=(ArrayList<Esfera>)this.esfera.clone();
                    if(recPersonaje.intersects(RecEsfera)){
                        System.out.println("Colision");
                        copia.remove(esfera);
                        return true;
                    }
                    this.esfera=copia;
                }
                return false;
}

    @Override
    public void actionPerformed(ActionEvent e) {
        validarColisiones();
         for(Esfera esfera: this.esfera)
            esfera.mover();
            repaint();
        
    }
}
    

