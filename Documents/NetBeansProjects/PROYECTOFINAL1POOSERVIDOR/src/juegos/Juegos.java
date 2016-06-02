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
        

/**
 *
 * @author USER
 */
public class Juegos extends Canvas implements ActionListener{
    private Juegos juego;
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
    
   // private Entity player2;
    
    ///private Socket cliente;
    ///private DataInputStream entrada;
    ///private DataOutputStream salida;
            
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
        private ArrayList<Fantasma2> fantasma2;
        private ArrayList<Correcamino> corredor;
        
    Thread cronometro=new Thread(new ThreadTiempo());

    public Juegos() throws FileNotFoundException{
        try {
            ///this.setFocusable(true);
            ///this.addKeyListener(this);
            sprite = loadImage("sprite.gif");
            fanta= loadImage("ghost.gif");
            fanta= fanta.getScaledInstance(20,20,0);
            Correca=loadImage("Agujero.gif");
            Correca=Correca.getScaledInstance(20, 20, 0);
            JFrame frame=new JFrame();
            frame.setSize(800,750);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(0,0,1000,1000);
            frame.add(this);
            frame.setLayout(null);
            frame.setLocationRelativeTo(null);
            frame.setTitle("SERVIDOR");
            frame.setVisible(true);
          
            tablero=new TableroNivel1();
            this.fantasma2=new ArrayList<Fantasma2>();
            this.fantasma2.add(new Fantasma2(100,100,fanta,tablero,3,400));
            this.fantasma2.add(new Fantasma2(200,200,fanta,tablero,3,250));
            
            
            this.fantasma=new ArrayList<Fantasma>();
            this.fantasma.add(new Fantasma(100,100,fanta,tablero,3,400));
            this.fantasma.add(new Fantasma(200,200,fanta,tablero,3,250));
            
            
            this.corredor=new ArrayList<Correcamino>();
            this.corredor.add(new Correcamino(8*20, 10*20, Correca, tablero,3));
            this.corredor.add(new Correcamino(2*20, 10*20, Correca, tablero,3));
          
            this.player= new Entity(sprite, tablero, 1.5f, 1.5f);
            this.serverSocket=new ServerSocket(8000);
            this.player2= new Entity(sprite, tablero, 1.5f, 1.5f);
            
            this.esfera=new ArrayList<Esfera>();
            //this.esfera.add(new Esfera(300,500));
            this.esfera.add(new Esfera(25*20,20*10));
            
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
            cronometro.start();
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
                         
                        for(Fantasma2 fantasma:fantasma2)
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
                                Thread cronometro=new Thread(new ThreadTiempo());
                                cronometro.start();
                                this.esfera=new ArrayList<Esfera>();
                                 this.esfera.add(new Esfera(20,50));
                                this.esfera.add(new Esfera(560,420));
                                this.corredor=new ArrayList<Correcamino>();
                                this.corredor.add(new Correcamino(9*20, 7*20, Correca, tablero,3));
                                this.corredor.add(new Correcamino(4*20, 19*20, Correca, tablero,3));
                                 this.corredor.add(new Correcamino(15*20, 27*20, Correca, tablero,3));
                                 //this.esfera.add(new Esfera(20,50));
                                 
                            }
                            else if(level==2){
                            level++;
                            tablero=new TableroNivel3();
                            Thread cronometro=new Thread(new ThreadTiempo());
                                cronometro.start();
                             this.fantasma2.removeAll(fantasma2);
                             this.fantasma.remove(1);
                             this.esfera=new ArrayList<Esfera>();
                             this.esfera.add(new Esfera(20,50));
                             this.esfera.add(new Esfera(24*20,20*20));
                             this.corredor=new ArrayList<Correcamino>();
                            this.corredor.add(new Correcamino(11*20, 3*20, Correca, tablero,3));
                            this.corredor.add(new Correcamino(4*20, 21*20, Correca, tablero,3));
                            this.corredor.add(new Correcamino(21*20, 28*20, Correca, tablero,3));
                             this.fantasma2.add(new Fantasma2(400,300,fanta,tablero,10,400));
                             this.fantasma.add(new Fantasma(300,200,fanta,tablero,10,500));
                        } else if(level==3){
                            level++;
                            tablero=new TableroNivel4();
                            Thread cronometro=new Thread(new ThreadTiempo());
                                cronometro.start();
                             this.fantasma.remove(1);
                             this.esfera=new ArrayList<Esfera>();
                             this.esfera.add(new Esfera(540,20));
                             this.esfera.add(new Esfera(20,50));
                             this.corredor=new ArrayList<Correcamino>();
                                this.corredor.add(new Correcamino(21*20, 11*20, Correca, tablero,3));
                                this.corredor.add(new Correcamino(3*20, 4*20, Correca, tablero,3));
                                 this.corredor.add(new Correcamino(24*20, 23*20, Correca, tablero,3));
                             this.fantasma.add(new Fantasma(300,200,fanta,tablero,10,500));
                             this.fantasma.add(new Fantasma(300,350,fanta,tablero,10,500));
                        }else if(level==4){
                            level++;
                            tablero=new TableroNivel5();
                            Thread cronometro=new Thread(new ThreadTiempo());
                                cronometro.start();
                             this.fantasma.remove(1);
                             this.esfera=new ArrayList<Esfera>();
                             this.esfera.add(new Esfera(28*20,28*20));
                             this.esfera.add(new Esfera(20,50));
                             this.fantasma.add(new Fantasma(400,200,fanta,tablero,10,500));
                        }else if(level==5){
                            level++;
                            tablero=new TableroNivel6();
                            Thread cronometro=new Thread(new ThreadTiempo());
                                cronometro.start();
                             this.esfera=new ArrayList<Esfera>();
                             this.esfera.add(new Esfera(28*20,28*20));
                             this.esfera.add(new Esfera(50,50));
                             this.fantasma.add(new Fantasma(500,250,fanta,tablero,10,500));
                        }else if(level==6){
                            level++;
                            tablero=new TableroNivel7();
                            Thread cronometro=new Thread(new ThreadTiempo());
                                cronometro.start();
                             this.esfera=new ArrayList<Esfera>();
                             this.esfera.add(new Esfera(28*20,28*20));
                             this.esfera.add(new Esfera(50,50));
                             this.fantasma.add(new Fantasma(100,200,fanta,tablero,10,500));
                             
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
                    this.player.setExtra1(0.01f);
                    this.player.setExtra2(0.01f);
                    this.player.move(dx * delta * 0.0003f, dy * delta * 0.0003f);
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
                Thread sonido=new Thread(new ReproducirSonido());
                sonido.start();
                
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
                        cronometro.stop();
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
        
        for(Fantasma2 fantasma: this.fantasma2)
            fantasma.move();
        
         for(Esfera esfera: this.esfera)
            esfera.mover();
            repaint();
        
    }
}
    

