/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package bosses;
import java.awt.Graphics;

import core.Window;
// TODO: Auto-generated Javadoc
/**
 * BossSegment represents one horizontal or vertical segment of a Boss. The "head" of
 * this segment is at (headX, headY). The segment is drawn starting from the "head"
 * and proceeding "length" cells in "direction", until it reaches the "tail".
 */
public class BossSegment {
   
   /** The head y. */
   private int headX, headY;   // The position of the head of this segment
   
   /** The length. */
   private int length;         // length of this segment
   
   /** The direction. */
   private Boss_Template.Direction direction;   // direction of this segment
   
// Construct a new Boss segment at given (headX, headY), length and direction.
   /**
 * Instantiates a new boss segment.
 *
 * @param headX the head x
 * @param headY the head y
 * @param length the length
 * @param direction the direction
 */
public BossSegment(int headX, int headY, int length, Boss_Template.Direction direction) {
      this.headX = headX;
      this.headY = headY;
      this.direction = direction;
      this.length = length;
   }
   
   // Grow by adding one cell to the head of this segment.
   /**
    * Grow.
    */
   public void grow() {
      ++length;
      // need to adjust the headX and headY
      switch (direction) {
         case LEFT:  --headX; break;
         case RIGHT: ++headX; break;
         case UP:    --headY; break;
         case DOWN:  ++headY; break;
      }
   }
   
   // Shrink by removing one cell from the tail of this segment.
   /**
    * Shrink.
    */
   public void shrink() {
      length--;  // no change in headX and headY needed
   }
   
   // Get the length, in cells, of this segment.
   /**
    * Gets the length.
    *
    * @return the length
    */
   public int getLength() { return length; }
   
   // Get the X coordinate of the cell that contains the head of this Boss segment.
   /**
    * Gets the head x.
    *
    * @return the head x
    */
   public int getHeadX() { return headX; }
   
   // Get the Y coordinate of the cell that contains the head of this Boss segment.
   /**
    * Gets the head y.
    *
    * @return the head y
    */
   public int getHeadY() { return headY; }
   
   // Get the X coordinate of the cell that contains the tail of this Boss segment.
   /**
    * Gets the tail x.
    *
    * @return the tail x
    */
   private int getTailX() {
      if (direction == Boss.Direction.LEFT) {
         return headX + length - 1;
      } else if (direction == Boss.Direction.RIGHT) {
         return headX - length + 1;
      } else {
         return headX;
      }
   }
   
   // Get the Y coordinate of the cell that contains the tail of this Boss segment.
   /**
    * Gets the tail y.
    *
    * @return the tail y
    */
   private int getTailY() {
      if (direction == Boss.Direction.DOWN) {
         return headY - length + 1;
      } else if (direction == Boss.Direction.UP) {
         return headY + length - 1;
      } else {
         return headY;
      }
   }
   
   // Returns true if the Boss segment contains the given cell. Used for collision detection.
   /**
    * Contains.
    *
    * @param x the x
    * @param y the y
    * @return true, if successful
    */
   public boolean contains(int x, int y) {
      switch (direction) {
         case LEFT:  return ((y == this.headY) && ((x >= this.headX) && (x <= getTailX())));
         case RIGHT: return ((y == this.headY) && ((x <= this.headX) && (x >= getTailX())));
         case UP:    return ((x == this.headX) && ((y >= this.headY) && (y <= getTailY())));
         case DOWN:  return ((x == this.headX) && ((y <= this.headY) && (y >= getTailY())));
      }
      return false;
   }
   
   // Draw this segment.
   /**
    * Draw.
    *
    * @param g the g
    */
   public void draw(Graphics g) {
      int x = headX;
      int y = headY;
   
      switch (direction) {
         case LEFT:
            for (int i = 0; i < length; ++i) {
               g.fill3DRect(x * Window.CELL_SIZE, y * Window.CELL_SIZE,
                     Window.CELL_SIZE, Window.CELL_SIZE, true);
               ++x;
            }
            break;
         case RIGHT:
            for (int i = 0; i < length; ++i) {
               g.fill3DRect(x * Window.CELL_SIZE, y * Window.CELL_SIZE,
                     Window.CELL_SIZE, Window.CELL_SIZE, true);
               --x;
            }
            break;
         case UP:
            for (int i = 0; i < length; ++i) {
               g.fill3DRect(x * Window.CELL_SIZE, y * Window.CELL_SIZE,
                     Window.CELL_SIZE, Window.CELL_SIZE, true);
               ++y;
            }
            break;
         case DOWN:
            for (int i = 0; i < length; ++i) {
               g.fill3DRect(x * Window.CELL_SIZE, y * Window.CELL_SIZE,
                     Window.CELL_SIZE, Window.CELL_SIZE, true);
               --y;
            }
            break;
      }
   }
   
   // For debugging.
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return "Head at (" + headX + "," + headY + ")" + " to (" + getTailX() + ","
            + getTailY() + ")" + ", length is " + getLength() + ", dir is " + direction;
   }
}