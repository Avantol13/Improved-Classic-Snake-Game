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
 * The Class SuperLaser.
 */
public class SuperLaser extends Item{
	
	/** The x snake. */
	private int xSnake;
	
	/** The y snake. */
	private int ySnake;
	
	/** The direction. */
	private Projectile.Direction direction;
	
	/** The laser bolt3. */
	private Projectile laserBolt1, laserBolt2, laserBolt3;
	
	/** The item image. */
	private static Image itemImage;
	
	/** The image_ r. */
	protected Image image_U, image_D, image_L, image_R; //images for projectiles
	
	/** The g2d. */
	public Graphics2D g2d;
    
   // Default constructor.
   /**
    * Instantiates a new super laser.
    */
   public SuperLaser() {
      super();
      try {
    	  	//image for item
			image = ImageIO.read(getClass().getResource("images/Super Laser.png"));
			itemImage = image;
			
			//projectile images
			image_U = ImageIO.read(getClass().getResource("images/Laser Bolt - UD.png"));
			image_D = ImageIO.read(getClass().getResource("images/Laser Bolt - UD.png"));
			image_L = ImageIO.read(getClass().getResource("images/Laser Bolt - LR.png"));
			image_R = ImageIO.read(getClass().getResource("images/Laser Bolt - LR.png"));
		} catch (IOException e) { }
      numUses = 1; 
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
		   laserBolt1 = new LaserBolt(xSnake - 2, ySnake, 1, direction, 0, 25, image_U, image_D, image_L, image_R);
		   laserBolt2 = new LaserBolt(xSnake, ySnake, 1, direction, 0, 25, image_U, image_D, image_L, image_R);
		   laserBolt3 = new LaserBolt(xSnake + 2, ySnake, 1, direction, 0, 25, image_U, image_D, image_L, image_R);
	   }
	   else if (snake.getDirection() == Snake.Direction.LEFT)
	   {
		   direction = Projectile.Direction.LEFT;
		   xSnake = snake.getHeadX() - 1;
		   ySnake = snake.getHeadY();
		   laserBolt1 = new LaserBolt(xSnake, ySnake - 2, 1, direction, 0, 25, image_U, image_D, image_L, image_R);
		   laserBolt2 = new LaserBolt(xSnake, ySnake, 1, direction, 0, 25, image_U, image_D, image_L, image_R);
		   laserBolt3 = new LaserBolt(xSnake, ySnake + 2, 1, direction, 0, 25, image_U, image_D, image_L, image_R);
	   }
	   else if (snake.getDirection() == Snake.Direction.RIGHT)
	   {
		   direction = Projectile.Direction.RIGHT;
		   xSnake = snake.getHeadX() + 1;
		   ySnake = snake.getHeadY();
		   laserBolt1 = new LaserBolt(xSnake, ySnake - 2, 1, direction, 0, 25, image_U, image_D, image_L, image_R);
		   laserBolt2 = new LaserBolt(xSnake, ySnake, 1, direction, 0, 25, image_U, image_D, image_L, image_R);
		   laserBolt3 = new LaserBolt(xSnake, ySnake + 2, 1, direction, 0, 25, image_U, image_D, image_L, image_R);
	   }
	   else
	   {
		   direction = Projectile.Direction.DOWN;
		   xSnake = snake.getHeadX();
		   ySnake = snake.getHeadY() + 1;
		   laserBolt1 = new LaserBolt(xSnake - 2, ySnake, 1, direction, 0, 25, image_U, image_D, image_L, image_R);
		   laserBolt2 = new LaserBolt(xSnake, ySnake, 1, direction, 0, 25, image_U, image_D, image_L, image_R);
		   laserBolt3 = new LaserBolt(xSnake + 2, ySnake, 1, direction, 0, 25, image_U, image_D, image_L, image_R);
	   }
	   
	   //add projectiles to game
	   game.getProjectiles().add(laserBolt1);
	   game.getProjectiles().add(laserBolt2);
	   game.getProjectiles().add(laserBolt3);
	   
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