/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package bosses;

import java.awt.*;
import java.util.*;

import core.*;
import core.Window;

// TODO: Auto-generated Javadoc
/**
 * A Boss_Template is made up of one or more boss_TemplateSegment. The first boss_TemplateSegment is the
 * "head" of the Boss_Template. The last boss_TemplateSegment is the "tail" of the Boss_Template. As the
 * Boss_Template moves, it adds one cell to the head and then removes one from the tail. If
 * the Boss_Template eats a piece of food, the head adds one cell but the tail will not
 * shrink.
 */
public abstract class Boss_Template {
   
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
   protected Color color = Color.DARK_GRAY;     // color for this Boss_Template body
   
   /** The color head. */
   protected Color colorHead = Color.RED; // color for the "head"
   
   /** The direction. */
   protected Boss_Template.Direction direction;   // the current direction of the Boss_Template's head
   
   /** The health. */
   protected int health = 100; //health of the Boss_Template
   
   /** The head x. */
   protected int headX; //X location of the "head" segment
   
   /** The head y. */
   protected int headY; //Y location of the "head" segment
// The Boss_Template segments that forms the Boss_Template
   /** The Boss segments. */
protected ArrayList<BossSegment> BossSegments = new ArrayList<BossSegment>();
   
   /** The count. */
   protected long count = 0L; //counts for AI in Boss_Template
   
   /** The alive. */
   protected boolean alive = false;
   
   /** The total health. */
   protected int totalHealth = health;
   
   /** The random. */
   protected Random random = new Random();   // for randomly regenerating a Boss_Template
   
   
  
   // *************Creation methods*******************
   
   /**
    * Make boss.
    *
    * @return the boss_ template
    */
   public static Boss_Template makeBoss() //Method must be defined in subclasses (see below for example)
   {
	   return null; 
	   
	   	/* ____Example for Subclass Methods____
	   Boss_Template boss_Template = new Boss_Template();
	   Boss_Template.regenerate();
	   return Boss_Template;
	   */
   }
   
   /**
    * Creates the shape.
    *
    * @param headX the head x
    * @param headY the head y
    */
   public void createShape(int headX, int headY) //Method must be defined in subclasses (see below for example)
   {
	   for (int i = 0; i < 8; i++)
	      {
	    	  for (int j = 0; j < 8; j++)
	    	  {
	    		  BossSegments.add(new BossSegment(headX + i, headY + j, 1, direction));
	    	  }
	      }
	   
   }
   
   // Regenerate the Boss_Template.
   /**
    * Regenerate.
    */
   public void regenerate() {
	   do
	   {
	      BossSegments.clear();
	      // Randomly generate a Boss_Template inside the pit.
	      headX = random.nextInt(Window.COLUMNS - 15);
	      while (headX <= 5) //regenerate if not towards middle of pit
	      {
	    	  headX = random.nextInt(Window.COLUMNS - 15);
	      }
	      
	      headY = random.nextInt(Window.ROWS - 15);
	      while (headY <= 5) //regenerate if not towards middle of pit
	      {
	    	  headY = random.nextInt(Window.COLUMNS - 15);
	      }
	      direction = Direction.LEFT;
	      
	      createShape(headX, headY); //creates the shape that defines the boss
	      
	      alive = true;
	   } while (!playingFair()); // regenerate if Boss_Template placed over the snake
   }
   
   
   //checks to see if any of the Boss_Template segments generate over the snake (e.g. instant death upon level up)
   //return false if the game isn't playing fair
   /**
    * Playing fair.
    *
    * @return true, if successful
    */
   public boolean playingFair()
   {
	   Snake snake = Framework.getGame().getSnake();
	   for (int i = 0; i < BossSegments.size(); ++i) 
	   {
		   int x = BossSegments.get(i).getHeadX();
		   int y = BossSegments.get(i).getHeadY();
		   
		   if (snake.contains(x,y))
			   return false;
	   }
	   return true;
   }
   
   // Change the direction of the Boss_Template, but no 180 degree turn allowed.
   /**
    * Sets the direction.
    *
    * @param newDir the new direction
    */
   public void setDirection(Boss_Template.Direction newDir) {
         BossSegment headSegment = BossSegments.get(0);  // get the head segment
         int x = headSegment.getHeadX();
         int y = headSegment.getHeadY();
         // add a new segment with zero length as the new head segment
         BossSegments.add(0, new BossSegment(x, y, 0, newDir));
         direction = newDir;
      
   }
   
   //************Update / AI for Boss_Template********************
   
   /**
    * Update.
    */
   public void update() {
	deathCheck(); //check to see if alive
    if (isAlive())
    {
	   //"AI" for Boss_Template
	   if (count >= Framework.getGAME_FPS() / 2 ) //if it's been a second
	   {
		   this.changeDirection();
		   move();
	   }
	   stayInPit(getHeadX(), getHeadY()); //makes sure the enemy stays in the pit
	   count++; //increments count
    }
  }
   
   /**
    * Death check.
    */
   public void deathCheck()
   {
	   if (health > 0)
	   {
		   alive = true;
	   }
	   else
	   {
		   alive = false;
		   kill();
	   }
   }
   
   /**
    * Stay in pit.
    *
    * @param headX the head x
    * @param headY the head y
    */
   public void stayInPit(int headX, int headY)
   {
	   int x = headX;
	   int y = headY;
	      if (y > (Window.ROWS - 1) - 13 && direction == Direction.DOWN)
	      {
	    	  direction = Direction.UP;
	    	  move();
	      }
	      if (y < 5 && direction == Direction.UP)
	      {
	    	  direction = Direction.DOWN;
	    	  move();
	      }
	      if (x > (Window.COLUMNS- 1) - 13 && direction == Direction.RIGHT)
	      {
	    	  direction = Direction.LEFT;
	    	  move();
	      }
	      if (x < 5 && direction == Direction.LEFT)
	      {
	    	  direction = Direction.RIGHT;
	    	  move();
	      }
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
       
       //setDirection(direction);
   }
   
   /**
    * Kill.
    */
   public void kill()
   {
	   BossSegments.clear();
	   alive = false;
	   //DESTROY THE Boss_Template
	   
	   Framework.getGame().increaseLevel(); //increase the level
	   Framework.getGame().levelStart(Framework.getGame().getLevel()); //start new level
   }
   
   /**
    * Damage.
    *
    * @param damage the damage
    */
   public void damage(int damage)
   {
	   health -= damage;
   }
   
   /**
    * Move.
    */
   public void move()
   {
	   for (int i = 0; i < BossSegments.size(); ++i) 
	   {
	         switch(direction)
	         {
		         case UP :
		         {
		         	BossSegment segment = new BossSegment(BossSegments.get(i).getHeadX(), 
		         											BossSegments.get(i).getHeadY() - 1, 1, direction);
		         	BossSegments.remove(BossSegments.get(i));
		         	BossSegments.add(i, segment); 
		         	
		         	break;
		         }
		         case DOWN :
		         {
		        	 BossSegment segment = new BossSegment(BossSegments.get(i).getHeadX(), 
								BossSegments.get(i).getHeadY() + 1, 1, direction);
					BossSegments.remove(BossSegments.get(i));
					BossSegments.add(i, segment);
		         	break;
		         }
		         case LEFT :
		         {
		        	 BossSegment segment = new BossSegment(BossSegments.get(i).getHeadX() - 1, 
								BossSegments.get(i).getHeadY(), 1, direction);
					BossSegments.remove(BossSegments.get(i));
					BossSegments.add(i, segment);
		         	break;
		         }
		         case RIGHT :
		         {
		        	 BossSegment segment = new BossSegment(BossSegments.get(i).getHeadX() + 1, 
								BossSegments.get(i).getHeadY(), 1, direction);
					BossSegments.remove(BossSegments.get(i));
					BossSegments.add(i, segment);
		         	break;
		         }
	         }
	         
	   }
   }
   
   
   // Not eaten a food item. Shrink the tail by one cell.
   /**
    * Shrink.
    */
   public void shrink() {
      BossSegment tailSegment;
      tailSegment = BossSegments.get(BossSegments.size() - 1);
      tailSegment.shrink();
      if (tailSegment.getLength() == 0) {
         BossSegments.remove(tailSegment);
      }
   }
   
   //******************Collision detection*********************
   // Returns true if the Boss_Template contains the given (x, y) cell. Used in collision detection
   /**
    * Contains.
    *
    * @param x the x
    * @param y the y
    * @return true, if successful
    */
   public boolean contains(int x, int y) {
      for (int i = 0; i < BossSegments.size(); ++i) {
         BossSegment segment = BossSegments.get(i);
         if (segment.contains(x, y)) {
            return true;
         }
      }
      return false;
   }
   
   // Returns true if the Boss_Template eats itself
   /**
    * Eat itself.
    *
    * @return true, if successful
    */
   public boolean eatItself() {
      int headX = getHeadX();
      int headY = getHeadY();
      // eat itself if the (headX, headY) hits its body segment (4th onwards) 
      for (int i = 3; i < BossSegments.size(); ++i) {
         BossSegment segment = BossSegments.get(i);
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
      for (int i = 0; i < BossSegments.size(); ++i) {
         BossSegments.get(i).draw(g);   // draw all the segments
      }
      if (BossSegments.size() > 0) {
         g.setColor(colorHead);
         g.fill3DRect(getHeadX() * Window.CELL_SIZE, getHeadY()
               * Window.CELL_SIZE, Window.CELL_SIZE,
                Window.CELL_SIZE, true);
      }
      
      
   }
   
   //***********toString*******************8
   // For debugging.
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("**Boss_Template** Direction is " + direction + "\n");
      int count = 1;
      for (BossSegment segment : BossSegments) {
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
   
   /**
    * Gets the total health.
    *
    * @return the total health
    */
   public int getTotalHealth()
   {
	   return totalHealth;
   }
   
   /**
    * Sets the total health.
    *
    * @param health the new total health
    */
   public void setTotalHealth(int health)
   {
	   this.health = totalHealth;
   }
   
   // Get the X coordinate of the cell that contains the head of this Boss_Template segment.
   /**
    * Gets the head x.
    *
    * @return the head x
    */
   public int getHeadX() {
      return BossSegments.get(0).getHeadX();
   }
// Get the Y coordinate of the cell that contains the head of this Boss_Template segment.
   /**
 * Gets the head y.
 *
 * @return the head y
 */
public int getHeadY() {
      return BossSegments.get(0).getHeadY();
   }
   
   //get the locations of the different sections of the Boss_Template
   /**
    * Gets the square2 x.
    *
    * @return the square2 x
    */
   public int getSquare2X() {
	   		return BossSegments.get(0).getHeadX() + 4;
	      //return BossSegments.get(5).getHeadX();
	   }
   
   /**
    * Gets the square2 y.
    *
    * @return the square2 y
    */
   public int getSquare2Y() {
	   	return BossSegments.get(0).getHeadY() + 0;
	     // return BossSegments.get(5).getHeadX();
	   }
   
   //
   /**
    * Gets the square3 x.
    *
    * @return the square3 x
    */
   public int getSquare3X() {
	   return BossSegments.get(0).getHeadX() + 0;
	    //  return BossSegments.get(33).getHeadX();
	   }
   
   /**
    * Gets the square3 y.
    *
    * @return the square3 y
    */
   public int getSquare3Y() {
	   return BossSegments.get(0).getHeadY() + 4;
	     // return BossSegments.get(33).getHeadX();
	   }
   
   //
   /**
    * Gets the square4 x.
    *
    * @return the square4 x
    */
   public int getSquare4X() {
	   return BossSegments.get(0).getHeadX() + 4;
	    //  return BossSegments.get(37).getHeadX();
	   }
   
   /**
    * Gets the square4 y.
    *
    * @return the square4 y
    */
   public int getSquare4Y() {
	   return BossSegments.get(0).getHeadY() + 4;
	     // return BossSegments.get(37).getHeadX();
	   }

   
   
   // Returns the length of this Boss_Template by adding up all the segments.
   /**
    * Gets the length.
    *
    * @return the length
    */
   public int getLength() {
      int length = 0;
      for (BossSegment segment : BossSegments) {
         length += segment.getLength();
      }
      return length;
   }
   
   //get the Boss_Template segments of a particular Boss_Template
   /**
    * Gets the boss segments.
    *
    * @return the boss segments
    */
   public ArrayList<BossSegment> getBossSegments()
   {
      return BossSegments;
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

/**
 * Creates the shape.
 */
public void createShape() {
	// TODO Auto-generated method stub
	
}
   
}