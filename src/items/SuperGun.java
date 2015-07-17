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
public class SuperGun extends Item{
	
	/** The x snake. */
	private int xSnake;
	
	/** The y snake. */
	private int ySnake;
	
	/** The direction. */
	private Projectile.Direction direction;
	
	/** The bullet5. */
	private Projectile bullet1, bullet2, bullet3, bullet4, bullet5;
	
	/** The item image. */
	private static Image itemImage;
	
	/** The image_ r. */
	protected Image image_U, image_D, image_L, image_R; //images of projectiles
	
	/** The g2d. */
	public Graphics2D g2d;
    
   // Default constructor.
   /**
    * Instantiates a new super gun.
    */
   public SuperGun() {
      super();
      try {
    	  	//item image
			image = ImageIO.read(getClass().getResource("images/Super Gun.png"));
			itemImage = image;
			
			//projectiles images
			image_U = ImageIO.read(getClass().getResource("images/Lightning Ball - U.png"));
			image_D = ImageIO.read(getClass().getResource("images/Lightning Ball - D.png"));
			image_L = ImageIO.read(getClass().getResource("images/Lightning Ball - L.png"));
			image_R = ImageIO.read(getClass().getResource("images/Lightning Ball - R.png"));
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
		   bullet1 = new Projectile(xSnake - 2, ySnake, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
		   bullet2 = new Projectile(xSnake - 1, ySnake, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
		   bullet3 = new Projectile(xSnake, ySnake, 1, direction, 0,  15, image_U, image_D, image_L, image_R);
		   bullet4 = new Projectile(xSnake + 1, ySnake, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
		   bullet5 = new Projectile(xSnake + 2, ySnake, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
	   }
	   else if (snake.getDirection() == Snake.Direction.LEFT)
	   {
		   direction = Projectile.Direction.LEFT;
		   xSnake = snake.getHeadX() - 1;
		   ySnake = snake.getHeadY();
		   bullet1 = new Projectile(xSnake, ySnake - 2, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
		   bullet2 = new Projectile(xSnake, ySnake - 1, 1, direction, 0, 15,image_U, image_D, image_L, image_R);
		   bullet3 = new Projectile(xSnake, ySnake, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
		   bullet4 = new Projectile(xSnake, ySnake + 1, 1, direction, 0, 15,image_U, image_D, image_L, image_R);
		   bullet5 = new Projectile(xSnake, ySnake + 2, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
	   }
	   else if (snake.getDirection() == Snake.Direction.RIGHT)
	   {
		   direction = Projectile.Direction.RIGHT;
		   xSnake = snake.getHeadX() + 1;
		   ySnake = snake.getHeadY();
		   bullet1 = new Projectile(xSnake, ySnake - 2, 1, direction, 0, 15,  image_U, image_D, image_L, image_R);
		   bullet2 = new Projectile(xSnake, ySnake - 1, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
		   bullet3 = new Projectile(xSnake, ySnake, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
		   bullet4 = new Projectile(xSnake, ySnake + 1, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
		   bullet5 = new Projectile(xSnake, ySnake + 2, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
	   }
	   else
	   {
		   direction = Projectile.Direction.DOWN;
		   xSnake = snake.getHeadX();
		   ySnake = snake.getHeadY() + 1;
		   bullet1 = new Projectile(xSnake - 2, ySnake, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
		   bullet2 = new Projectile(xSnake - 1, ySnake, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
		   bullet3 = new Projectile(xSnake, ySnake, 1, direction, 0, 15,  image_U, image_D, image_L, image_R);
		   bullet4 = new Projectile(xSnake + 1, ySnake, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
		   bullet5 = new Projectile(xSnake + 2, ySnake, 1, direction, 0, 15, image_U, image_D, image_L, image_R);
	   }
	   
	   //projectiles to game
	   game.getProjectiles().add(bullet1);
	   game.getProjectiles().add(bullet2);
	   game.getProjectiles().add(bullet3);
	   game.getProjectiles().add(bullet4);
	   game.getProjectiles().add(bullet5);
	   
	   //item has been used
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