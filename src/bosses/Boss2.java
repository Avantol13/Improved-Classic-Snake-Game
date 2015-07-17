/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package bosses;
import items.Projectile;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.Framework;


// TODO: Auto-generated Javadoc
/**
 * The Class Boss2.
 */
public class Boss2 extends Boss_Template {
	
   /** The count2. */
   protected long count2 = 0L;
   
   // *************Creation methods*****************
   
   /**
    * Instantiates a new boss2.
    */
   public Boss2()
   {
	   super();
	   alive = true;
	   health = 300;
	   totalHealth = health;
   }
   
   /**
    * Make boss.
    *
    * @return the boss2
    */
   public static Boss2 makeBoss() 
   {
	   Boss2 boss2 = new Boss2();
	   boss2.color = Color.RED;
	   boss2.regenerate();
	   return boss2;
   }
   
   /* (non-Javadoc)
    * @see bosses.Boss_Template#update()
    */
   public void update() {
		deathCheck(); //check to see if alive
	    if (isAlive())
	    {
		   //"AI" for Boss_Template
		   if (count >= 2 * Framework.getGAME_FPS()) //if it's been 2 seconds
		   {
			   this.changeDirection();
			   move();
		   }
		   stayInPit(getHeadX(), getHeadY()); //makes sure the enemy stays in the pit
		   count++; //increments count
		   
		   //Shooting
		   if (count2 >= 5*Framework.getGAME_FPS()) //if it's been 5 seconds
		   {
			   if (random.nextInt(10) + 1 > 0) //60% chance
			   {
				   shoot(); //shoot the projectile
				   count2 = 0;
			   }
		   }
		   count2++;
	    }
	  }
   
   /* (non-Javadoc)
    * @see bosses.Boss_Template#createShape(int, int)
    */
   @Override
   public void createShape(int x, int y) 
   {
	   for (int i = 0; i < 6; i++)
	   {
	    	for (int j = 0; j < 6; j++)
	    	{
	    		BossSegments.add(new BossSegment(x + i, y + j, 1, direction));
	    	}
	   }
   }
   
   /**
    * Shoot.
    */
   public void shoot()
   {
	   int x;
	   int y;
	   Projectile.Direction projDirection;
	   Projectile bullet;
	   Image image_U = null, image_D = null, image_L = null, image_R = null; //images of projectiles
	   try {				
			//projectiles images
			image_U = ImageIO.read(getClass().getResource("images/Lightning Ball - U.png"));
			image_D = ImageIO.read(getClass().getResource("images/Lightning Ball - D.png"));
			image_L = ImageIO.read(getClass().getResource("images/Lightning Ball - L.png"));
			image_R = ImageIO.read(getClass().getResource("images/Lightning Ball - R.png"));
		} catch (IOException e) { }
	   
	   for (int i = 0; i < 7; i++)
	   {
			   projDirection = Projectile.Direction.UP;
			   bullet = new Projectile(i + this.getHeadX() - 1, this.getHeadY() - 1, 
					    1, projDirection, 1, 25, image_U, image_D, image_L, image_R);
			   Framework.getGame().getProjectiles().add(bullet);
	   }
	   for (int i = 0; i < 7; i++)
	   {
			   projDirection = Projectile.Direction.DOWN;
			   bullet = new Projectile(i + this.getHeadX() - 1, this.getHeadY() - 1 + 7, 
					    1, projDirection, 1, 25, image_U, image_D, image_L, image_R);
			   Framework.getGame().getProjectiles().add(bullet);
	   }
	   for (int i = 0; i < 7; i++)
	   {
			   projDirection = Projectile.Direction.LEFT;
			   bullet = new Projectile(this.getHeadX() - 1, this.getHeadY() - 1 + i, 
					    1, projDirection, 1, 25, image_U, image_D, image_L, image_R);
			   Framework.getGame().getProjectiles().add(bullet);
	   }
	   for (int i = 0; i < 7; i++)
	   {
			   projDirection = Projectile.Direction.RIGHT;
			   bullet = new Projectile(this.getHeadX() - 1 + 7, this.getHeadY() - 1 + i, 
					    1, projDirection, 1, 25, image_U, image_D, image_L, image_R);
			   Framework.getGame().getProjectiles().add(bullet);
	   }
	   
   }
   
   //***********toString*******************
   /** For debugging.
    * @see bosses.Boss_Template#toString()
    */
   @Override
   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("**Boss2** Direction is " + direction + "\n");
      int count = 1;
      for (BossSegment segment : BossSegments) {
         sb.append("  Segment " + count + ": ");
         ++count;
         sb.append(segment);
         sb.append('\n');
      }
      return sb.toString();
   }
   
}