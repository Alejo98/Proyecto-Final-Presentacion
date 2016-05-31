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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;
import juegos.*;
        

/**
 *
 * @author USER
 */
public class Juegos extends Canvas implements ActionListener{
    private Tablero tablero;
    private Timer timer;
    private BufferStrategy strategy;
    private Entity player;
    private Entity player2;
    private ServerSocket serverSocket;
    private Socket cliente1;
    private Socket cliente2;
    
    public DataOutputStream salida1;
    public DataOutputStream salida2;
    
    
    private Image sprite;
    private Image fanta;
    private Image Correca;
    
    private ArrayList<Esfera> esfera;
    	private boolean left;
	/** True if the right key is currently pressed */
	private boolean right;
	/** True if the up key is currently pressed */
	private boolean up;
	/** True if the down key is currently pressed */
	private boolean down;
        private ArrayList<Fantasma> fantasma;
        private ArrayList<Correcamino> corredor;

    public Juegos() throws FileNotFoundException{
        try {
            sprite = loadImage("sprite.gif");
            fanta= loadImage("ghost.gif");
            fanta= fanta.getScaledInstance(20,20,0);
            Correca=loadImage("personaje.jpg");
            Correca=Correca.getScaledInstance(20, 20, 0);
            JFrame frame=new JFrame();
            frame.setSize(800,750);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(0,0,1000,1000);
            frame.add(this);
            frame.setTitle("SERVIDOR");
            frame.setLayout(null);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            tablero=new TableroNivel1();
            this.fantasma=new ArrayList<Fantasma>();
            this.fantasma.add(new Fantasma(100,100,fanta,tablero,3));
            this.fantasma.add(new Fantasma(200,200,fanta,tablero,3));
            this.corredor=new ArrayList<Correcamino>();
            this.corredor.add(new Correcamino(20, 100, Correca, tablero,3));
            this.corredor.add(new Correcamino(20, 10, Correca, tablero,3));
            
            this.player= new Entity(sprite, tablero, 1.5f, 1.5f);
            this.serverSocket=new ServerSocket(8000);
            this.player2= new Entity(sprite, tablero, 1.5f, 1.5f);
            
            this.esfera=new ArrayList<Esfera>();
            //this.esfera.add(new Esfera(300,500));
            this.esfera.add(new Esfera(20,100));
            
            cliente1=this.serverSocket.accept();
            this.salida1=new DataOutputStream(cliente1.getOutputStream());
            System.out.println("Acepte cliente 1");
            
            cliente2=this.serverSocket.accept();
            this.salida2=new DataOutputStream((cliente2.getOutputStream()));
            System.out.println("Acepte cliente 2");
            
            Thread proceso1=new Thread(new Hilo(cliente1,salida2,player,tablero)); 
            proceso1.start();
            Thread proceso2=new Thread(new Hilo(cliente2,salida1,player2,tablero));
            proceso2.start();
            
            
            this.timer = new Timer(100,this);
            this.timer.start();
            createBufferStrategy(3);
            strategy = getBufferStrategy();
            gameLoop();
        } catch (IOException ex) {
            Logger.getLogger(Juegos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void gameLoop() throws FileNotFoundException {
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
			g.translate(100,20);
                        
			tablero.paint(g, (int)(player.x), (int)(player.y));
                        this.player.paint(g);
                        this.player2.paint(g);
                        
                        
                        for(Fantasma fantasma:fantasma)
                        fantasma.dibujar(g);
                        
                        for(Correcamino corredor:corredor)
                        corredor.dibujar(g);
                        
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
                            
                            player = new Entity(sprite, tablero, 1.5f, 1.5f);
                        }
                        if (muerteFantasma()){
                                player = new Entity(sprite, tablero, 1.5f, 1.5f);
                            
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
                if (trampa()){
                    player.move(dx * delta * 0.0003f, dy * delta * 0.0003f);
                }else{{
                    if ((dx != 0) || (dy != 0)) {
			player.move(dx * delta * 0.003f, dy * delta * 0.003f);
                        
                
                    }
                }
	}
    }
	
	/**
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */

    /**
     *
     * @param e
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    

	
   

	
    
    

    protected Image loadImage(String imageName) {
             ImageIcon ii = new ImageIcon(imageName);
             Image image = ii.getImage();
             return image;
         } 
    public boolean trampa(){
        Rectangle recPersonaje=this.player.obRectangle();
          for (Correcamino correcamino: corredor){
                  Rectangle recCorredor=correcamino.obRectangle();
                    if(recPersonaje.intersects(recCorredor))
                    return true;
                    }
        return false;
    }
    public boolean muerteFantasma(){
        Rectangle recPersonaje=this.player.obRectangle();
        for(Fantasma fantasma:fantasma){
            Rectangle recFantasma=fantasma.obRectangle();
            if(recPersonaje.intersects(recFantasma)){
                    return true;
                }
        }
        return false;
    }
    public boolean validarColisiones(){
                Rectangle recPersonaje=this.player.obRectangle();
                for (Esfera esfera: esfera){
                Rectangle RecEsfera=esfera.obRectangle();
                ArrayList<Esfera> copia=(ArrayList<Esfera>)this.esfera.clone();
                    if(recPersonaje.intersects(RecEsfera)){
                        
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
        for(Fantasma fantasma: this.fantasma)
            fantasma.move();
         for(Esfera esfera: this.esfera)
            esfera.mover();
            repaint();
        
    }
}
    

