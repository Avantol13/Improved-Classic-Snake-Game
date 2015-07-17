/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package items;
import java.awt.*;
import java.util.*;

import core.Framework;
import core.Game;
import core.Snake;
import core.Window;
// TODO: Auto-generated Javadoc
/**
 * item is a item item that the snake can eat. It is placed randomly in the pit.
 */
public abstract class Item {
   
   /** The y. */
   protected int x, y;   // current item location (x, y) in cells
   
   /** The rand. */
   protected static Random rand = new Random(); // For randomly placing the item
   
   /** The item count. */
   static int itemCount = 0; //amount of item that's been generated
   
   /** The image. */
   protected Image image;
   
   /** The snake. */
   protected Snake snake; //snake to use item on
   
   /** The game. */
   protected Game game;
   
   /** The num uses. */
   protected int numUses;
   
   // Default constructor.
   /**
    * Instantiates a new item.
    */
   public Item() {
      x = -10;
      y = -10;
      image = null; //initialize image to nothing, added by each of the items
      game = Framework.getGame();
      increaseItemCount();
   }
   
   //*** methods to deal with item count
   /**
    * Increase item count.
    */
   public void increaseItemCount()
   {
      Item.itemCount++;
   }
   
   /**
    * Gets the item count.
    *
    * @return the item count
    */
   public int getitemCount()
   {
      return itemCount;
   }
   
   // Regenerate a item item. Randomly place inside the pit (slightly off the edge).
   /**
    * Regenerate.
    */
   public void regenerate() {
         x = rand.nextInt(Window.COLUMNS - 5) + 2;
         y = rand.nextInt(Window.ROWS - 10) + 2; //10 because of hud on bottom
         itemCount++;
   }
   
   //override this in specific item class
   /**
    * Use.
    *
    * @param snake the snake
    */
   public void use(Snake snake)
   {
	   //DON'T FORGET TO SUBTRACT FROM NUMUSES AT END
   }
   
   //sets up the number of times the item can be used
   /**
    * Checks for uses.
    *
    * @return true, if successful
    */
   public boolean hasUses()
   {
	   if (numUses > 0)
	   {
		   return true;
	   }
	   
	   return false;
   }
   
   /**
    * Gets the num uses.
    *
    * @return the num uses
    */
   public int getNumUses()
   {
	   return numUses;
   }
   
   /**
    * Sets the num uses.
    *
    * @param uses the new num uses
    */
   public void setNumUses(int uses)
   {
	   this.numUses = uses;
   }
   
   //
   /**
    * Erase.
    */
   public void erase()
   {
      x = -1;
      y = -1;
   }
   
   
   // Returns the x coordinate of the cell that contains this item item.
   /**
    * Gets the x.
    *
    * @return the x
    */
   public int getX() { 
      return x; }
   
   /**
    * Sets the x.
    *
    * @param x the new x
    */
   public void setX(int x)
   {
	   this.x = x;
   }
   
   // Returns the y coordinate of the cell that contains this item item.
   /**
    * Gets the y.
    *
    * @return the y
    */
   public int getY() { 
      return y; }
   
   /**
    * Sets the y.
    *
    * @param y the new y
    */
   public void setY(int y)
   {
	   this.y = y;
   }
   
   // Draw itself.
   /**
    * Draw.
    *
    * @param g2d the g2d
    */
   public void draw(Graphics2D g2d) {
	   g2d.drawImage(image, x * Window.CELL_SIZE + Window.CELL_SIZE, y * Window.CELL_SIZE,
   			x * Window.CELL_SIZE, y * Window.CELL_SIZE + Window.CELL_SIZE,
   			image.getWidth(null), 0, 0, image.getHeight(null),
    	        null);
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
	   return "Item at: " + x + ", " + y;
   }
}
