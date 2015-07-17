/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package enemies;
import java.awt.*;
import java.util.*;

import core.Framework;
import core.Window;
// TODO: Auto-generated Javadoc
/**
 * A Enemy_Template is made up of one or more EnemySegment. The first EnemySegment is the
 * "head" of the Enemy_Template. The last EnemySegment is the "tail" of the Enemy_Template. As the
 * Enemy_Template moves, it adds one cell to the head and then removes one from the tail. If
 * the Enemy_Template eats a piece of food, the head adds one cell but the tail will not
 * shrink.
 */
public abstract class Enemy_Template {
   
   /**
    * The Enum Direction.
    */
   public static enum Direction {
      
      /** The up. */
      UP, 
 /** The down. */
 DOWN, 
 /** The left. */
 LEFT, 
 /** The right. */
 RIGHT
   }
   
   /** The color. */
   protected Color color = Color.DARK_GRAY;     // color for this Enemy_Template body
   
   /** The color head. */
   protected Color colorHead = Color.RED; // color for the "head"
   
   /** The direction. */
   protected Enemy_Template.Direction direction;     // the current direction of the Enemy_Template's head
   
   /** The length. */
   protected int length; //length of enemy
   
   /** The alive. */
   protected boolean alive = false; //not alive yet
   
   /** The health. */
   protected int health; //Yeah
   
   /** The enemy segments. */
   protected ArrayList<EnemySegment> enemySegments = new ArrayList<EnemySegment>(); // The Enemy_Template segments that forms the Enemy_Template
   
   /** The enemies. */
   protected static ArrayList<ArrayList<EnemySegment>> enemies = new ArrayList<ArrayList<EnemySegment>>(); //list of enemySegments/enemies
   
   /** The enemy_ template count. */
   protected static int enemy_TemplateCount = 0; //number of enemies
   
   /** The dir update pending. */
   protected boolean dirUpdatePending;   // Pending update for a direction change?
   
   /** The random. */
   protected Random random = new Random();   // for randomly regenerating a Enemy_Template
   
   /** The count. */
   protected long count = 0L; //counts for AI in Enemy_Template
   
   /** The count2. */
   protected long count2 = 0L; //another count for whatever 
   
   
   // *************Creation methods*******************
   
   /**
    * Make enemy_ template.
    *
    * @return the enemy_ template
    */
   public static Enemy_Template makeEnemy_Template() //Method must be defined in subclasses (see below for example)
   {
	   return null;
	   /* ____Example for Subclass Methods____
	   Enemy_Template enemy_Template = new Enemy_Template();
	   enemy_Template.length = 5;
	   enemy_Template.regenerate();
	   enemies.add(Enemy_Template.getenemySegments());
	   enemy_TemplateCount++;
	   enemy_Template.alive = true;
	   enemy_Template.setHealth(10);
	   return enemy_Template;
	   */
   }
   
   /**
    * Make enemy_ template.
    *
    * @param x the x
    * @param y the y
    * @return the enemy_ template
    */
   public static Enemy_Template makeEnemy_Template(int x, int y) //Method must be defined in subclasses (see below for example)
   {
	   return null;
	   /* ____Example for Subclass Methods____
	   Enemy_Template enemy_Template = new Enemy_Template();
	   enemy_Template.length = 5;
	   enemy_Template.regenerate(x, y, Direction.LEFT);
	   enemies.add(enemy_Template.getenemySegments());
	   enemy_TemplateCount++;
	   enemy_Template.alive = true;
	   return enemy_Template;
	   */
   }
   
   // Regenerate the Enemy_Template.
   /**
    * Regenerate.
    */
   public void regenerate() {
      enemySegments.clear();
      // Randomly generate a Enemy_Template inside the pit.
      int headX = random.nextInt(Window.COLUMNS - length * 2) + length;
      int headY = random.nextInt(Window.ROWS - length * 2) + length;
      direction = Enemy_Template.Direction.values()[random.nextInt(Enemy_Template.Direction.values().length)];
      enemySegments.add(new EnemySegment(headX, headY, length, direction));
      dirUpdatePending = false;
      alive = true;
      enemy_TemplateCount++;
   }
   
   //regenerate in specific place
   /**
    * Regenerate.
    *
    * @param x the x
    * @param y the y
    * @param direction the direction
    */
   public void regenerate(int x, int y, Direction direction) {
      enemySegments.clear();
      // Randomly generate a Enemy_Template inside the pit.
      int headX = x;
      int headY = y;
      enemySegments.add(new EnemySegment(headX, headY, length, direction));
      dirUpdatePending = false;
      alive = true;
   }
   
   
   // Change the direction of the Enemy_Template, but no 180 degree turn allowed.
   /**
    * Sets the direction.
    *
    * @param newDir the new direction
    */
   public void setDirection(Enemy_Template.Direction newDir) {
      
         EnemySegment headSegment = enemySegments.get(0);  // get the head segment
         int x = headSegment.getHeadX();
         int y = headSegment.getHeadY();
         // add a new segment with zero length as the new head segment
         enemySegments.add(0, new EnemySegment(x, y, 0, newDir));
         direction = newDir;
         dirUpdatePending = true; // will be cleared after updated
      
   }
   
   //************Update / AI for Enemy_Template********************
   // Move the Enemy_Template by one step. The Enemy_Template "head" segment grows by one cell. The rest of the 
   // segments remain unchanged. The "tail" segment will later be shrink if collision detected.
   /**
    * Update.
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
	   
      EnemySegment headSegment;
      headSegment = enemySegments.get(0);   // "head" segment
      headSegment.grow();
      dirUpdatePending = false;   // can process the key input again
      
      //"AI" for Enemy_Template
      if (count >= Framework.getGAME_FPS()) //if it's been a second
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
          enemySegments.add(new EnemySegment(x, 0, 5, Direction.DOWN));
      }
      if (y == 0 && direction == Direction.UP)
      {
          enemySegments.clear();
    	  enemySegments.add(new EnemySegment(x, (Window.ROWS - 1) - 4, 5, Direction.UP));
      }
      if (x == (Window.COLUMNS- 1) && direction == Direction.RIGHT)
      {
          enemySegments.clear();
    	  enemySegments.add(new EnemySegment(0, y, 5, Direction.RIGHT));
      }
      if (x == 0 && direction == Direction.LEFT)
      {
          enemySegments.clear();
    	  enemySegments.add(new EnemySegment(Window.COLUMNS - 1, y, 5, Direction.LEFT));
      }
      
      count++;
   }
   
   /**
    * Change direction.
    */
   public void changeDirection()
   {
	   int choice = random.nextInt(3);

 	   Direction tempDir = direction;
	  			 
       switch (choice)
       {
          case 0 : 
        	if (tempDir != Direction.RIGHT)
          	{
        	    direction = Direction.LEFT;
          	}
        	else
        	{
        		direction = Direction.RIGHT;
        	}
          count = 0;
          break;
          case 1 : 
        	if (tempDir != Direction.LEFT)
        	{
        		  direction = Direction.RIGHT;
        	}
        	else
        	{
        		direction = Direction.LEFT;
        	}
          count = 0;
          break;
          case 2 : 
        	if (tempDir != Direction.DOWN)
          	{
          		  direction = Direction.UP;
          	}
          	else
          	{
          		direction = Direction.DOWN;
          	}
          count = 0;
          break;
          case 3 : 
        	if (tempDir != Direction.UP)
        	{
        		direction = Direction.DOWN;
            }
            else
            {
            	direction = Direction.UP;
            }
          count = 0;
          break;
       }
       
       setDirection(direction);
   }
   
   // Not eaten a food item. Shrink the tail by one cell.
   /**
    * Shrink.
    */
   public void shrink() {
      EnemySegment tailSegment;
      tailSegment = enemySegments.get(enemySegments.size() - 1);
      tailSegment.shrink();
      if (tailSegment.getLength() == 0) {
         enemySegments.remove(tailSegment);
      }
   }
   
   //******************Collision detection*********************
   // Returns true if the Enemy_Template contains the given (x, y) cell. Used in collision detection
   /**
    * Contains.
    *
    * @param x the x
    * @param y the y
    * @return true, if successful
    */
   public boolean contains(int x, int y) {
      for (int i = 0; i < enemySegments.size(); ++i) {
         EnemySegment segment = enemySegments.get(i);
         if (segment.contains(x, y)) {
            return true;
         }
      }
      return false;
   }
   
   // Returns true if the Enemy_Template eats itself
   /**
    * Eat itself.
    *
    * @return true, if successful
    */
   public boolean eatItself() {
      int headX = getHeadX();
      int headY = getHeadY();
      // eat itself if the (headX, headY) hits its body segment (4th onwards) 
      for (int i = 3; i < enemySegments.size(); ++i) {
         EnemySegment segment = enemySegments.get(i);
         if (segment.contains(headX, headY)) {
            return true;
         }
      }
      return false;
   }
   
   // ************Graphics*****************
   // Draw itself.
   /**
    * Draw.
    *
    * @param g the g
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
   
   //***********toString*******************
   // For debugging.
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("**Enemy_Template** Direction is " + direction + "\n");
      int count = 1;
      for (EnemySegment segment : enemySegments) {
         sb.append("  Segment " + count + ": ");
         ++count;
         sb.append(segment);
         sb.append('\n');
      }
      return sb.toString();
   }
   
// ***********Getters & setters**************
   
   /**
 * Gets the health.
 *
 * @return the health
 */
public int getHealth()
   {
	   return health;
   }
   
   /**
    * Sets the health.
    *
    * @param health the new health
    */
   public void setHealth(int health)
   {
	   this.health = health;
   }
   // Get the X coordinate of the cell that contains the head of this Enemy_Template segment.
   /**
    * Gets the head x.
    *
    * @return the head x
    */
   public int getHeadX() {
      return enemySegments.get(0).getHeadX();
   }
   
   // Get the Y coordinate of the cell that contains the head of this Enemy_Template segment.
   /**
    * Gets the head y.
    *
    * @return the head y
    */
   public int getHeadY() {
      return enemySegments.get(0).getHeadY();
   }
   
   /**
    * Gets the length.
    *
    * @return the length
    */
   public int getLength()
   {
	   return length;
   }
   
   // Returns the length of this Enemy_Template by adding up all the segments.
   /**
    * Gets the actual length.
    *
    * @return the actual length
    */
   public int getActualLength() {
      int length = 0;
      for (EnemySegment segment : enemySegments) {
         length += segment.getLength();
      }
      return length;
   }
   
   //get the Enemy_Template segments of a particular Enemy_Template
   /**
    * Gets the enemy segments.
    *
    * @return the enemy segments
    */
   public ArrayList<EnemySegment> getEnemySegments()
   {
      return enemySegments;
   }
   
   //get the full list of enemies
   /**
    * Gets the enemies.
    *
    * @return the enemies
    */
   public static ArrayList<ArrayList<EnemySegment>> getEnemies()
   {
	   return enemies;	   
   }
   
   //get the Enemy_Template count
   /**
    * Gets the enemy_ template count.
    *
    * @return the enemy_ template count
    */
   public static int getEnemy_TemplateCount()
   {
	   return enemy_TemplateCount;
   }
   
   //change color
   /**
    * Sets the color.
    *
    * @param color the new color
    */
   public void setColor(Color color)
   {
      this.color = color;
   }
   
   /**
    * Checks if is alive.
    *
    * @return true, if is alive
    */
   public boolean isAlive()
   {
      return alive;
   }
   
}
