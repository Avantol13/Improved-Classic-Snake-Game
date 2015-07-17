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
import java.util.Random;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

import core.Framework;
import core.Snake;
// TODO: Auto-generated Javadoc

/**
 * The Class Invincibility.
 */
public class Invincibility extends Item{
	
	/** The item image. */
	private static Image itemImage;
	
	/** The g2d. */
	public Graphics2D g2d;
    
   // Default constructor.
   /**
    * Instantiates a new invincibility.
    */
   public Invincibility() {
      super();
      try {
    	  	//item image
			image = ImageIO.read(getClass().getResource("images/Invincibility.png"));
			itemImage = image;
		} catch (IOException e) { }
      
      Random rand = new Random();
      if (rand.nextInt(10) + 1 > 7) //may have one or 2 uses
    	  numUses = 2;
      else
    	  numUses = 1;
   }
   
   /* (non-Javadoc)
    * @see items.Item#use(core.Snake)
    */
   public void use(Snake snake)
   {
	   if (game.getSnake().getState() == 0)
		   game.getSnake().setState(1);
	   else if (game.getSnake().getState() == 1)
		   game.getSnake().addToCount((int) (-12 * Framework.getGAME_FPS()));
	   
	   numUses--;
   }
   
 //**
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
