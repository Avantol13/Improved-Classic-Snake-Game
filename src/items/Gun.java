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
 * Food is a food item that the snake can eat. It is placed randomly in the pit.
 */
public class Gun extends Item{
	
	/** The x snake. */
	private int xSnake;
	
	/** The y snake. */
	private int ySnake;
	
	/** The direction. */
	private Projectile.Direction direction;
	
	/** The bullet. */
	private Projectile bullet;
	
	/** The item image. */
	private static Image itemImage;
	
	/** The image_ r. */
	protected Image image_U, image_D, image_L, image_R; //images of projectiles
	
	/** The g2d. */
	public Graphics2D g2d;
    
   // Default constructor.
   /**
    * Instantiates a new gun.
    */
   public Gun() {
      super();
      try {
    	  	//item image
			image = ImageIO.read(getClass().getResource("images/Laser Power Up.png"));
			itemImage = image;
			
			//projectiles images
			image_U = ImageIO.read(getClass().getResource("images/Lightning Ball - U.png"));
			image_D = ImageIO.read(getClass().getResource("images/Lightning Ball - D.png"));
			image_L = ImageIO.read(getClass().getResource("images/Lightning Ball - L.png"));
			image_R = ImageIO.read(getClass().getResource("images/Lightning Ball - R.png"));
		} catch (IOException e) { }
      numUses = 3;
   }
   
   /* (non-Javadoc)
    * @see items.Item#use(core.Snake)
    */
   public void use(Snake snake)
   {
	   if (snake.getDirection() == Snake.Direction.UP)
	   {
		   direction = Projectile.Direction.UP;
		   xSnake = snake.getHeadX();
		   ySnake = snake.getHeadY() - 1;
	   }
	   else if (snake.getDirection() == Snake.Direction.LEFT)
	   {
		   direction = Projectile.Direction.LEFT;
		   xSnake = snake.getHeadX() - 1;
		   ySnake = snake.getHeadY();
	   }
	   else if (snake.getDirection() == Snake.Direction.RIGHT)
	   {
		   direction = Projectile.Direction.RIGHT;
		   xSnake = snake.getHeadX() + 1;
		   ySnake = snake.getHeadY();
	   }
	   else
	   {
		   direction = Projectile.Direction.DOWN;
		   xSnake = snake.getHeadX();
		   ySnake = snake.getHeadY() + 1;
	   }
	   
	   bullet = new Projectile(xSnake, ySnake, 1, direction, 0, 20, image_U, image_D, image_L, image_R);
	   game.getProjectiles().add(bullet);
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
   
// For debugging.
   /* (non-Javadoc)
 * @see items.Item#toString()
 */
@Override
   public String toString() {
	   if (bullet != null)
	   {
		   return "Direction: " + direction + "\n" + 
				   bullet.toString();  
	   }
	   return super.toString();
   }
}