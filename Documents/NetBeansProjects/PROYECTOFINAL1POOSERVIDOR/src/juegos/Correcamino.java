/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Felipe
 */
public class Correcamino {
        int x; //posicion en x
	int y; //posicion en y
	private Image image;
	private Tablero map;
	  
        
    public Correcamino(int x, int y,Image image, Tablero map,int saltos) {
        this.x = x;
        this.y = y;
        this.image=image;
        this.map = map;
        
    }
    
     protected Image loadImage(String imageName) {
       ImageIcon ii = new ImageIcon(imageName);
        Image image = ii.getImage();
        return image;
    }
    public void dibujar(Graphics g){
       g.setColor(Color.red);
       g.drawImage(image, x, y, null);
       
    }
     
     public Rectangle obRectangle(){
         return new Rectangle(this.x, this.y, 20, 20);
    }
        
    
    
}
