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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;
import juegos.*;
import static sun.audio.AudioPlayer.player;
        

/**
 *
 * @author USER
 */
public class Juegos extends Canvas implements KeyListener,ActionListener{
    
    private Tablero tablero;
    private Timer timer;
    private BufferStrategy strategy;
    private Entity personajeJugador1;
    private Entity personajeJugador2;
    private Socket jugador ;
    private Image sprite;
    private ArrayList<Esfera> esfera;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private DataInputStream entrada;
    private DataOutputStream salida;
    

    public Juegos() throws IOException{
        this.setFocusable(true);
        this.addKeyListener(this);
        sprite = loadImage("sprite.gif");
        JFrame frame=new JFrame();
        frame.setSize(800,750);
        frame.setTitle("JUGADOR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1000,1000);
        frame.add(this);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addKeyListener(this);
        tablero=new TableroNivel1();
        this.personajeJugador1 = new Entity(sprite,tablero,1.5f, 1.5f);
        this.personajeJugador2= new Entity(sprite,tablero,1.5f, 1.5f);
        this.esfera=new ArrayList<Esfera>();
        this.esfera.add(new Esfera(20,50));
        jugador=new Socket("localhost",8000);
        System.out.println("LA CONEXION ESTA LISTA");
        this.salida = new DataOutputStream(jugador.getOutputStream());
        this.entrada = new DataInputStream (jugador.getInputStream());
        Thread proceso1 = new Thread(new Hilo(jugador,personajeJugador2));
        proceso1.start();
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        gameLoop();
        this.timer = new Timer(50,this);
        this.timer.start();
    }
    public void gameLoop() {
		boolean gameRunning = true;
		long last = System.nanoTime();
                int level = 1;
		while (gameRunning) {
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.lightGray);
			g.fillRect(0,0,1000,800);
			g.translate(0,0);
			tablero.paint(g, (int)(personajeJugador1.getX()), (int)(personajeJugador1.getY()));
                        this.personajeJugador1.paint(g);
                       // tablero.paint(g, (int)(personajeJugador2.getX()), (int)(personajeJugador2.getY()));
                        //personajeJugador2.paint(g);
                        
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
		
		if ((dx != 0) || (dy != 0)) {
			 personajeJugador1.move(dx * delta * 0.3f,dy * delta * 0.3f);
		}
	}
	
	/**
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
	}
        
	public void keyPressed(KeyEvent e) {
        try {
            int codigo=-1;
            int key = e.getKeyCode();
            
            if (key == KeyEvent.VK_SPACE) {
                codigo=1;
            }
            
            if (key == KeyEvent.VK_LEFT) {
                codigo=1;
            }
            
            if (key == KeyEvent.VK_RIGHT) {
                codigo=2;
            }
            
            if (key == KeyEvent.VK_UP) {
                codigo=3;
            }
            
            if (key == KeyEvent.VK_DOWN) {
                codigo=4;
            }
            this.personajeJugador1.keyPressed(e);
            this.salida.writeInt(codigo);
            this.salida.flush();
        } catch (IOException ex) {
            Logger.getLogger(Juegos.class.getName()).log(Level.SEVERE, null, ex);
        }
		
	}

	
	public void keyReleased(KeyEvent e) {
		// check the keyboard and record which keys are released
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = false;
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
    

