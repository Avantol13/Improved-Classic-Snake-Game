/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package enemies;
import items.Projectile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

import core.Framework;
import core.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class Enemy_Shooter.
 */
public class Enemy_Shooter extends Enemy_Template {
	// *************Creation methods*******************
	   
	   /**
	 * Make enemy_ shooter.
	 *
	 * @return the enemy_ shooter
	 */
	public static Enemy_Shooter makeEnemy_Shooter()
	   {
		   Enemy_Shooter enemy_Shooter = new Enemy_Shooter();
		   enemy_Shooter.colorHead = Color.BLUE;
		   enemy_Shooter.health = 20;
		   enemy_Shooter.length = 3; 
		   enemy_Shooter.regenerate();
		   enemies.add(enemy_Shooter.getEnemySegments());
		   enemy_TemplateCount++;
		   enemy_Shooter.alive = true;
		   enemy_Shooter.setHealth(20);
		   return enemy_Shooter;
	   }
	   
	   /**
   	 * Make enemy_ shooter.
   	 *
   	 * @param x the x
   	 * @param y the y
   	 * @return the enemy_ shooter
   	 */
   	public static Enemy_Shooter makeEnemy_Shooter(int x, int y)
	   {
		   Enemy_Shooter enemy_Shooter = new Enemy_Shooter();
		   enemy_Shooter.colorHead = Color.BLUE;
		   enemy_Shooter.health = 20;
		   enemy_Shooter.length = 3; 
		   enemy_Shooter.regenerate(x, y, Direction.LEFT);
		   enemies.add(enemy_Shooter.getEnemySegments());
		   enemy_TemplateCount++;
		   enemy_Shooter.alive = true;
		   return enemy_Shooter;
	   }
	   
	 //************Update / AI for enemy********************
	   // Move the enemy by one step. The enemy "head" segment grows by one cell. The rest of the 
	   // segments remain unchanged. The "tail" segment will later be shrink if collision detected.
	   /* (non-Javadoc)
 	 * @see enemies.Enemy_Template#update()
 	 */
 	public void update() {
		   
		   //check to see if alive
		   if (health > 0)
		   {
			   alive = true;
		   }
		   else
		   {
			   alive = false;
		   }
		   
		   //Shooting
		   if (count2 >= 5*Framework.getGAME_FPS()) //if it's been 5 seconds
		   {
			   if (random.nextInt(10) + 1 > 6) //60% chance
			   {
				   shoot(); //shoot the projectile
				   count2 = 0;
			   }
		   }
		   count2++;
		   
	      EnemySegment headSegment;
	      headSegment = enemySegments.get(0);   // "head" segment
	      headSegment.grow();
	      
	      //"AI" for enemy
	      if (count >= Framework.getGAME_FPS()/2) //if it's been a half second
	      {
	    	 this.changeDirection();
	      }
	      shrink();
	      
	      //stay in pit
	      int x = this.getHeadX();
	      int y = this.getHeadY();
	      if (y == (Window.ROWS - 1) - 4 && direction == Direction.DOWN)
	      {
	          enemySegments.clear();
	          enemySegments.add(new EnemySegment(x, 0, length, Direction.DOWN));
	      }
	      if (y == 0 && direction == Direction.UP)
	      {
	    	  enemySegments.clear();
	    	  enemySegments.add(new EnemySegment(x, (Window.ROWS - 1) - 4, length, Direction.UP));
	      }
	      if (x == (Window.COLUMNS- 1) && direction == Direction.RIGHT)
	      {
	    	  enemySegments.clear();
	    	  enemySegments.add(new EnemySegment(0, y, length, Direction.RIGHT));
	      }
	      if (x == 0 && direction == Direction.LEFT)
	      {
	    	  enemySegments.clear();
	    	  enemySegments.add(new EnemySegment(Window.COLUMNS - 1, y, length, Direction.LEFT));
	      }
	      
	      count++;
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
		   
		   if (direction == Enemy_Template.Direction.UP)
		   {
			   projDirection = Projectile.Direction.UP;
			   x = this.getHeadX();
			   y = this.getHeadY() - 1;
		   }
		   else if (direction == Enemy_Template.Direction.LEFT)
		   {
			   projDirection = Projectile.Direction.LEFT;
			   x = this.getHeadX() - 1;
			   y = this.getHeadY();
		   }
		   else if (direction == Enemy_Template.Direction.RIGHT)
		   {
			   projDirection = Projectile.Direction.RIGHT;
			   x = this.getHeadX() + 1;
			   y = this.getHeadY();
		   }
		   else
		   {
			   projDirection = Projectile.Direction.DOWN;
			   x = this.getHeadX();
			   y = this.getHeadY() + 1;
		   }
		   
		   bullet = new Projectile(x, y, 1, projDirection, 1, 25, image_U, image_D, image_L, image_R);
		   Framework.getGame().getProjectiles().add(bullet);
	   }
	   
	// ************Graphics*****************
	   // Draw itself.
	   /* (non-Javadoc)
	 * @see enemies.Enemy_Template#draw(java.awt.Graphics)
	 */
	public void draw(Graphics g) {
	      g.setColor(color);
	      for (int i = 0; i < enemySegments.size(); ++i) {
	    	  enemySegments.get(i).draw(g);   // draw all the segments
	      }
	      if (enemySegments.size() > 0) {
	         g.setColor(colorHead);
	         g.fill3DRect(getHeadX() * Window.CELL_SIZE, getHeadY()
	               * Window.CELL_SIZE, Window.CELL_SIZE - 1,
	                Window.CELL_SIZE - 1, true);
	      }
	   }
}
