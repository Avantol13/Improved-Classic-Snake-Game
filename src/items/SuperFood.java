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
import javax.imageio.ImageIO;
// TODO: Auto-generated Javadoc
/**
 * Food is a food item that the snake can eat. It is placed randomly in the pit.
 */
public class SuperFood extends Food{
   
   /** The color. */
   private Color color = Color.RED;   // color for display

   // Default constructor.
   /**
    * Instantiates a new super food.
    */
   public SuperFood() {
      super();
      try {
			image = ImageIO.read(getClass().getResource("images/superFood.png"));
		} catch (IOException e) { }
   }
}