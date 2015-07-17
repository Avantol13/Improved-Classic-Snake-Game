/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package core;
import java.awt.*;
import java.util.*;

import enemies.Enemy;
// TODO: Auto-generated Javadoc
/**
 * A Snake is made up of one or more SnakeSegment. The first SnakeSegment is the
 * "head" of the snake. The last SnakeSegment is the "tail" of the snake. As the
 * snake moves, it adds one cell to the head and then removes one from the tail. If
 * the snake eats a piece of food, the head adds one cell but the tail will not
 * shrink.
 */
public class Snake {
   
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
   public Color color = Color.BLACK;     // color for this snake body
   
   /** The color head. */
   private Color colorHead = Color.BLUE; // color for the "head"
   
   /** The direction. */
   private Snake.Direction direction;     // the current direction of the snake's head
   
   /** The state. */
   private int state; //0 is normal
   
   /** The count. */
   private int count; //count for doin' stuff 
   
   // The snake segments that forms the snake
   /** The snake segments. */
   private java.util.List<SnakeSegment> snakeSegments = new ArrayList<SnakeSegment>();
   
   /** The dir update pending. */
   private boolean dirUpdatePending;   // Pending update for a direction change?
   
   /** The random. */
   private Random random = new Random();   // for randomly regenerating a snake
   
   //gets the snakesegments
   /**
    * Gets the snake segments.
    *
    * @return the snake segments
    */
   public java.util.List<SnakeSegment> getSnakeSegments()
   {
	   return snakeSegments;
   }
   
   //gets the direction
   /**
    * Gets the direction.
    *
    * @return the direction
    */
   public Snake.Direction getDirection()
   {
	   return direction;
   }
   
   //gets the count 
   /**
    * Gets the count.
    *
    * @return the count
    */
   public int getCount()
   {
	   return count;
   }
   
   /**
    * Sets the count.
    *
    * @param count the new count
    */
   public void setCount(int count)
   {
	   this.count = count;
   }
   
   /**
    * Adds the to count.
    *
    * @param add the add
    */
   public void addToCount(int add)
   {
	   this.count += add;
   }
   
   //sets the state of the snake ( 0 is normal , 1 is invincible)
   /**
    * Sets the state.
    *
    * @param state the new state
    */
   public void setState(int state)
   {
	   this.state = state;
   }
   
   /**
    * Gets the state.
    *
    * @return the state
    */
   public int getState()
   {
	   return state;
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
   
   //change color of head
   /**
    * Sets the color head.
    *
    * @param color the new color head
    */
   public void setColorHead(Color color)
   {
      this.colorHead = color;
   }
   
   // Regenerate the snake.
   /**
    * Regenerate.
    */
   public void regenerate() {
      snakeSegments.clear();
      // Randomly generate a snake inside the pit.
      int length = 10;
      int headX = random.nextInt(Window.COLUMNS - length * 2) + length;
      int headY = random.nextInt(Window.ROWS - length * 2) + length;
      direction = Snake.Direction.values()[random.nextInt(Snake.Direction.values().length)];
      snakeSegments.add(new SnakeSegment(headX, headY, length, direction));
      dirUpdatePending = false;
   }
   
   // Change the direction of the snake, but no 180 degree turn allowed.
   /**
    * Sets the direction.
    *
    * @param newDir the new direction
    */
   public void setDirection(Snake.Direction newDir) {
      // Ignore if there is a direction change pending and no 180 degree turn
      if (!dirUpdatePending && (newDir != direction)
            && ((newDir == Snake.Direction.UP && direction != Snake.Direction.DOWN)
             || (newDir == Snake.Direction.DOWN && direction != Snake.Direction.UP)
             || (newDir == Snake.Direction.LEFT && direction != Snake.Direction.RIGHT) 
             || (newDir == Snake.Direction.RIGHT && direction != Snake.Direction.LEFT))) {
         SnakeSegment headSegment = snakeSegments.get(0);  // get the head segment
         int x = headSegment.getHeadX();
         int y = headSegment.getHeadY();
         // add a new segment with zero length as the new head segment
         snakeSegments.add(0, new SnakeSegment(x, y, 0, newDir));
         direction = newDir;
         dirUpdatePending = true; // will be cleared after updated
      }
   }
   
   // Move the snake by one step. The snake "head" segment grows by one cell. The rest of the 
   // segments remain unchanged. The "tail" segment will later be shrink if collision detected.
   /**
    * Update.
    */
   public void update() {
      SnakeSegment headSegment;
      headSegment = snakeSegments.get(0);   // "head" segment
      headSegment.grow();
      dirUpdatePending = false;   // can process the key input again
      
      if (state == 0)
      {
    	  //do nothing
      }
      else
      {
    	  if (count == 12 * Framework.getGAME_FPS()) //if it's been 10 seconds
    	  {
    		  state = 0;
    		  count = 0;
    	  }
    	  count++;
      }
   }
   
   // Not eaten a food item. Shrink the tail by one cell.
   /**
    * Shrink.
    */
   public void shrink() {
      SnakeSegment tailSegment;
      tailSegment = snakeSegments.get(snakeSegments.size() - 1);
      tailSegment.shrink();
      if (tailSegment.getLength() == 0) {
         snakeSegments.remove(tailSegment);
      }
   }
   
   //Shrink the tail by specified cells.
   /**
    * Shrink.
    *
    * @param amount the amount
    */
   public void shrink(int amount) {
      for (int i = 0; i < amount; i++)
      {
         SnakeSegment segment;
         segment = snakeSegments.get(snakeSegments.size() - 1);
         segment.shrink();
         if (segment.getLength() == 0) {
            snakeSegments.remove(segment);
         }
      }
   }
   
   // Get the X coordinate of the cell that contains the head of this snake segment.
   /**
    * Gets the head x.
    *
    * @return the head x
    */
   public int getHeadX() {
      return snakeSegments.get(0).getHeadX();
   }
   
   // Get the Y coordinate of the cell that contains the head of this snake segment.
   /**
    * Gets the head y.
    *
    * @return the head y
    */
   public int getHeadY() {
      return snakeSegments.get(0).getHeadY();
   }
   
   // Returns the length of this snake by adding up all the segments.
   /**
    * Gets the length.
    *
    * @return the length
    */
   public int getLength() {
      int length = 0;
      for (SnakeSegment segment : snakeSegments) {
         length += segment.getLength();
      }
      return length;
   }
   
   // Returns true if the snake contains the given (x, y) cell. Used in collision detection
   /**
    * Contains.
    *
    * @param x the x
    * @param y the y
    * @return true, if successful
    */
   public boolean contains(int x, int y) {
      for (int i = 0; i < snakeSegments.size(); ++i) {
         SnakeSegment segment = snakeSegments.get(i);
         if (segment.contains(x, y)) {
            return true;
         }
      }
      return false;
   }
   
   // Returns true if the snake eats itself
   /**
    * Eat itself.
    *
    * @return true, if successful
    */
   public boolean eatItself() {
      int headX = getHeadX();
      int headY = getHeadY();
      // eat itself if the (headX, headY) hits its body segment (4th onwards) 
      for (int i = 3; i < snakeSegments.size(); ++i) {
         SnakeSegment segment = snakeSegments.get(i);
         if (segment.contains(headX, headY)) {
            return true;
         }
      }
      return false;
   }
   
   // Returns true if the enemy head hits the snake
   /**
    * Enemy hit.
    *
    * @param enemy the enemy
    * @return true, if successful
    */
   public boolean enemyHit(Enemy enemy) {
      int headX = enemy.getHeadX();
      int headY = enemy.getHeadY(); 
      for (int i = 0; i < snakeSegments.size(); ++i) {
         SnakeSegment segment = snakeSegments.get(i);
         if (segment.contains(headX, headY)) {
            return true;
         }
      }
      return false;
   }
   
   //Destroys part of snake
   /**
    * Destroy segments.
    */
   public void destroySegments()
   {
      for (int i = 0; i < this.getLength()/2; i++)
      {
         shrink();
      }
   }
   
   // Draw itself.
   /**
    * Draw.
    *
    * @param g the g
    */
   public void draw(Graphics g) {
	   //color depends on state of snake
	  if (state == 0)
	  {
		  g.setColor(color); //normal color
	  }
	  else
	  {
		  g.setColor(Color.CYAN); //invincible
	  }
	  
      for (int i = 0; i < snakeSegments.size(); ++i) {
         snakeSegments.get(i).draw(g);   // draw all the segments
      }
      if (snakeSegments.size() > 0) {
         g.setColor(colorHead);
         g.fill3DRect(getHeadX() * Window.CELL_SIZE, getHeadY()
               * Window.CELL_SIZE, Window.CELL_SIZE - 1,
               Window.CELL_SIZE - 1, true);
      }
   }
   
   // For debugging.
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("**Snake** Direction is " + direction + "\n" + "" + state + "\n");
      int count = 1;
      for (SnakeSegment segment : snakeSegments) {
         sb.append("  Segment " + count + ": ");
         ++count;
         sb.append(segment);
         sb.append('\n');
      }
      return sb.toString();
   }
}