/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package items;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import core.Window;

// TODO: Auto-generated Javadoc
/**
 * Projectile represents one horizontal or vertical segment. The "head" of
 * this segment is at (headX, headY). The segment is drawn starting from the "head"
 * and proceeding "length" cells in "direction", until it reaches the "tail".
 */
public class Projectile {
   
   /** The head y. */
   protected int headX, headY;   // The position of the head of this segment
   
   /** The length. */
   protected int length;         // length of this segment
   
   /** The direction. */
   protected Projectile.Direction direction;   // direction of this segment
   
   /** The image_ r. */
   protected Image image_U, image_D, image_L, image_R;
   
   /** The shooter. */
   protected int shooter; //  0 - Friendly, means the projectile won't hurt the snake
   						  //  1 - Enemy, means it will hurt it
   /** The damage. */
  						   protected int damage;
   
   /**
    * The Enum Direction.
    */
   public static enum Direction {
	      
      	/** The up. */
      	UP, 
 /** The down. */
 DOWN, 
 /** The left. */
 LEFT, 
 /** The right. */
 RIGHT
	   }
 
   
   // Construct a new Item segment at given (headX, headY), length and direction.
   /**
    * Instantiates a new projectile.
    *
    * @param headX the head x
    * @param headY the head y
    * @param length the length
    * @param direction the direction
    * @param shooter the shooter
    * @param damage the damage
    */
   public Projectile(int headX, int headY, int length, Projectile.Direction direction, int shooter, int damage) {
      this.headX = headX;
      this.headY = headY;
      this.direction = direction;
      this.shooter = shooter;
      this.length = length;
      this.damage = damage;
   }
   
// Construct a new Item segment at given (headX, headY), length and direction.
   /**
 * Instantiates a new projectile.
 *
 * @param headX the head x
 * @param headY the head y
 * @param length the length
 * @param direction the direction
 * @param shooter the shooter
 * @param damage the damage
 * @param image_U the image_ u
 * @param image_D the image_ d
 * @param image_L the image_ l
 * @param image_R the image_ r
 */
public Projectile(int headX, int headY, int length, Projectile.Direction direction, int shooter, int damage,
		   					Image image_U, Image image_D, Image image_L, Image image_R) {
      this.headX = headX;
      this.headY = headY;
      this.direction = direction;
      this.length = length;
      this.damage = damage;
      this.shooter = shooter;
      this.image_U = image_U;
      this.image_D = image_D;
      this.image_L = image_L;
      this.image_R = image_R;
      
   }
  
   //**UPDATE method
   /**
    * Update.
    */
   public void update() //may need to change this update with
						//different types of projectiles
   {
		this.grow();
		this.shrink();
   }
   
// Grow by adding one cell to the head of this segment.
   /**
 * Grow.
 */
public void grow() {
      ++length;
      // need to adjust the headX and headY
      switch (direction) {
         case LEFT:  --headX; break;
         case RIGHT: ++headX; break;
         case UP:    --headY; break;
         case DOWN:  ++headY; break;
      }
   }
   
   //grows by amount
   /**
    * Grow.
    *
    * @param amount the amount
    */
   public void grow(int amount) {
	      length += amount;
	      // need to adjust the headX and headY
	      switch (direction) {
	         case LEFT:  --headX; break;
	         case RIGHT: ++headX; break;
	         case UP:    --headY; break;
	         case DOWN:  ++headY; break;
	      }
	   }
   
   // Shrink by removing one cell from the tail of this segment.
   /**
    * Shrink.
    */
   public void shrink() {
      length--;  // no change in headX and headY needed
   }
   
   /**
    * Shrink.
    *
    * @param amount the amount
    */
   public void shrink(int amount) {
      length -= amount;  // no change in headX and headY needed
   }
   
   // Get the length, in cells, of this segment.
   /**
    * Gets the length.
    *
    * @return the length
    */
   public int getLength() { return length; }
   
   // Get the X coordinate of the cell that contains the head of this snake segment.
   /**
    * Gets the head x.
    *
    * @return the head x
    */
   public int getHeadX() { return headX; }
   
   // Get the Y coordinate of the cell that contains the head of this snake segment.
   /**
    * Gets the head y.
    *
    * @return the head y
    */
   public int getHeadY() { return headY; }
   
   // Get the X coordinate of the cell that contains the tail of this snake segment.
   /**
    * Gets the tail x.
    *
    * @return the tail x
    */
   public int getTailX() {
      if (direction == Projectile.Direction.LEFT) {
         return headX + length - 1;
      } else if (direction == Projectile.Direction.RIGHT) {
         return headX - length + 1;
      } else {
         return headX;
      }
   }
   
   // Get the Y coordinate of the cell that contains the tail of this snake segment.
   /**
    * Gets the tail y.
    *
    * @return the tail y
    */
   public int getTailY() {
      if (direction == Projectile.Direction.DOWN) {
         return headY - length + 1;
      } else if (direction == Projectile.Direction.UP) {
         return headY + length - 1;
      } else {
         return headY;
      }
   }
   
   //**
   /**
    * Gets the direction.
    *
    * @return the direction
    */
   public Projectile.Direction getDirection()
   {
	  return direction;
   }
   
   /**
    * Sets the direction.
    *
    * @param direction the new direction
    */
   public void setDirection(Projectile.Direction direction)
   {
	   this.direction = direction;
   }
   
 //**
   /**
  * Gets the damage.
  *
  * @return the damage
  */
 public int getDamage()
   {
	   return damage;
   }
   
   /**
    * Sets the damage.
    *
    * @param damage the new damage
    */
   public void setDamage(int damage)
   {
	   this.damage = damage;
   }
   
   //**
   /**
    * Gets the shooter.
    *
    * @return the shooter
    */
   public int getShooter()
   {
	   return shooter; //0 means it won't hurt snake, 1 will
   }
   
   /**
    * Sets the shooter.
    *
    * @param shooter the new shooter
    */
   public void setShooter(int shooter)
   {
	   this.shooter = shooter;
   }
   
   // Returns true if the snake segment contains the given cell. Used for collision detection.
   /**
    * Contains.
    *
    * @param x the x
    * @param y the y
    * @return true, if successful
    */
   public boolean contains(int x, int y) {
      switch (direction) {
         case LEFT:  return ((y == this.headY) && ((x >= this.headX) && (x <= getTailX())));
         case RIGHT: return ((y == this.headY) && ((x <= this.headX) && (x >= getTailX())));
         case UP:    return ((x == this.headX) && ((y >= this.headY) && (y <= getTailY())));
         case DOWN:  return ((x == this.headX) && ((y <= this.headY) && (y >= getTailY())));
      }
      return false;
   }
   
   // Draw this segment.
   /**
    * Draw.
    *
    * @param g2d the g2d
    * @param color the color
    */
   public void draw(Graphics g2d, Color color) {
      int x = headX;
      int y = headY;
      g2d.setColor(color);
      
      switch (direction) {
         case LEFT:
            for (int i = 0; i < length; ++i) {
               g2d.fill3DRect(x * Window.CELL_SIZE, y * Window.CELL_SIZE,
                     Window.CELL_SIZE + 1, Window.CELL_SIZE + 1, true);
               ++x;
            }
            break;
         case RIGHT:
            for (int i = 0; i < length; ++i) {
               g2d.fill3DRect(x * Window.CELL_SIZE, y * Window.CELL_SIZE,
                     Window.CELL_SIZE + 1, Window.CELL_SIZE + 1, true);
               --x;
            }
            break;
         case UP:
            for (int i = 0; i < length; ++i) {
               g2d.fill3DRect(x * Window.CELL_SIZE, y * Window.CELL_SIZE,
                     Window.CELL_SIZE + 1, Window.CELL_SIZE + 1, true);
               ++y;
            }
            break;
         case DOWN:
            for (int i = 0; i < length; ++i) {
               g2d.fill3DRect(x * Window.CELL_SIZE, y * Window.CELL_SIZE,
                     Window.CELL_SIZE + 1, Window.CELL_SIZE + 1, true);
               --y;
            }
            break;
      }
   }
   
// Draw this segment.
   /**
 * Draw.
 *
 * @param g2d the g2d
 */
public void draw(Graphics g2d) {
      int x = headX;
      int y = headY;
      
      switch (direction) {
         case LEFT:
            for (int i = 0; i < length; ++i) {
            	g2d.drawImage(image_L,
            	   	       x * Window.CELL_SIZE, y * Window.CELL_SIZE, 
            	   	       (x * Window.CELL_SIZE + Window.CELL_SIZE), (y * Window.CELL_SIZE + Window.CELL_SIZE),
            	   	       0, image_L.getHeight(null), image_L.getWidth(null), 0,
            	   	       null);
               ++x;
            }
            break;
         case RIGHT:
            for (int i = 0; i < length; ++i) {
            	g2d.drawImage(image_R,
            	   	       x * Window.CELL_SIZE, y * Window.CELL_SIZE, 
            	   	       (x * Window.CELL_SIZE + Window.CELL_SIZE), (y * Window.CELL_SIZE + Window.CELL_SIZE),
            	   	       0, image_R.getHeight(null), image_R.getWidth(null), 0,
            	   	       null);
               --x;
            }
            break;
         case UP:
            for (int i = 0; i < length; ++i) {
            	g2d.drawImage(image_D,
            	   	       x * Window.CELL_SIZE, y * Window.CELL_SIZE, 
            	   	       (x * Window.CELL_SIZE + Window.CELL_SIZE), (y * Window.CELL_SIZE + Window.CELL_SIZE),
            	   	       0, image_D.getHeight(null), image_D.getWidth(null), 0,
            	   	       null);
               ++y;
            }
            break;
         case DOWN:
            for (int i = 0; i < length; ++i) {
            	g2d.drawImage(image_U,
            	   	       x * Window.CELL_SIZE, y * Window.CELL_SIZE, 
            	   	       (x * Window.CELL_SIZE + Window.CELL_SIZE), (y * Window.CELL_SIZE + Window.CELL_SIZE),
            	   	       0, image_U.getHeight(null), image_U.getWidth(null), 0,
            	   	       null);
               --y;
            }
            break;
      }
   }
   
   // For debugging.
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return "Head at (" + headX + "," + headY + ")" + " to (" + getTailX() + ","
            + getTailY() + ")" + ", length is " + getLength() + ", dir is " + direction;
   }
}