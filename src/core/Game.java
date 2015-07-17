/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */

package core;


import items.Food;
import items.Genocider;
import items.Gun;
import items.Invincibility;
import items.Item;
import items.Laser;
import items.LaserBolt;
import items.Projectile;
import items.Shrinker;
import items.SuperFood;
import items.SuperGun;
import items.SuperLaser;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.Clip;

import enemies.Enemy;
import enemies.EnemySegment;
import enemies.Enemy_Shooter;
import enemies.Enemy_Template;
import bosses.Boss;
import bosses.Boss2;
import bosses.BossSegment;
import bosses.Boss_Template;


// TODO: Auto-generated Javadoc
/**
 * Actual game.
 * 
 * @author Tiger Festival
 */

public class Game {
	
	  // Define instance variables for the game objects
	/** The food. */
  	private Food food;
	   
   	/** The super food. */
   	private SuperFood superFood;
	   
   	/** The snake. */
   	private Snake snake;
	   
   	/** ArrayList of enemies. */
   	private static ArrayList<Enemy_Template> enemies; 
	   
   	/** The items in the game. */
   	private static ArrayList<Item> items; 
	   
   	/** The items that the snake has. */
   	private static Item [] itemPouch; 
	   
   	/** The pouch size. */
   	private int pouchSize;
	   
   	/** The total score. */
   	private static int score;    
	   
   	/** Temporary score to determine leveling up. */
   	private int tempScore; 
	   
   	/** What to add to the score. */
   	private static int addToScore;    
	   
   	/** What level you're on, does different things on each level. */
   	private static int level;   
	   
   	/** Heads up display for controls and stuff. */
   	private Image hud;    
	   
   	/** The projectiles. */
   	private java.util.List<Projectile> projectiles = new ArrayList<Projectile>();
	   
   	/** The count. */
   	private int count; //for timing purposes
	   
   	/** The intro rumble. */
   	private Clip introRumble;
	   
   	/** The bosses. */
   	private static ArrayList<Boss_Template> bosses; //arraylist of enemies
	   
   	/** The spawn rate. */
   	private int spawnRate; //numbers 1-10 with 1 resulting in a 100% change enemies will spawn every 10 seconds
	   
	   //DON'T FORGET TO ADD GETTER AND SETTER WHEN ADDING MORE INSTANCE VARIABLES

    /**
   	 * Instantiates a new game.
   	 */
   	public Game()
    {
        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
        
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                // Sets variables and objects for the game.
                initialize();
                
                // Load game files (images, sounds, ...)
                loadContent();
                
                //generate the snake and food
                snake.regenerate();
                generateFood();
                generateItem();
                
                Framework.gameState = Framework.GameState.PLAYING;
            }
        };
        threadForInitGame.start();
    }
    
    
   /**
     * Set variables and objects for the game.
     */
    public void initialize()
    {
    	// Allocate a new snake and a food item, do not regenerate.
        snake = new Snake();
        enemies = new ArrayList<Enemy_Template>();
        bosses = new ArrayList<Boss_Template>();
        items = new ArrayList<Item>();
        pouchSize = 3; //size of pouch defaults to 3
        itemPouch = new Item [pouchSize];
        food = new Food();
        superFood = new SuperFood();
        projectiles = new ArrayList<Projectile>();
        count = 0;
        spawnRate = 7;
        score = 0;
        tempScore = 0;
        addToScore = 0;
        level = 1;
    }
    
    /**
     * Load game files - images, sounds, ...
     */
    public void loadContent()
    {/*
    	try {
			introRumble = AudioSystem.getAudioInputStream(getClass().getResource("sound/Low Sci Fi Rumble Loop.wav"));
		} catch (IOException e) { }*/
    }   
    
    
    /**
     * Restart game - reset some variables.
     */
    public void RestartGame()
    {
    	//reset stuffs
        enemies.clear();
        items.clear();
        for (int i = 0; i < itemPouch.length; i++)
        {
        	itemPouch[i] = null;
        }
        projectiles.clear();
        count = 0;
        bosses.clear();
        
        // Sets variables and objects for the game.
        initialize();
        // Load game files (images, sounds, ...)
        loadContent();
        
        //generate the snake and food and maybe item
        snake.regenerate();
        generateFood();
        generateItem();
    }
    
    
    /**
     * Update game logic.
     * 
     * @param gameTime gameTime of the game.
     * @param mousePosition current mouse position.
     */
    public void UpdateGame(long gameTime, Point mousePosition)
    {
    	snake.update();
    	
        //collision detection for snake
        processCollision();
        
        //if there are enemies, update and collision check
        if (!enemies.isEmpty())
        {
  	      for (int i=0; i<enemies.size(); i++){
  	             enemies.get(i).update();
  	             processCollisionEnemy(enemies.get(i));
  	      }
        }
        
      //if there are bosses, update and collision check
        if (!bosses.isEmpty())
        {
        	for (int i=0; i < bosses.size(); i++){
 	             bosses.get(i).update();
 	             processCollisionBoss(bosses.get(i));
 	             
 	             if (!(bosses.get(i).isAlive())) //remove if boss is dead
 	            	 bosses.remove(bosses.get(i));
 	      }
        }
        
        
      //if there are projectiles, update and collision check
        if (!projectiles.isEmpty())
        {
  	      for (int i = 0; i < projectiles.size(); i++)
  	      {
  	    	  if (projectiles.get(i) instanceof LaserBolt)
  	    		  ((LaserBolt)(projectiles.get(i))).update(); 
  	    	  
  	    	  else
  	    	  {
  	    		projectiles.get(i).update(); //may need to change this update with
					//different types of projectiles
  	    	  }
  	    	  
  	    	  //^ ^ ^ ^ ^ MAY NEED TO ADD DIFFERENT TYPES OF PROJECTILES TO UPDATE DIFF. HERE
  	    	  
  	    	//check to see if they're still on the screen
      	     if (projectiles.get(i) != null && !(Window.contentPane).contains(
      	    		 projectiles.get(i).getHeadX(), projectiles.get(i).getHeadY())) 
      	     {
      	    	 if (projectiles.get(i) instanceof LaserBolt && projectiles.get(i).getLength() > 1)
      	    	 {	 
      	    		 projectiles.get(i).shrink(); 
      	    	 	 projectileCollisions(projectiles.get(i));
      	    	 }
      	    	 else
      	    	 {
      	    		 projectiles.remove(i); //remove if not on the screen
      	    	 }
      	     }
      	     else
      	     {
      	    	 projectileCollisions(projectiles.get(i));
      	     }
      	     
      	     
  	      }
        }
        
        //Item AND enemy spawn Generation (timing and probability)
        if (count == 10 * Framework.getGAME_FPS()) //if it's been 10 seconds
        {
        	//item
            Random rand = new Random();
            int random = rand.nextInt(10)+1;
            if (random > 5) //probability of item generation
            {
            	generateItem();
            }
            
            //enemy
            if (rand.nextInt(10)+1 > spawnRate) //probability of enemy generation
            {
            	enemies.add(Enemy.makeEnemy());
            }
            
            count = 0; //reset counter
        }
        count++;
        
        //record level to check if it changed
        int tempLvl = level;
        
        //update level if necessary
        levelCheck();
        
        //check level and if it changed, begin the next level
        if (tempLvl != level)
        {
  	      levelStart(level);
        }
     }
    
    
    /**
     * Draw the game to the screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public void Draw(Graphics2D g2d, Point mousePosition)
    {
    	
    }
    
    //***************(COLLISION DETECTION)************
     
    
    // Collision detection and response
    /**
     * Process collision enemy.
     *
     * @param enemy the enemy
     */
    public void processCollisionEnemy(Enemy_Template enemy) {
       //Deals with enemy collisions
          if (snakeHit(enemy, snake))
          {
         	if (snake.getState() == 0)
         	{
         		Framework.gameState = Framework.GameState.GAMEOVER;
         		return;
         	}
         	if (snake.getState() == 1)
         	{
         		enemies.remove(enemy);
         		addToScore = 1500;
         		score += addToScore;
         		return;
         	}
          }
          
          if (snake.contains(enemy.getHeadX(), enemy.getHeadY()))
          {
         	if (snake.getState() == 0)
          	{
 	            snake.destroySegments();
 	            addToScore = -500;
 	            score += addToScore;
 	            return;
          	}
         	if (snake.getState() == 1)
         	{
         		enemies.remove(enemy);
         		addToScore = 1500;
         		score += addToScore;
         		return;
         	}
          }
    }
    
 // Collision detection and response
    /**
  * Process collision boss.
  *
  * @param boss the boss
  */
 public void processCollisionBoss(Boss_Template boss) {
       //Deals with boss collisions
          if (snakeHit(boss, snake))
          {
         	if (snake.getState() == 0)
         	{
         		Framework.gameState = Framework.GameState.GAMEOVER;
         		return;
         	}
         	if (snake.getState() == 1)
         	{
         		boss.damage(1);
         		return;
         	}
          }
          
          for (int i = 0; i < boss.getBossSegments().size(); ++i) 
   	   	{
      		BossSegment segment = boss.getBossSegments().get(i);
          if (snake.contains(segment.getHeadX(), segment.getHeadY()))
          {
         	if (snake.getState() == 0)
          	{
 	            snake.destroySegments();
 	            addToScore = -500;
 	            score += addToScore;
 	            return;
          	}
         	if (snake.getState() == 1)
         	{
         		boss.damage(10);
         		return;
         	}
          }
   	   	}
    }
    
    // Collision detection and response
    /**
     * Process collision.
     */
    public void processCollision() {
       // check if this snake eats the food item
       int headX = snake.getHeadX();
       int headY = snake.getHeadY();
       
       //food collision
       if (headX == food.getX() && headY == food.getY() ||
           headX == superFood.getX() && headY == superFood.getY()) {
          if (headX == superFood.getX() && headY == superFood.getY())
          {                          
             //increase score and display
             addToScore = snake.getLength() * 75;
             score += addToScore;
             
             if (snake.getState() == 1) //if snake is already invincible
             {
            	 snake.setState(1); //make invincible
            	 snake.addToCount((int) (-12 * Framework.getGAME_FPS())); //8 more seconds of invincibility
             }
             else
             {
            	 snake.setState(1); //make invincible
            	 snake.setCount(0); //reset the count (in case the state is already 1)
             }
             
          }
          else
          {                         
             //increase score and display
             addToScore = snake.getLength() * 35;
             score += addToScore;
          }
          // food eaten, regenerate one
          generateFood();
       } 
       else {
          // not eaten, shrink the tail
          snake.shrink();
       }
       
       //item collision
       for (int i=0; i<items.size(); i++)
       {
    	   boolean itemAlive = true;
	       if (headX == items.get(i).getX() && headY == items.get(i).getY()) 
	       {        
	    	   	//give item to snake and remove from board if you have room in pouch
	    	   for (int j = 0; j < itemPouch.length; j++)
	           {
		    	   	if (itemPouch[j] == null && itemAlive)
		    	   	{
		    	   		itemPouch[j] = items.get(i);	    	   	
		    	   		items.remove(items.get(i));
		    	   		itemAlive = false;
		    	   	}
	           }
	       }
       }
       
       // Check if the snake moves out of bounds
       if (!(Window.contentPane).contains(headX, headY)) {
    	   Framework.gameState = Framework.GameState.GAMEOVER;
          return;
       }
    
       // Check if the snake eats itself
       if (snake.eatItself()) {
    	   Framework.gameState = Framework.GameState.GAMEOVER;
          return;
       }
    }
    
    /**
     * Projectile collisions.
     *
     * @param projectile the projectile
     */
    public void projectileCollisions(Projectile projectile)
    {
     	   //************SNAKE*****************
            if (snakeHit(projectile, snake))
            {
         	//if the snake is normal and friendly fire is on for the projectile
           	if (snake.getState() == 0 && projectile.getShooter() == 1)
           	{
           		snake.destroySegments();
   	            addToScore = -500;
   	            score += addToScore;
   	            return;
           	}
           	if (snake.getState() == 1 && projectile.getShooter() == 1) //snake is invincible and projectile can hit snake
           	{
           		projectiles.remove(projectile);
           		return;
           	}
            }
            
            if (snake.contains(projectile.getHeadX(), projectile.getHeadY()))
            {
	           	if (snake.getState() == 0 && projectile.getShooter() == 1)//snake is normal
	            	{
	   	            snake.destroySegments();
	   	            addToScore = -500;
	   	            score += addToScore;
	   	            return;
	            	}
	           	if (snake.getState() == 1 && projectile.getShooter() == 1)//snake is invincible
	           	{
	           		projectiles.remove(projectile);
	           		return;
	           	}
            }
            
            //********ENEMIES****************
            
          //if there are enemies, collision check
            if (!enemies.isEmpty())
            {
      	      for (int i=0; i<enemies.size(); i++)
      	      {
		            if (projectileHit(projectile, enemies.get(i)) && projectile.getShooter() == 0
		            		|| enemyHit(projectile, enemies.get(i)) && projectile.getShooter() == 0)
		            {
		          	  enemies.remove(enemies.get(i));
		          	  addToScore = 2000;
		          	  score += addToScore;
		            }
      	      }
            }
            
          //************BOSSES****************
            
            //if there are enemies, collision check
              if (!bosses.isEmpty())
              {
        	      for (int i=0; i < bosses.size(); i++)
        	      {
  		            if (projectileHit(projectile, bosses.get(i)) && projectile.getShooter() == 0
  		            		|| bossHit(projectile, bosses.get(i)) && projectile.getShooter() == 0)
  		            {
  		              bosses.get(i).damage(projectile.getDamage());
  		          	  addToScore = projectile.getDamage() * 100;
  		          	  score += addToScore;
  		            }
        	      }
              }
              
    }
    
    
 // Returns true if the enemy hits the snake
    /**
  * Snake hit.
  *
  * @param enemy the enemy
  * @param snake the snake
  * @return true, if successful
  */
 public boolean snakeHit(Enemy_Template enemy, Snake snake) {
       for (int i = 0; i < enemy.getEnemySegments().size(); ++i) {
          EnemySegment segment = enemy.getEnemySegments().get(i);
          if (segment.contains(snake.getHeadX(), snake.getHeadY())) {
             return true;
          }
       }
       return false;
    }
    
 // Returns true if the enemy hits a projectile
    /**
  * Enemy hit.
  *
  * @param projectile the projectile
  * @param enemy the enemy
  * @return true, if successful
  */
 public boolean enemyHit(Projectile projectile, Enemy_Template enemy) {
	       if (projectile.contains(enemy.getHeadX(), enemy.getHeadY()))
	       {
	          return true;
	       }
       return false;
    }
    
 // Returns true if the boss hits a projectile
    /**
  * Boss hit.
  *
  * @param projectile the projectile
  * @param boss the boss
  * @return true, if successful
  */
 public boolean bossHit(Projectile projectile, Boss_Template boss) {
    	for (int i = 0; i < boss.getBossSegments().size(); ++i) 
 	   	{
    		BossSegment segment = boss.getBossSegments().get(i);
	       if (projectile.contains(segment.getHeadX(), segment.getHeadY()))
	       {
	    	   projectiles.remove(projectile);
	          return true;
	       }
 	   	}
       return false;
    }
    
 // Returns true if the projectiles hits the snake
    /**
  * Snake hit.
  *
  * @param projectile the projectile
  * @param snake the snake
  * @return true, if successful
  */
 public boolean snakeHit(Projectile projectile, Snake snake) {
       if (projectile.contains(snake.getHeadX(), snake.getHeadY()))
       {
          return true;
       }
       return false;
    }
    
 // Returns true if the projectiles hits an enemy
    /**
  * Projectile hit.
  *
  * @param projectile the projectile
  * @param enemy the enemy
  * @return true, if successful
  */
 public boolean projectileHit(Projectile projectile, Enemy_Template enemy){
       if (enemy.contains(projectile.getHeadX(), projectile.getHeadY()))
       {
          return true;
       }
       return false;
    }
    
 // Returns true if the projectiles hits a boss
    /**
  * Projectile hit.
  *
  * @param projectile the projectile
  * @param boss the boss
  * @return true, if successful
  */
 public boolean projectileHit(Projectile projectile, Boss_Template boss){
       if (boss.contains(projectile.getHeadX(), projectile.getHeadY()))
       {
    	   projectiles.remove(projectile);
          return true;
       }
       return false;
    }
    
 // Returns true if the boss hits the snake
    /**
  * Snake hit.
  *
  * @param boss the boss
  * @param snake the snake
  * @return true, if successful
  */
 public boolean snakeHit(Boss_Template boss, Snake snake) {
       for (int i = 0; i < boss.getBossSegments().size(); ++i) {
          BossSegment segment = boss.getBossSegments().get(i);
          if (segment.contains(snake.getHeadX(), snake.getHeadY())) {
             return true;
          }
       }
       return false;
    }
    
 // Check if this pit contains the given (x, y), for collision detection
    /**
  * Contains.
  *
  * @param x the x
  * @param y the y
  * @return true, if successful
  */
 public boolean contains(int x, int y) {
       if ((x < 0) || (x >= Window.ROWS)) {
          return false;
       }
       if ((y < 0) || (y >= Window.COLUMNS - 4)) {
          return false;
       }
       return true;
    }
    
    // ***********OTHER METHODS (LEVEL STUFF)*****************.
    
    
    //determine level
    /**
     * Level check.
     */
    public void levelCheck()
    {
    	if ((level * 1.0) % 3 == 0) //if level is a multiple of 3 (boss battle)
    	{
    		tempScore = score - 15000;
    	}
    	
 	   if (score >= tempScore + 5000 * level && (level * 1.0) % 3 != 0) //level up if score increases by 5000 unless the level
 		   													//is a multiple of 3 (boss battle)
 	   {
 		   increaseLevel();
 	   }
    }
    
    /**
     * Increase level.
     */
    public void increaseLevel()
    {
 	   level += 1;
    }
    
    //begin the next level
    /**
     * Level start.
     *
     * @param level the level
     */
    public void levelStart(int level)
    {
 	   switch (level)
 	      {
 	      	case 1 : //do nothing
 	    	  break;
 	      	case 2 : levelTwo();
 	      	  break;
 	      	case 3 : levelThree();
 	      	  break;
 	      	case 4 : levelFour();
 	      	  break;
 	      	case 5 : 
 	      	  break;
 	      	case 6 : levelSix();
 	      	  break;
 	      	case 7 :
 	      	  break;
 	      	case 8 :
 	      	  break;
 	      }
    }
    
    //Different level stuff
    /**
     * Level two.
     */
    public void levelTwo()
    {
 	   for (int x = 0; x < 3; x++)
 	   {
 		   enemies.add(Enemy.makeEnemy());
 	   }
 	   spawnRate = 6;
    }
    
    /**
     * Level three.
     */
    public void levelThree()
    {
 	   bosses.add(Boss.makeBoss());
    }
    
    /**
     * Level four.
     */
    public void levelFour()
    {
       for (int x = 0; x < 3; x++)
  	   {
    		enemies.add(Enemy_Shooter.makeEnemy_Shooter());
  	   }
    }
    
    /**
     * Level six.
     */
    public void levelSix()
    {
    	 bosses.add(Boss2.makeBoss());
    }
    
    
    
    //determines which food to generate next
    /**
     * Generate food.
     */
    public void generateFood()
    {
       int x, y; 
       Random rand = new Random();
       if (rand.nextInt(10)+1 > 8 && score != 0) 
       {
          do {
             food.erase();
             superFood.erase();
             superFood.regenerate();
             x = superFood.getX();
             y = superFood.getY();
             enemies.add(Enemy.makeEnemy(x+5, y-4));
             
          } while (snake.contains(x, y)); // regenerate if food placed under the snake
       }
       else
       {
          do {
             food.erase();
             superFood.erase();
             food.regenerate();
             x = food.getX();
             y = food.getY();
          } while (snake.contains(x, y)); // regenerate if food placed under the snake
       }
    }
    

    //determines which food to generate next
    /**
     * Generate item.
     */
    public void generateItem()
    {
    	Random rand = new Random();
    	int x, y; 
    	int random = rand.nextInt(100)+1;
        if (random >= 70) //probability of individual item generation (**all numbers must be covered!!)
        { 	
        	Gun gun = new Gun();
	        do {
	            gun.erase();
	            gun.regenerate();
	            x = gun.getX();
	            y = gun.getY();
	            items.add(gun);
	         } while (snake.contains(x, y)); // regenerate if item placed under the snake
        }
        
        else if (random >= 50) 
        { 	
        	Laser laser = new Laser();
	        do {
	            laser.erase();
	            laser.regenerate();
	            x = laser.getX();
	            y = laser.getY();
	            items.add(laser);
	         } while (snake.contains(x, y)); // regenerate if item placed under the snake
        }
        
        else if (random >= 40) 
        { 	
        	SuperGun superGun = new SuperGun();
	        do {
	        	superGun.erase();
	        	superGun.regenerate();
	            x = superGun.getX();
	            y = superGun.getY();
	            items.add(superGun);
	         } while (snake.contains(x, y)); // regenerate if item placed under the snake
        }
        
        else if (random >= 20) 
        { 	
        	SuperLaser superLaser = new SuperLaser();
	        do {
	        	superLaser.erase();
	        	superLaser.regenerate();
	            x = superLaser.getX();
	            y = superLaser.getY();
	            items.add(superLaser);
	         } while (snake.contains(x, y)); // regenerate if item placed under the snake
        }
        
        else if (random >= 10) 
        { 	
        	Shrinker shrinker = new Shrinker();
	        do {
	        	shrinker.erase();
	        	shrinker.regenerate();
	            x = shrinker.getX();
	            y = shrinker.getY();
	            items.add(shrinker);
	         } while (snake.contains(x, y)); // regenerate if item placed under the snake
        }
        
        else if (random >= 3) 
        { 	
        	Invincibility invincibility = new Invincibility();
	        do {
	        	invincibility.erase();
	        	invincibility.regenerate();
	            x = invincibility.getX();
	            y = invincibility.getY();
	            items.add(invincibility);
	         } while (snake.contains(x, y)); // regenerate if item placed under the snake
        }
        
        else if (random >= 0) 
        { 	
        	Genocider genocider = new Genocider();
	        do {
	        	genocider.erase();
	        	genocider.regenerate();
	            x = genocider.getX();
	            y = genocider.getY();
	            items.add(genocider);
	         } while (snake.contains(x, y)); // regenerate if item placed under the snake
        }
        
        //^^^^^^^^^^^^OTHER ITEMS GO HERE
        
        
    }
    
    /**
     * Use item.
     *
     * @param item the item
     */
    public void useItem(Item item)
    {
    	if (item instanceof Gun) //if it's a gun
    	{
    		((Gun)item).use(snake); //use the item
    	}
    	
    	if (item instanceof Laser) 
    	{
    		((Laser)item).use(snake); //use the item
    	}
    	
    	if (item instanceof SuperGun) 
    	{
    		((SuperGun)item).use(snake); //use the item
    	}
    	
    	if (item instanceof SuperLaser) 
    	{
    		((SuperLaser)item).use(snake); //use the item
    	}
    	
    	if (item instanceof Shrinker) 
    	{
    		((Shrinker)item).use(snake); //use the item
    	}
    	
    	if (item instanceof Invincibility) 
    	{
    		((Invincibility)item).use(snake); //use the item
    	}
    	
    	if (item instanceof Genocider) 
    	{
    		((Genocider)item).use(snake); //use the item
    	}
    	
    	// ^ ^ other items go here ^ ^
    }
    
    // *************Getters and Setters*******************.
     
     
    
    /**
     * Gets the spawn rate.
     *
     * @return the spawn rate
     */
    public int getSpawnRate()
    {
    	return spawnRate;
    }
    
    /**
     * Sets the spawn rate.
     *
     * @param spawn the new spawn rate
     */
    public void setSpawnRate(int spawn)
    {
    	this.spawnRate = spawn;
    }
    
     
     /**
      * Gets the food.
      *
      * @return the food
      */
     public Food getFood()
     {
  	   return food;
     }
     
     /**
      * Sets the food.
      *
      * @param food the new food
      */
     public void setFood(Food food)
     {
  	   this.food = food;
     }
     
     //**
     /**
      * Gets the super food.
      *
      * @return the super food
      */
     public SuperFood getSuperFood()
     {
  	   return superFood;
     }
     
     /**
      * Sets the super food.
      *
      * @param food the new super food
      */
     public void setSuperFood(SuperFood food)
     {
  	   this.superFood = food;
     }
     
     //**
     /**
      * Gets the snake.
      *
      * @return the snake
      */
     public Snake getSnake()
     {
  	   return snake;
     }
     
     /**
      * Sets the snake.
      *
      * @param snake the new snake
      */
     public void setSnake(Snake snake)
     {
  	   this.snake = snake;
     }
     
   //**
     /**
    * Gets the bosses.
    *
    * @return the bosses
    */
   public ArrayList<Boss_Template>  getBosses()
     {
  	   return bosses;
     }
     
     /**
      * Sets the bosses.
      *
      * @param bosses the new bosses
      */
     public void setBosses(ArrayList<Boss_Template> bosses)
     {
  	   this.bosses = bosses;
     }
    
     
     //**
     /**
      * Gets the enemies.
      *
      * @return the enemies
      */
     public ArrayList<Enemy_Template>  getEnemies()
     {
  	   return enemies;
     }
     
     /**
      * Sets the enemies.
      *
      * @param enemies the new enemies
      */
     public void setEnemies(ArrayList<Enemy_Template> enemies)
     {
  	   this.enemies = enemies;
     }
     
     //**
     /**
      * Gets the items.
      *
      * @return the items
      */
     public ArrayList<Item>  getItems()
     {
  	   return items;
     }
     
     /**
      * Sets the items.
      *
      * @param items the new items
      */
     public void setItems(ArrayList<Item> items)
     {
  	   this.items = items;
     }
     
   //**
     /**
    * Gets the item pouch.
    *
    * @return the item pouch
    */
   public Item [] getItemPouch()
     {
  	   return itemPouch;
     }
     
     /**
      * Sets the item pouch.
      *
      * @param items the new item pouch
      */
     public void setItemPouch(Item [] items)
     {
  	   this.itemPouch = items;
     }
     
     //**
     /**
      * Gets the score.
      *
      * @return the score
      */
     public int getScore()
     {
  	   return score;
     }
     
     /**
      * Sets the score.
      *
      * @param score the new score
      */
     public void setScore(int score)
     {
  	   this.score = score;
     }
     
     /**
      * Gets the adds the to score.
      *
      * @return the adds the to score
      */
     public int getAddToScore()
     {
    	 return addToScore;
     }
     
     /**
      * Sets the adds the to score.
      *
      * @param addToScore the new adds the to score
      */
     public void setAddToScore(int addToScore)
     {
    	 this.addToScore = addToScore;
     }
     
     /**
      * Adds the to score.
      *
      * @param add the add
      */
     public void addToScore(int add)
     {
  	   score += add;
     }
     
     //**
     /**
      * Gets the level.
      *
      * @return the level
      */
     public int getLevel()
     {
  	   return level;
     }
     
     /**
      * Sets the level.
      *
      * @param level the new level
      */
     public void setLevel(int level)
     {
  	   this.level = level;
     }
     
     
     //**
     /**
      * Gets the hud.
      *
      * @return the hud
      */
     public Image getHud()
     {
  	   return hud;
     }
     
     /**
      * Sets the hud.
      *
      * @param hud the new hud
      */
     public void setHud(Image hud)
     {
  	   this.hud = hud;
     }
     
   //**
     /**
    * Gets the pouch size.
    *
    * @return the pouch size
    */
   public int getPouchSize()
     {
  	   return pouchSize;
     }
     
     /**
      * Sets the pouch size.
      *
      * @param size the new pouch size
      */
     public void setPouchSize(int size)
     {
  	   this.pouchSize = size;
     }
     
     //private java.util.List<Projectile> projectiles = new ArrayList<Projectile>();
     //**
     /**
      * Gets the projectiles.
      *
      * @return the projectiles
      */
     public java.util.List<Projectile> getProjectiles()
     {
    	 return projectiles;
     }
     
     /**
      * Sets the projectiles.
      *
      * @param projectiles the new projectiles
      */
     public void setProjectiles(java.util.List<Projectile> projectiles)
     {
    	 this.projectiles = projectiles;
     }
     
}
