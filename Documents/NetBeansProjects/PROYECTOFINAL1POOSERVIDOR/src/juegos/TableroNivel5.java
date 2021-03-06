/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static juegos.Tablero.HEIGHT;

/**
 *
 * @author USER
 */
public class TableroNivel5 extends Tablero{

public TableroNivel5() {
        try {
            Scanner lectura = new Scanner(new File("nivel7.txt"));
            int aux;
            for (int y=0;y<HEIGHT;y++) {
                for(int x=0;x<WIDTH;x++) {
                    tableroMatriz[x][y]=BLOCKED;
                    aux=lectura.nextInt();
                    if(aux==1){
                        tableroMatriz[x][y] = BLOCKED;
                        
                        
                    }else{
                        tableroMatriz[x][y] = CLEAR;
                        
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TableroNivel7.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        protected void paint(Graphics2D g, int i, int j) {
		// loop through all the tiles in the map rendering them
		// based on whether they block or not
		for (int x=0;x<WIDTH;x++) {
			for (int y=0;y<HEIGHT;y++) {
                        /*if (tableroMatriz[x][y] == BLOCKED) {
					g.setColor(Color.blue);
				}*/
				g.setColor(Color.green);//fondo de la matriz
				if (x >= i - 9 && x <= i + 9 && y >= j - 9 && y <= j + 9) {
					g.setColor(Color.white);
                                        if(blocked(x, y)) {
                                            g.setColor(Color.green);
                                        }
				}				
				// draw the rectangle with a dark outline
				g.fillRect(x*TILE_SIZE,y*TILE_SIZE,TILE_SIZE,TILE_SIZE);
				g.setColor(g.getColor().darker());
				g.drawRect(x*TILE_SIZE,y*TILE_SIZE,TILE_SIZE,TILE_SIZE);
			}
		}
	}
}
