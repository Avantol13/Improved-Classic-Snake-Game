/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package bosses;
// TODO: Auto-generated Javadoc
/**
 * A Boss is made up of one or more boss_TemplateSegment. The first boss_TemplateSegment is the
 * "head" of the Boss. The last boss_TemplateSegment is the "tail" of the Boss. As the
 * Boss moves, it adds one cell to the head and then removes one from the tail. If
 * the Boss eats a piece of food, the head adds one cell but the tail will not
 * shrink.
 */
public class Boss extends Boss_Template {
   
   // *************Creation methods*******************
   
   /**
    * Instantiates a new boss.
    */
   public Boss()
   {
	   super();
	   alive = true;
	   health = 200;
	   totalHealth = health;
   }
   
   /**
    * Make boss.
    *
    * @return the boss
    */
   public static Boss makeBoss() 
   {
	   Boss boss = new Boss();
	   boss.regenerate();
	   return boss;
   }
   
   /* (non-Javadoc)
    * @see bosses.Boss_Template#createShape()
    */
   public void createShape() 
   {
	   for (int i = 0; i < 8; i++)
	      {
	    	  for (int j = 0; j < 8; j++)
	    	  {
	    		  BossSegments.add(new BossSegment(getHeadX() + i, getHeadY() + j, 1, direction));
	    	  }
	      }
   }
   
   //***********toString*******************8
   // For debugging.
   /* (non-Javadoc)
    * @see bosses.Boss_Template#toString()
    */
   @Override
   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("**Boss** Direction is " + direction + "\n");
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