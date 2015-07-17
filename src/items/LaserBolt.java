/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package items;
import java.awt.Image;
import core.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class LaserBolt.
 */
public class LaserBolt extends Projectile {
	
   // Construct a new Item segment at given (headX, headY), length and direction.
   /**
    * Instantiates a new laser bolt.
    *
    * @param headX the head x
    * @param headY the head y
    * @param length the length
    * @param direction the direction
    * @param friendlyFire the friendly fire
    * @param damage the damage
    * @param image_U the image_ u
    * @param image_D the image_ d
    * @param image_L the image_ l
    * @param image_R the image_ r
    */
   public LaserBolt(int headX, int headY, int length, Projectile.Direction direction, int friendlyFire, int damage,
		   			Image image_U, Image image_D, Image image_L, Image image_R) {
      super(headX, headY, length, direction, friendlyFire, damage, image_U, image_D, image_L, image_R);
   }
  
   //**UPDATE method
   /* (non-Javadoc)
    * @see items.Projectile#update()
    */
   @Override
   public void update() //doesn't shrink projectile
   {
	   if (!(Window.contentPane).contains(this.getHeadX(), this.getHeadY())) //if not in pit
	     {
		   //don't grow
	     }
	   else
		   this.grow(); //in pit
   }
   
   // For debugging.
   /* (non-Javadoc)
    * @see items.Projectile#toString()
    */
   @Override
   public String toString() {
      return "Head at (" + headX + "," + headY + ")" + " to (" + getTailX() + ","
            + getTailY() + ")" + ", length is " + getLength() + ", dir is " + direction;
   }
}