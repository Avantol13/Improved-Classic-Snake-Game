/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package items;
import java.awt.*;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

import core.Window;
/**
 * Food is a food item that the snake can eat. It is placed randomly in the pit.
 */
public class Food {
   
   /** The y. */
   protected int x, y;   // current food location (x, y) in cells
   
   /** The color. */
   protected Color color = Color.GREEN;   // color for display
   
   /** The rand. */
   protected Random rand = new Random(); // For randomly placing the food
   
   /** The food count. */
   static int foodCount = 0; //amount of food that's been generated
   
   /** The image. */
   protected Image image;
   
   
   /**
    * Instantiates a new food.
    */
   public Food() {
      // place outside the pit, so that it will not be "displayed".
      x = -10;
      y = -10;
      
      try {
			image = ImageIO.read(getClass().getResource("images/food.png"));
		} catch (IOException e) { }
   }
   
   //*** methods to deal with food count
   /**
    * Increase food count.
    *
    * @param increase the increase
    */
   public void increaseFoodCount(int increase)
   {
      this.foodCount += increase;
   }
   
   /**
    * Gets the food count.
    *
    * @return the food count
    */
   public int getFoodCount()
   {
      return foodCount;
   }
   
    
   /**
    * Regenerate a food item. Randomly place inside the pit (slightly off the edge)..
    */
   public void regenerate() {
         x = rand.nextInt(Window.COLUMNS - 5) + 2;
         y = rand.nextInt(Window.ROWS - 10) + 2; //8 because of hud on bottom
         foodCount++;  
   }
   
   /**
    * Erase.
    */
   public void erase()
   {
      x = -1;
      y = -1;
   }
   
   
    
   /**
    * Returns the x coordinate of the cell that contains this food item.
    * 
    * @return the x
    */
   public int getX() { 
      return x; }
   
   
   /**
    * Returns the y coordinate of the cell that contains this food item.
    *
    * @return the y
    */
   public int getY() { 
      return y; }
   
   
   /**
    * Draw itself.
    *
    * @param g2d the g2d
    */
   public void draw(Graphics2D g2d) {
      g2d.drawImage(image,
   	       x * Window.CELL_SIZE, y * Window.CELL_SIZE, 
   	       (x * Window.CELL_SIZE + Window.CELL_SIZE), (y * Window.CELL_SIZE + Window.CELL_SIZE),
   	       0, image.getHeight(null), image.getWidth(null), 0,
   	       null);
   }
}