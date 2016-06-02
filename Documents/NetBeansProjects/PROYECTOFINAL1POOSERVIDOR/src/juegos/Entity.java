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
	/** The x position of this entity in terms of grid cells */
	public float x;
	/** The y position of this entity in terms of grid cells */
	public float y;
	/** The image to draw for this entity */
	private Image image;
	/** The map which this entity is wandering around */
	private Tablero map;
	/** The angle to draw this entity at */
	private float ang;
	/** The size of this entity, this is used to calculate collisions with walls */
	private float size = 0.3f;
        private float extra1=0;
        private float extra2=0;
	
	/**
	 * Create a new entity in the game
	 * 
	 * @param image The image to represent this entimage, TableroNivel1 map, ity (needs to be 32x32)
	 * @param map The map this entity is going to wander around
	 * @param x The initial x position of this entity in grid cells
	 * @param y The initial y position of this entity in grid cells
	 */
	public Entity(Image image, Tablero map, float x, float y) {
		this.image = image;
		this.map = map;
		this.x = x;
		this.y = y;
	}
        public void setExtra1(float extra1) {
        this.extra1 = extra1;
        }

        public void setExtra2(float extra2) {
        this.extra2 = extra2;
        }
	
	/**
	 * Move this entity a given amount. This may or may not succeed depending
	 * on collisions
	 * 
	 * @param dx The amount to move on the x axis
	 * @param dy The amount to move on the y axis
	 * @return True if the move succeeded
	 */
	public boolean move(float dx, float dy) {
		// work out what the new position of this entity will be
		float nx = x+dx ; 
                float ny = y+dy ;
                if(extra1!=0&&dx!=0){
                    System.out.println("aw");
                    if(dx<0){
                         System.out.println("awd");
                    nx = x+dx-extra1 ;
                    }
                    
                    else if(dx>0)
                    nx = x+dx+extra1;
                }
                if(extra1!=0&&dy!=0){
                    if(dy<0)
                    ny = y+dy-extra1 ;
                    else if(dy>0)
                    ny = y+dy+extra1;
                }
               
		
		// check if the new position of the entity collides with
		// anything
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
	
	/**
	 * Check if the entity would be at a valid location if its position
	 * was as specified
	 * 
	 * @param nx The potential x position for the entity
	 * @param ny The potential y position for the entity
	 * @return True if the new position specified would be valid
	 */
	public boolean validLocation(float nx, float ny) {
		// here we're going to check some points at the corners of
		// the player to see whether we're at an invalid location
		// if any of them are blocked then the location specified
		// isn't valid
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
	
	/**
	 * Draw this entity to the graphics context provided.
	 * 
	 * @param g The graphics context to which the entity should be drawn
	 */
	public void paint(Graphics2D g) {
		// work out the screen position of the entity based on the
		// x/y position and the size that tiles are being rendered at. So
		// if we're at 1.5,1.5 and the tile size is 10 we'd render on screen 
		// at 15,15.
                int contador=1;
                Tablero Nivel;
                int xp = (int) (Tablero.TILE_SIZE * x);
		int yp = (int) (Tablero.TILE_SIZE * y);
		
		// rotate the sprite based on the current angle and then
		// draw it
		g.rotate(ang, xp, yp);
		g.drawImage(image, (int) (xp - 16), (int) (yp - 16), null);
                
                g.drawRect(xp-8, yp-8,15 ,20);
                g.rotate(-ang, xp, yp);
	}
        public Rectangle obRectangle(){
            int xp = (int) (Tablero.TILE_SIZE * x);
            int yp = (int) (Tablero.TILE_SIZE * y);
            return new Rectangle(xp-8, yp-8,15 ,20);
    }
        /**public void keyPressed (KeyEvent e){
            switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN: y += 0.003; break;
            case KeyEvent.VK_UP: y -= 0.003; break;
            case KeyEvent.VK_LEFT: x -= 0.003; break;
            case KeyEvent.VK_RIGHT: x += 0.003; break;              
        }
        }*/

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
