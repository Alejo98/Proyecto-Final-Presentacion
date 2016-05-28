package juegos;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * A single entity moving round our map. It maintains its position 
 * in terms of grid cells. Note that the positions are floating 
 * point numbers allowing us to be partially across a cell.
 * 
 * @author Kevin Glass
 */
public class Entity {
	
	private float x;
	private float y;
	private Image image;
	private Tablero map;
	private float ang;
	private float size = 0.3f;
	
	public Entity(Image image, Tablero map, float x, float y) {
		this.image = image;
		this.map = map;
		this.x = x;
		this.y = y;
	}
        
        public boolean move(float dx, float dy) {

		float nx = x + dx;
		float ny = y + dy;
		

		
		if (validLocation(nx, ny)) {
			// if it doesn't then change our position to the new position
			x = nx;
			y = ny;
			
			// and calculate the angle we're facing based on our last move
			ang = (float) (Math.atan2(dy, dx) - (Math.PI / 2));
			return true;
		}
		
		// if it wasn't a valid move don't do anything apart from 
		// tell the caller
		return false;
	}
	
	
	public boolean validLocation(float nx, float ny) {
		if (map.blocked(nx - size, ny - size)) {
			return false;
		}
		if (map.blocked(nx + size, ny - size)) {
			return false;
		}
		if (map.blocked(nx - size, ny + size)) {
			return false;
		}
		if (map.blocked(nx + size, ny + size)) {
			return false;
		}
		
		// if all the points checked are unblocked then we're in an ok
		// location
		return true;
	}
	
	
	public void paint(Graphics2D g) {
	        int contador=1;
                Tablero Nivel;
                int xp = (int) (Tablero.TILE_SIZE * x);
		int yp = (int) (Tablero.TILE_SIZE * y);
		g.rotate(ang, xp, yp);
		g.drawImage(image, (int) (xp - 16), (int) (yp - 16), null);
                g.drawRect(xp-8, yp-8,15 ,20);
                g.rotate(-ang, xp, yp);
	}
        public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN: y += 1; break;
            case KeyEvent.VK_UP: y -= 1; break;
            case KeyEvent.VK_LEFT: x -= 1; break;
            case KeyEvent.VK_RIGHT: x += 1; break;              
        }
    }
        public Rectangle obRectangle(){
            int xp = (int) (Tablero.TILE_SIZE * x);
            int yp = (int) (Tablero.TILE_SIZE * y);
            return new Rectangle(xp-8, yp-8,15 ,20);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
        

  
        
}
