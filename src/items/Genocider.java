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
import java.util.ArrayList;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

import bosses.Boss_Template;
import core.Framework;
import core.Snake;

// TODO: Auto-generated Javadoc

/**
 * The Class Genocider.
 */
public class Genocider extends Item{
	
	/** The item image. */
	private static Image itemImage;
	
	/** The g2d. */
	public Graphics2D g2d;
    
   // Default constructor.
   /**
    * Instantiates a new genocider.
    */
   public Genocider() {
      super();
      try {
    	  	//item image
			image = ImageIO.read(getClass().getResource("images/Genocider.png"));
			itemImage = image;
		} catch (IOException e) { }
      numUses = 1;
   }
   
   /* (non-Javadoc)
    * @see items.Item#use(core.Snake)
    */
   public void use(Snake snake)
   {
	   //clear enemies
	   Framework.getGame().getEnemies().clear();
	   
	   //do damage to bosses
	   ArrayList<Boss_Template> bosses = Framework.getGame().getBosses();
	   for (int count = 0; count < bosses.size(); count++)
	   {
		   bosses.get(count).damage(100);
	   }
	   numUses--;
   }
   
   /**
  * Gets the item image.
  *
  * @return the item image
  */
 public static Image getItemImage()
   {
	   return itemImage;
   }
   
}
