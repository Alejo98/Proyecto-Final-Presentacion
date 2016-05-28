/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegos;

import java.awt.Color;
import java.awt.Graphics2D;
import static juegos.Tablero.WIDTH;

/**
 *
 * @author USER
 */
public class TableroNivel3 extends Tablero {
    public TableroNivel3(){
        for (int y=0;y<HEIGHT;y++) {
                    for(int x=0;x<WIDTH;x++) {
                    System.out.println(WIDTH);
                    tableroMatriz[x][y]=BLOCKED;
                    }
                   /* if(y>0 && y<=20)
                    tableroMatriz[1][y]=CLEAR;
                    
                    if(y>7 && y<=18)
                        tableroMatriz[8][y]=CLEAR;
                    
                    if(y>11 && y<=18)
                        tableroMatriz[12][y]=CLEAR;
                    if(y>15 && y<=21)
                        tableroMatriz[10][y]=CLEAR;
                    if(y>20 && y<=24)
                        tableroMatriz[14][y]=CLEAR;
                    if(y>18 && y<=29)
                        tableroMatriz[25][y]=CLEAR;
                    if(y>0&&y<=14)
                        tableroMatriz[13][y]=CLEAR;*/
                    if(y>=1 && y<=28)
                       tableroMatriz[1][y]=CLEAR;
                    if(y>=1 && y<=14)
                        tableroMatriz[13][y]=CLEAR;
                    if(y>=3 && y<=12)
                        tableroMatriz[19][y]=CLEAR;
                    if(y>=1 && y<=2)
                        tableroMatriz[25][y]=CLEAR;
                    if(y>=4 && y<=18)
                        tableroMatriz[9][y]=CLEAR;
                    if(y>=16 && y<=18)
                        tableroMatriz[18][y]=CLEAR;
                    if(y>=8 && y<=12)
                        tableroMatriz[15][y]=CLEAR;
                    if(y>=6 && y<=12)
                        tableroMatriz[22][y]=CLEAR;
                    if(y>=8 && y<=15)
                        tableroMatriz[27][y]=CLEAR;
                    if(y>=15 && y<=17)
                        tableroMatriz[22][y]=CLEAR;
                    if(y>=2 && y<=4)
                        tableroMatriz[28][y]=CLEAR;
                    if(y>=21 && y<=27)
                       tableroMatriz[3][y]=CLEAR;
                    if(y>=22 && y<=25)
                        tableroMatriz[5][y]=CLEAR;
                    if(y>=22 && y<=27)
                        tableroMatriz[8][y]=CLEAR;
                    if(y>=21 && y<=22)
                        tableroMatriz[12][y]=CLEAR;
                    if(y>=24 && y<=27)
                        tableroMatriz[10][y]=CLEAR;
                    if(y>=21 && y<=27)
                        tableroMatriz[15][y]=CLEAR;
                    if(y>=21 && y<=23)
                        tableroMatriz[18][y]=CLEAR;
                    if(y>=21 && y<=26)
                        tableroMatriz[23][y]=CLEAR;
                    if(y>=21 && y<=24)
                        tableroMatriz[24][y]=CLEAR;
                    if(y>=23 && y<=24)
                        tableroMatriz[27][y]=CLEAR;
                    if(y>=26 && y<=27)
                        tableroMatriz[28][y]=CLEAR;

                    
                
                }
                        
		for (int x=0;x<WIDTH;x++) {
			if ((x > 0) && (x < 25)) {
                            tableroMatriz[x][1]=CLEAR;
                            //tableroMatriz[x][] = CLEAR;
			}
                        /*
                        if (x > 10 && (x < WIDTH-1)) {
                             tableroMatriz[x][20] = CLEAR;
                        }
                         if(x>=8 && x<=13){
                            tableroMatriz[x][12] = CLEAR;
                        }
                        if(x>=8 && x<=11){
                            tableroMatriz[x][17] = CLEAR;
                            tableroMatriz[x][15] = CLEAR;
                        }
                        if(x>=10 && x<=15)
                            tableroMatriz[x][25] = CLEAR;
                        
                        if(x>=20 && x<=25)
                            tableroMatriz[x][25] = CLEAR;
                        
                        if(x>=26 && x<=28)
                            tableroMatriz[x][29] = CLEAR;*/
                        
                        if(x>=13 && x<=21)
                            tableroMatriz[x][14] = CLEAR;
                        if(x>=21 && x<22)
                            tableroMatriz[x][13] = CLEAR;
                        if(x>=15 && x<=22)
                            tableroMatriz[x][12] = CLEAR;
                        if(x>=15 && x<=17)
                            tableroMatriz[x][10] = CLEAR;
                        if(x>=17 && x<=19)
                            tableroMatriz[x][8] = CLEAR;
                        if(x>=19 && x<=26)
                            tableroMatriz[x][4] = CLEAR;
                        if(x>=21 && x<=26)
                            tableroMatriz[x][6] = CLEAR;
                        if(x>=21 && x<=27)
                            tableroMatriz[x][8] = CLEAR;
                        if(x>=21 && x<=22)
                            tableroMatriz[x][10] = CLEAR;
                        if(x>=22 && x<=26)
                            tableroMatriz[x][15] = CLEAR;
                       if(x>=20 && x<=22)
                            tableroMatriz[x][17] = CLEAR;
                        if(x>=9 && x<=13)
                            tableroMatriz[x][4] = CLEAR;
                       if(x>=9 && x<=18)
                            tableroMatriz[x][18] = CLEAR;
                       if(x>=25 && x<=28)
                            tableroMatriz[x][2] = CLEAR;
                       if(x>=1 && x<=15)
                            tableroMatriz[x][27] = CLEAR;
                       if(x>=1 && x<=3)
                            tableroMatriz[x][21] = CLEAR;
                       if(x>=5 && x<=6)
                            tableroMatriz[x][22] = CLEAR;
                       if(x>=1 && x<=5)
                            tableroMatriz[x][23] = CLEAR;
                       if(x>=8 && x<=12)
                            tableroMatriz[x][22] = CLEAR;
                       if(x>=12 && x<=15)
                            tableroMatriz[x][24] = CLEAR;
                       if(x>=16 && x<=18)
                            tableroMatriz[x][23] = CLEAR;
                       if(x>=18 && x<=28)
                            tableroMatriz[x][21] = CLEAR;
                       if(x>=25 && x<=28)
                            tableroMatriz[x][24] = CLEAR;
                       if(x>=18 && x<=28)
                            tableroMatriz[x][26] = CLEAR;
                       if(x>=14 && x<=15)
                            tableroMatriz[x][21] = CLEAR;
                        //tableroMatriz[x][0] = BLOCKED;
			//tableroMatriz[x][HEIGHT-1] = BLOCKED;
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
				g.setColor(Color.WHITE);//fondo de la matriz
				if (x >= i - 2 && x <= i + 2 && y >= j - 2 && y <= j + 2) {
					g.setColor(Color.black);
                                        if(blocked(x, y)) {
                                            g.setColor(Color.white);
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
