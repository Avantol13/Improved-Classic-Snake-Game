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
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

import core.Snake;
// TODO: Auto-generated Javadoc

/**
 * The Class Shrinker.
 */
public class Shrinker extends Item{
	
	/** The item image. */
	private static Image itemImage;
	
	/** The g2d. */
	public Graphics2D g2d;
    
   // Default constructor.
   /**
    * Instantiates a new shrinker.
    */
   public Shrinker() {
      super();
      try {
    	  	//item image
			image = ImageIO.read(getClass().getResource("images/Shrinker.png"));
			itemImage = image;
		} catch (IOException e) { }
      numUses = 1;
   }
   
   /* (non-Javadoc)
    * @see items.Item#use(core.Snake)
    */
   public void use(Snake snake)
   {
	   int points = 0;
	   int length = game.getSnake().getLength();
	   
	   for (int i = 0; i < length - 1; i++)
	   {
		   points += snake.getLength() * 25;
		   game.getSnake().shrink();
	   }
	   game.setAddToScore(points);
	   game.setScore(game.getScore() + game.getAddToScore());
	   
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
