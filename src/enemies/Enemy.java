/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package enemies;
// TODO: Auto-generated Javadoc
/**
 * A Enemy is made up of one or more EnemySegment. The first EnemySegment is the
 * "head" of the enemy. The last EnemySegment is the "tail" of the enemy. As the
 * enemy moves, it adds one cell to the head and then removes one from the tail. If
 * the enemy eats a piece of food, the head adds one cell but the tail will not
 * shrink.
 */
public class Enemy extends Enemy_Template {
   
   // *************Creation methods*******************
   
   /**
    * Make enemy.
    *
    * @return the enemy
    */
   public static Enemy makeEnemy()
   {
	   Enemy enemy = new Enemy();
	   enemy.length = 5;
	   enemy.regenerate();
	   enemies.add(enemy.getEnemySegments());
	   enemy_TemplateCount++;
	   enemy.alive = true;
	   enemy.setHealth(10);
	   return enemy;
   }
   
   /**
    * Make enemy.
    *
    * @param x the x
    * @param y the y
    * @return the enemy
    */
   public static Enemy makeEnemy(int x, int y)
   {
	   Enemy enemy = new Enemy();
	   enemy.length = 5;
	   enemy.regenerate(x, y, Direction.LEFT);
	   enemies.add(enemy.getEnemySegments());
	   enemy_TemplateCount++;
	   enemy.alive = true;
	   return enemy;
   }
}