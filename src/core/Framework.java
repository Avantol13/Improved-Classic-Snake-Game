/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package core;

import items.Genocider;
import items.Gun;
import items.Invincibility;
import items.Item;
import items.Laser;
import items.Projectile;
import items.Shrinker;
import items.SuperGun;
import items.SuperLaser;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import enemies.Enemy_Shooter;
import bosses.Boss;
import bosses.Boss2;
import bosses.Boss_Template;

// TODO: Auto-generated Javadoc
/**
 * Framework that controls the game (Game.java) that created it, update it and draw it on the screen.
 * 
 */

public class Framework extends Canvas{
	
	/** The done. */
	public boolean done = false;
	/**
     * Width of the frame.
     */
    public static int frameWidth;
    /**
     * Height of the frame.
     */
    public static int frameHeight;

    /**
     * Time of one second in nanoseconds.
     * 1 second = 1 000 000 000 nanoseconds
     */
    public static final long secInNanosec = 1000000000L;
    
    /**
     * Time of one millisecond in nanoseconds.
     * 1 millisecond = 1 000 000 nanoseconds
     */
    public static final long milisecInNanosec = 1000000L;
    
    /**   FPS - Frames per second How many times per second the game should update?. */
    private static final int GAME_FPS = 9; 
    /**
     * Pause between updates. It is in nanoseconds.
     */
    private static final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
    
    /**
     * Possible states of the game.
     */
    public static enum GameState{
	 /** The starting. */
	 STARTING, 
	 /** The visualizing. */
	 VISUALIZING, 
	 /** The game content loading. */
	 GAME_CONTENT_LOADING, 
	 /** The main menu. */
	 MAIN_MENU, 
	 /** The options. */
	 OPTIONS, 
	 /** The playing. */
	 PLAYING, 
	 /** The gameover. */
	 GAMEOVER, 
	 /** The destroyed. */
	 DESTROYED}
    
    /** Current state of the game. */
    public static GameState gameState;
    
    /**
     * Elapsed game time in nanoseconds.
     */
    private long gameTime;
    // It is used for calculating elapsed time.
    /** The last time. */
    private long lastTime;
    
    // The actual game
    /** The game. */
    private static Game game;
    
    /** The count. */
    private int count; //used to measure time 
    
    //*********Images for the game***********
    /** The hud. */
    private Image hud; //heads up display
    
    /** The menu background. */
    private Image menuBackground;
    
    /** The game over background. */
    private Image gameOverBackground;
    
    /** The menu. */
    private Image menu;
    
    /** The health. */
    private Image health;
    
    /** The boss 1 images. */
    private Image Boss1_1, Boss1_2, Boss1_3, Boss1_4, Boss2_1; //images for boss1
    
    /** The level images. */
    private Image lvl1, lvl2, lvl3, lvl4, lvl5, lvl6, lvl7, 
    				lvl8, lvl9, lvl0, lvl11, lvl12;
    
    /** The backgrounds. */
    private Image lvl1Back, lvl2Back, lvl3Back, lvl4Back, lvl5Back, 
    				lvl6Back, lvl7Back, lvl8Back, lvl9Back, lvl10Back, lvl11Back, lvl12Back;
    //gets the game
    /**
     * Gets the game.
     *
     * @return the game
     */
    public static Game getGame()
    {
    	return game;
    }
    
    /**
     * Sets the game.
     *
     * @param newGame the new game
     */
    public static void setGame(Game newGame)
    {
    	game = newGame;
    }
    
    /**
     * Gets the game state.
     *
     * @return the game state
     */
    public static GameState getGameState()
    {
    	return gameState;
    }
    
    /**
     * Gets the game update period.
     *
     * @return the game update period
     */
    public static long getGAME_UPDATE_PERIOD()
    {
    	return Framework.GAME_UPDATE_PERIOD;
    }
    
    /**
     * Gets the game fps.
     *
     * @return the game fps
     */
    public static long getGAME_FPS()
    {
    	return Framework.GAME_FPS;
    }
    
    /**
     * Instantiates a new framework.
     */
    public Framework()
    {
        super();
        
        gameState = GameState.VISUALIZING;
        
        //We start game in new thread.
        Thread gameThread = new Thread() {
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();
    }
    
   /**
     * Set variables and objects.
     * This method is intended to set the variables and objects for this class, variables and objects for the actual game can be set in Game.java.
     */
    private void Initialize()
    {
    	
    }
    
    /**
     * Load files - images, sounds, ...
     * This method is intended to load files for this class, 
     * files for the actual game can be loaded in Game.java.
     */
    private void LoadContent()
    {
		try {
			hud = ImageIO.read(getClass().getResource("images/Snake Game Button Bar Thing.png"));
			health = ImageIO.read(getClass().getResource("images/HealthBar100.png"));
		} catch (IOException e) { }
		
		try {
			menuBackground = ImageIO.read(getClass().getResource("images/Cloud Map.png"));
		} catch (IOException e) { }
		
		try {
			gameOverBackground = ImageIO.read(getClass().getResource("images/Absolute Rust.png"));
		} catch (IOException e) { }
		
		try {
			menu = ImageIO.read(getClass().getResource("images/menu.png"));
		} catch (IOException e) { }
		
		try {
			lvl1Back = ImageIO.read(getClass().getResource("images/Wall.png"));
			lvl2Back = ImageIO.read(getClass().getResource("images/Wall.png"));
			lvl3Back = ImageIO.read(getClass().getResource("images/Wall.png"));
			lvl4Back = ImageIO.read(getClass().getResource("images/Wall II.png"));
			lvl5Back = ImageIO.read(getClass().getResource("images/Wall II.png"));
			lvl6Back = ImageIO.read(getClass().getResource("images/Wall II.png"));
			lvl7Back = ImageIO.read(getClass().getResource("images/Ice.png"));
			lvl8Back = ImageIO.read(getClass().getResource("images/Ice.png"));
			lvl9Back = ImageIO.read(getClass().getResource("images/Ice.png"));
		} catch (IOException e) { }
		
		try {
			lvl1 = ImageIO.read(getClass().getResource("images/lvl 1.png")); 
			lvl2 = ImageIO.read(getClass().getResource("images/lvl 2.png")); 
			lvl3 = ImageIO.read(getClass().getResource("images/lvl 3.png")); 
			lvl4 = ImageIO.read(getClass().getResource("images/lvl 4.png"));
			lvl5 = ImageIO.read(getClass().getResource("images/lvl 5.png")); 
			lvl6 = ImageIO.read(getClass().getResource("images/lvl 6.png"));
			lvl7 = ImageIO.read(getClass().getResource("images/lvl 7.png")); 
			lvl8 = ImageIO.read(getClass().getResource("images/lvl 8.png"));
			lvl9 = ImageIO.read(getClass().getResource("images/lvl 9.png"));
		} catch (IOException e) { }
		
		try {
			Boss1_1 = ImageIO.read(getClass().getResource("images/boss1-1.png"));
			Boss1_2 = ImageIO.read(getClass().getResource("images/boss1-2.png"));
			Boss1_3 = ImageIO.read(getClass().getResource("images/boss1-3.png"));
			Boss1_4 = ImageIO.read(getClass().getResource("images/boss1-4.png"));
			Boss2_1 = ImageIO.read(getClass().getResource("images/boss2.png"));
		} catch (IOException e) { }
    }
    
    /**
     * In specific intervals of time (GAME_UPDATE_PERIOD) the game/logic is updated and then the game is drawn on the screen.
     */
    private void GameLoop()
    {
        // This two variables are used in VISUALIZING state of the game. We used them to wait some time so that we get correct frame/window resolution.
        long visualizingTime = 0, lastVisualizingTime = System.nanoTime();
        
        // This variables are used for calculating the time that defines for how long we should put threat to sleep to meet the GAME_FPS.
        long beginTime, timeTaken, timeLeft;
        
        while(true)
        {
            beginTime = System.nanoTime();
            switch (gameState)
            {
                case PLAYING:
                    gameTime += System.nanoTime() - lastTime;
                    
                    game.UpdateGame(gameTime, mousePosition());
                    
                    lastTime = System.nanoTime();
                       
                break;
                case GAMEOVER:
                    //...
                break;
                case MAIN_MENU:
                	
                break;
                case OPTIONS:
                    //...
                break;
                case GAME_CONTENT_LOADING:
                    //...
                break;
                case STARTING:
                    // Sets variables and objects.
                    Initialize();
                    // Load files - images, sounds, ...
                    LoadContent();
                    
                    // When all things that are called above finished, we change game status to main menu.
                    gameState = GameState.MAIN_MENU;
                break;
                case VISUALIZING:
                    // On Ubuntu OS (when I tested on my old computer) this.getWidth() method doesn't return the correct value immediately (eg. for frame that should be 800px width, returns 0 than 790 and at last 798px). 
                    // So we wait one second for the window/frame to be set to its correct size. Just in case we
                    // also insert 'this.getWidth() > 1' condition in case when the window/frame size wasn't set in time,
                    // so that we although get approximately size.
                    if(this.getWidth() > 1 && visualizingTime > secInNanosec)
                    {
                        frameWidth = this.getWidth();
                        frameHeight = this.getHeight();

                        // When we get size of frame we change status.
                        gameState = GameState.STARTING;
                    }
                    else
                    {
                        visualizingTime += System.nanoTime() - lastVisualizingTime;
                        lastVisualizingTime = System.nanoTime();
                    }
                break;
            }
            
            // Repaint the screen.
            repaint();

            /*
            //buttons
            if (!done)
            {
	           //Buttons
		        //************************************
            	
		        startButton = new JButton("Begin Anew");
		        startButton.setActionCommand("start");
		        startButton.setMnemonic(KeyEvent.VK_E);
		        startButton.setEnabled(true);
		        
		        //Listen for actions on buttons.
		        startButton.addActionListener(this);
		        startButton.addMouseListener(this);
		        startButton.setToolTipText("Go destroy some E.");
		
		        //Add Components to this container, using the default FlowLayout.
		        add(startButton);
		        //*************************************
		        
		        done = true;
            }
            */
            // Here we calculate the time that defines for how long we should put threat to sleep to meet the GAME_FPS.
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec; // In milliseconds
            // If the time is less than 10 milliseconds, then we will put thread to sleep for 10 millisecond so that some other thread can do some work.
            if (timeLeft < 10) 
                timeLeft = 10; //set a minimum
            try {
                 //Provides the necessary delay and also yields control so that other thread can do work.
                 Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }
    
    /**
     * Starts new game.
     */
    public void newGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game = new Game();
    }
    
    /**
     *  Restart game - reset game time and call RestartGame() method of game object so that reset some variables.
     */
    public void restartGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game.RestartGame();
        
        // We change game status so that the game can start.
        gameState = GameState.PLAYING;
    }
    
    /**
     * Draw the game to the screen. It is called through repaint() method in 
     * GameLoop() method.
     *
     * @param g2d the g2d
     */
    @Override
    protected void Draw(Graphics2D g2d) //change modifier to private if things go no-no
    {
        switch (gameState)
        {
            case PLAYING:
            	drawPlaying(g2d);
            break;
            case GAMEOVER:
            	drawGameOver(g2d);
            break;
            case MAIN_MENU:
            	drawMainMenu(g2d);
            break;
            case OPTIONS:
            	g2d.drawImage(menu, 0, 0, frameWidth, frameHeight, null);
            break;
            case GAME_CONTENT_LOADING:
            	drawContentLoading(g2d);
            break;
        }
    }
    
    /**
     * Draws all the necessary objects to the screen for game play.
     *
     * @param g2d the 2D Graphics object
     */
    private void drawPlaying(Graphics2D g2d)
    {
    	//Draw the Level and Background
        drawLevel(g2d);
       
        //Draw the HUD
        g2d.drawImage(hud,
            	  0, Window.CANVAS_HEIGHT - 5 * Window.CELL_SIZE, 
            	  Window.CANVAS_WIDTH, Window.CANVAS_HEIGHT,
            	  0, hud.getHeight(null), hud.getWidth(null), 0,
            	  null);
        
       //Draw the Items
       drawItems(g2d);
        
       //TODO Mouse position not working
       //game.Draw(g2d, mousePosition()); 
        
        //Draw the Projectiles
        for (int i=0; i < game.getProjectiles().size(); i++){
     	   Projectile projectile = game.getProjectiles().get(i);
     	   projectile.draw(g2d);
     	   }
         
        //Draw the Snake and Food
        game.getSnake().draw(g2d);
        game.getFood().draw(g2d);
        game.getSuperFood().draw(g2d);
        
       //Draw the Enemies
       for (int i=0; i < game.getEnemies().size(); i++){
            game.getEnemies().get(i).draw(g2d);
       }
        
       //Draw the Bosses
       drawBosses(g2d);
        
       //Draw the Items in Pouch to HUD
         drawItemsToHUD(g2d);
        
       //Draw the Game Info
       drawGameInfo(g2d);
    }
    
    /**
     * Draw game over screen.
     *
     * @param g2d the 2D Graphics object
     */
    private void drawGameOver(Graphics2D g2d)
    {
    	//Draw the Background
    	g2d.drawImage(gameOverBackground, 0, 0, frameWidth, frameHeight, null);
    	
    	//Draw the Text and other Info
    	Font f;
    	String s; 
    	 
    	 //Details of font and string
    	 f = new Font("Lucida Bright", Font.BOLD, 35);
    	 g2d.setFont(f);
         g2d.setColor(Color.WHITE);
    	 s = "GAME OVER";
    	 //draws string with string s, font f, graphics2d g2d, and place at 
    	 //centered x division and y division yDivision
    	 customDrawString(s, f, g2d, 2, 3);
  
         f = new Font("Times New Roman", Font.BOLD, 30);
         g2d.setFont(f);
         g2d.setColor(Color.WHITE);
         s = "Final Score: " + game.getScore();
         customDrawString(s, f, g2d, 2, 8, 4);
         
         f = new Font("Lucida Bright", Font.BOLD, 18);
         g2d.setFont(f);
         g2d.setColor(Color.WHITE);
         s = "Press enter to play again.";
         customDrawString(s, f, g2d, 2, 8, 6);
    }
    
    /**
     * Draw main start menu.
     *
     * @param g2d the 2D Graphics object
     */
    private void drawMainMenu(Graphics2D g2d)
    {
    	g2d.drawImage(menuBackground, 0, 0, frameWidth, frameHeight, null);
   	 
   	 g2d.drawImage(menu,
   			   Window.CANVAS_WIDTH - (5 * Window.CANVAS_WIDTH / 6), 
   			   Window.CANVAS_HEIGHT - (14 * Window.CANVAS_HEIGHT / 15)  + menu.getHeight(null),
   			   Window.CANVAS_WIDTH - (Window.CANVAS_WIDTH / 6), 
   			   Window.CANVAS_HEIGHT - (14 * Window.CANVAS_HEIGHT / 15),
   	   	       0, menu.getHeight(null), menu.getWidth(null), 0,
   	   	       null);
   	 
   	 Font f = new Font("Lucida Bright", Font.BOLD, 18);
        g2d.setFont(f);
        g2d.setColor(Color.WHITE);
        String s = "Use them arrow keys or wasd to control McSnake.";
        customDrawString(s, f, g2d, 2, 8, 6);
        
        s = "Press enter to start destroying some E.";
        customDrawString(s, f, g2d, 2, 8, 7);
    }
    
    /**
     * Draw content loading screen.
     *
     * @param g2d the 2D Graphics object
     */
    private void drawContentLoading(Graphics2D g2d)
    {
    	Font f = new Font("Lucida Bright", Font.PLAIN, 12);
        g2d.setFont(f);
    	g2d.setColor(Color.BLACK);
        g2d.drawString("McSnake is getting ready...", frameWidth / 2 - 75, frameHeight / 2);
    }
    
    /**
     * Draw level and background.
     *
     * @param g2d the 2d graphics object
     */
    private void drawLevel(Graphics2D g2d)
    {
    	switch (game.getLevel())
    	{
    	case 1: g2d.drawImage(lvl1Back, 0, 0, frameWidth, frameHeight, null);
    			g2d.drawImage(lvl1, 0, 150, lvl1.getWidth(null), lvl1.getHeight(null), null);
    		break;
    	case 2: g2d.drawImage(lvl2Back, 0, 0, frameWidth, frameHeight, null);
    			g2d.drawImage(lvl2, 0, 150, lvl2.getWidth(null), lvl2.getHeight(null), null);          
    		break;
    	case 3: g2d.drawImage(lvl3Back, 0, 0, frameWidth, frameHeight, null);
    			g2d.drawImage(lvl3, 0, 150, lvl3.getWidth(null), lvl3.getHeight(null), null); 
    		break;
    	case 4: g2d.drawImage(lvl4Back, 0, 0, frameWidth, frameHeight, null);
    			g2d.drawImage(lvl4, 0, 150, lvl4.getWidth(null), lvl4.getHeight(null), null); 
    		break;
    	case 5: g2d.drawImage(lvl5Back, 0, 0, frameWidth, frameHeight, null);
    			g2d.drawImage(lvl5, 0, 150, lvl5.getWidth(null), lvl5.getHeight(null), null); 
    		break;
    	case 6: g2d.drawImage(lvl6Back, 0, 0, frameWidth, frameHeight, null);
    			g2d.drawImage(lvl6, 0, 150, lvl6.getWidth(null), lvl6.getHeight(null), null); 
    		break;
    	case 7: g2d.drawImage(lvl7Back, 0, 0, frameWidth, frameHeight, null);
    			g2d.drawImage(lvl7, 0, 150, lvl7.getWidth(null), lvl7.getHeight(null), null); 
    		break;
    	case 8: g2d.drawImage(lvl8Back, 0, 0, frameWidth, frameHeight, null);
    			g2d.drawImage(lvl8, 0, 150, lvl8.getWidth(null), lvl8.getHeight(null), null); 
    		break;
    	case 9: g2d.drawImage(lvl9Back, 0, 0, frameWidth, frameHeight, null);
    			g2d.drawImage(lvl9, 0, 150, lvl9.getWidth(null), lvl9.getHeight(null), null); 
    		break;
    	case 10:
    		break;
    	case 11:
    		break;
    	case 12:
    		break;
    	}
    }
    
    /**
     * Draw items.
     *
     * @param g2d the 2d graphics object
     */
    private void drawItems(Graphics2D g2d)
    {
    	for (int i=0; i < game.getItems().size(); i++)
        {
        	Item item = game.getItems().get(i);
        	if (item instanceof Gun)
        		((Gun)(item)).draw(g2d);
        	if (item instanceof Laser)
        		((Laser)(item)).draw(g2d);
        	if (item instanceof SuperGun)
        		((SuperGun)(item)).draw(g2d);
        	if (item instanceof SuperLaser)
        		((SuperLaser)(item)).draw(g2d);
        	if (item instanceof Shrinker)
        		((Shrinker)(item)).draw(g2d);
        	if (item instanceof Invincibility)
        		((Invincibility)(item)).draw(g2d);
        	if (item instanceof Genocider)
        		((Genocider)(item)).draw(g2d);
        	//^^^^^^^^^^^^^^^^^OTHER ITEMS GO HERE
        	// CHECK THE METHOD FOR PROJECTILES TO ADD THOSE ALSO
        }
    }
    
    /**
     * Draw bosses.
     *
     * @param g2d the 2D graphics object
     */
    private void drawBosses(Graphics2D g2d)
    {
    	for (int i=0; i < game.getBosses().size(); i++){
        	Boss_Template boss = game.getBosses().get(i);
        	
        	if (boss instanceof Boss)
        		((Boss)(boss)).draw(g2d);
        	
        	//^^^^^^OTHER BOSSES GO HERE
            
            //draw image if boss 1
            if (game.getBosses().get(i) instanceof Boss)
            {
            	g2d.drawImage(Boss1_1, boss.getHeadX()* Window.CELL_SIZE + 4 * Window.CELL_SIZE, boss.getHeadY() * Window.CELL_SIZE,
            			boss.getHeadX() * Window.CELL_SIZE, boss.getHeadY() * Window.CELL_SIZE + 4 * Window.CELL_SIZE,
            			Boss1_1.getWidth(null), 0, 0, Boss1_1.getHeight(null),
             	        null); 
            	
            	g2d.drawImage(Boss1_2, boss.getSquare2X() * Window.CELL_SIZE + 4 * Window.CELL_SIZE, boss.getSquare2Y() * Window.CELL_SIZE,
            			boss.getSquare2X() * Window.CELL_SIZE, boss.getSquare2Y() * Window.CELL_SIZE + 4 * Window.CELL_SIZE,
            			Boss1_2.getWidth(null), 0, 0, Boss1_2.getHeight(null),
             	        null); 
            	
            	g2d.drawImage(Boss1_3, boss.getSquare3X()* Window.CELL_SIZE + 4 * Window.CELL_SIZE, boss.getSquare3Y() * Window.CELL_SIZE,
            			boss.getSquare3X() * Window.CELL_SIZE, boss.getSquare3Y() * Window.CELL_SIZE + 4 * Window.CELL_SIZE,
            			Boss1_3.getWidth(null), 0, 0, Boss1_3.getHeight(null),
             	        null);
            	
            	g2d.drawImage(Boss1_4, boss.getSquare4X()* Window.CELL_SIZE + 4 * Window.CELL_SIZE, boss.getSquare4Y() * Window.CELL_SIZE,
            			boss.getSquare4X() * Window.CELL_SIZE, boss.getSquare4Y() * Window.CELL_SIZE + 4 * Window.CELL_SIZE,
            			Boss1_4.getWidth(null), 0, 0, Boss1_4.getHeight(null),
             	        null);
            }
            
          //draw image if boss2
            if (game.getBosses().get(i) instanceof Boss2)
            {
            	g2d.drawImage(Boss2_1, (boss.getHeadX() - 1) * Window.CELL_SIZE + 8 * Window.CELL_SIZE, 
            			(boss.getHeadY() - 1) * Window.CELL_SIZE,
            			(boss.getHeadX() - 1) * Window.CELL_SIZE, 
            			(boss.getHeadY() - 1) * Window.CELL_SIZE + 8 * Window.CELL_SIZE,
            			Boss2_1.getWidth(null), 0, 0, Boss2_1.getHeight(null),
             	        null); 
            }
            
            //draw boss health
            g2d.setFont(new Font("Times New Roman", Font.BOLD, 20));
            g2d.setColor(Color.BLACK);
            
            //determines health bar image based on bosses health
            determineHealthBar(game.getBosses().get(i).getHealth(), game.getBosses().get(i));
            
            //draws the health bar
            g2d.drawImage(health, 395, 545, 395 + 6 * Window.CELL_SIZE, 545 + 2 * Window.CELL_SIZE,
    				0, health.getHeight(null), health.getWidth(null), 0,
         	        null); 
            
            //draw health if greater than or equal to zero (don't show negative numbers)
            if (game.getBosses().get(i).getHealth() >= 0)
            	g2d.drawString("" + game.getBosses().get(i).getHealth(), 418, 563);
        }
    }
    
    /**
     * Draw items to hud.
     *
     * @param g2d the 2D graphics object
     */
    private void drawItemsToHUD(Graphics2D g2d)
    {
    	 for (int i=0; i < game.getItemPouch().length; i++)
           {
               	g2d.setFont(new Font("Times New Roman", Font.BOLD, 14));
	                g2d.setColor(Color.BLACK);
	                
	                int x, y; //coordinates to draw item to 
	                x = -10; //initialize off screen
	                y = -10;
	                
	                if(i == 0) //first item
	                {
	                	x = 213;
	                	y = 544;
	                } 
	                else if (i == 1) //second
	                {
	                	x = 278;
	                	y = 544;
	                }
	                else //third
	                {
	                	x = 343;
	                	y = 544;
	                }
	                
                   if (game.getItemPouch()[i] instanceof Gun)
                   	{
                   		g2d.drawImage(Gun.getItemImage(), x, y, x + 2*Window.CELL_SIZE, y + 2*Window.CELL_SIZE,
                   				Gun.getItemImage().getWidth(null), 0, 0, Gun.getItemImage().getHeight(null), 
                        	        null); //draws item image
                   		g2d.drawString("" + game.getItemPouch()[i].getNumUses(), x+26, y+26);
                   	}
                   
	               if (game.getItemPouch()[i] instanceof Laser)
                 	{
                 		g2d.drawImage(Laser.getItemImage(), x, y, x + 2*Window.CELL_SIZE, y + 2*Window.CELL_SIZE,
                 				Laser.getItemImage().getWidth(null), 0, 0, Laser.getItemImage().getHeight(null),
                      	        null); //draws item image
                 		g2d.drawString("" + game.getItemPouch()[i].getNumUses(), x+26, y+26);
                 	}
                 
	                if (game.getItemPouch()[i] instanceof SuperGun)
             	{
             		g2d.drawImage(SuperGun.getItemImage(), x, y, x + 2*Window.CELL_SIZE, y + 2*Window.CELL_SIZE,
             				SuperGun.getItemImage().getWidth(null), 0, 0, SuperGun.getItemImage().getHeight(null),
                  	        null); //draws item image
             		g2d.drawString("" + game.getItemPouch()[i].getNumUses(), x+26, y+26);
             	}
	                
	              if (game.getItemPouch()[i] instanceof SuperLaser)
               	{
               		g2d.drawImage(SuperLaser.getItemImage(), x, y, x + 2*Window.CELL_SIZE, y + 2*Window.CELL_SIZE,
               				SuperLaser.getItemImage().getWidth(null), 0, 0, SuperLaser.getItemImage().getHeight(null),
                    	        null); //draws item image
               		g2d.drawString("" + game.getItemPouch()[i].getNumUses(), x+26, y+26);
               	}
	              
	  	          if (game.getItemPouch()[i] instanceof Shrinker)
               	{
               		g2d.drawImage(Shrinker.getItemImage(), x, y, x + 2*Window.CELL_SIZE, y + 2*Window.CELL_SIZE,
               				Shrinker.getItemImage().getWidth(null), 0, 0, Shrinker.getItemImage().getHeight(null),
                    	        null); //draws item image
               		g2d.drawString("" + game.getItemPouch()[i].getNumUses(), x+26, y+26);
               	}
	  	          
		  	        if (game.getItemPouch()[i] instanceof Invincibility)
               	{
               		g2d.drawImage(Invincibility.getItemImage(), x, y, x + 2*Window.CELL_SIZE, y + 2*Window.CELL_SIZE,
               				Invincibility.getItemImage().getWidth(null), 0, 0, Invincibility.getItemImage().getHeight(null),
                    	        null); //draws item image
               		g2d.drawString("" + game.getItemPouch()[i].getNumUses(), x+26, y+26);
               	}
		  	        
		  	        if (game.getItemPouch()[i] instanceof Genocider)
               	{
               		g2d.drawImage(Genocider.getItemImage(), x, y, x + 2*Window.CELL_SIZE, y + 2*Window.CELL_SIZE,
               				Genocider.getItemImage().getWidth(null), 0, 0, Genocider.getItemImage().getHeight(null),
                    	        null); //draws item image
               		g2d.drawString("" + game.getItemPouch()[i].getNumUses(), x+26, y+26);
               	}
               
                   // ^ ^ ^ ^ other items go here ^ ^ ^ ^ 
                   
                   
                   //Draws the number above the item image if there's an item there
                   if (i == 0 && game.getItemPouch()[0] != null)
                   {
                   	g2d.setColor(Color.WHITE);
                   	g2d.drawString("1", x - 15, y + 11); //draws number to push to use item
                   }
                   if (i == 1 && game.getItemPouch()[1] != null)
                   {
                   	g2d.setColor(Color.WHITE);
                   	g2d.drawString("2", x - 13, y + 11); //draws number to push to use item
                   }
                   if (i == 2 && game.getItemPouch()[2] != null)
                   {
                   	g2d.setColor(Color.WHITE);
                   	g2d.drawString("3", x - 12, y + 11); //draws number to push to use item
                   }
            }
    }
    
    /**
     * Draw game info.
     *
     * @param g2d the 2D graphics object
     */
    private void drawGameInfo(Graphics2D g2d)
    {
    	g2d.setFont(new Font("Times New Roman", Font.BOLD, 25));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score: " + game.getScore(), 25, 564);
        if (game.getSnake().getState() == 1)
        {
        	g2d.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            g2d.setColor(Color.CYAN);
        	g2d.drawString("Invincibility! " + (10 - game.getSnake().getCount() / 10), 470, 564);
        }
        
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        //Determine color by if the add to score is negative of positive
        if (game.getAddToScore() > 0)
        {
        	g2d.setColor(Color.BLUE);
        }
        else if (game.getAddToScore() < 0)
        {
        	g2d.setColor(Color.RED);
        }
        else
        {
        	g2d.setColor(Color.BLACK);
        }
        
        //Deals with the drawing the points earned that are added to the score
        if (game.getAddToScore() == 0)
        {
        	//do nothing
        }
        else
        {
        	//draw the add score
        	g2d.drawString(game.getAddToScore() + " Points!", 
        			game.getSnake().getHeadX() * Window.CELL_SIZE + 5, 
        			game.getSnake().getHeadY() * Window.CELL_SIZE - 5);
        	if (count == GAME_FPS - GAME_FPS / 3) //if it's been 2/3 second
        	{
        		game.setAddToScore(0);//don't draw the add score any more
        		count = 0;
        	}
        	count++;
        }
    }
    
    /**
     * Determine health bar.
     *
     * @param amountHealth the amount health
     * @param boss the boss
     */
    public void determineHealthBar(int amountHealth, Boss_Template boss)
    {
    	int total = boss.getTotalHealth();
    	
    	if (amountHealth >= total)
    	{
    		try {
    			health = ImageIO.read(getClass().getResource("images/HealthBar100.png"));
    		} catch (IOException e) { }
    	}
    	else if (amountHealth < total && amountHealth >= total - total/10)
    	{
    		try {
    			health = ImageIO.read(getClass().getResource("images/HealthBar90.png"));
    		} catch (IOException e) { }
    	}
    	else if (amountHealth < total - total/10 && amountHealth >= total - 2*(total/10))
    	{
    		try {
    			health = ImageIO.read(getClass().getResource("images/HealthBar80.png"));
    		} catch (IOException e) { }
    	}
    	else if (amountHealth < total - 2*(total/10) && amountHealth >= total - 3*(total/10))
    	{
    		try {
    			health = ImageIO.read(getClass().getResource("images/HealthBar70.png"));
    		} catch (IOException e) { }
    	}
    	else if (amountHealth < total - 3*(total/10) && amountHealth >= total - 4*(total/10))
    	{
    		try {
    			health = ImageIO.read(getClass().getResource("images/HealthBar60.png"));
    		} catch (IOException e) { }
    	}
    	else if (amountHealth < total - 4*(total/10) && amountHealth >= total - 5*(total/10))
    	{
    		try {
    			health = ImageIO.read(getClass().getResource("images/HealthBar50.png"));
    		} catch (IOException e) { }
    	}
    	else if (amountHealth < total - 5*(total/10) && amountHealth >= total - 6*(total/10))
    	{
    		try {
    			health = ImageIO.read(getClass().getResource("images/HealthBar40.png"));
    		} catch (IOException e) { }
    	}
    	else if (amountHealth < total - 6*(total/10) && amountHealth >= total - 7*(total/10))
    	{
    		try {
    			health = ImageIO.read(getClass().getResource("images/HealthBar30.png"));
    		} catch (IOException e) { }
    	}
    	else if (amountHealth < total - 7*(total/10) && amountHealth >= total - 8*(total/10))
    	{
    		try {
    			health = ImageIO.read(getClass().getResource("images/HealthBar20.png"));
    		} catch (IOException e) { }
    	}
    	else if (amountHealth < total - 8*(total/10) && amountHealth >= total - 9*(total/10))
    	{
    		try {
    			health = ImageIO.read(getClass().getResource("images/HealthBar10.png"));
    		} catch (IOException e) { }
    	}
    }
    
    /**
     * Draws the string s with font f and grapics2d g2d by dividing the drawable area into divisions and then placing the string in the 
     * upper most division.
     *
     * @param s the s
     * @param f the f
     * @param g2d the g2d
     * @param xDivision the x division
     * @param yDivision the y division
     */
    public void customDrawString(String s, Font f, Graphics2D g2d, int xDivision, int yDivision)
    {
    	 // Find the size of string s in font f in the current Graphics context g.
	   	 FontMetrics fm   = g2d.getFontMetrics(f);
	   	 java.awt.geom.Rectangle2D rect = fm.getStringBounds(s, g2d);
	
	   	 int textHeight = (int)(rect.getHeight()); 
	   	 int textWidth  = (int)(rect.getWidth());
	   	 int panelHeight= this.getHeight();
	   	 int panelWidth = this.getWidth();
	
	   	 // Center text horizontally and vertically
	   	 int x = (panelWidth  - textWidth)  / xDivision;
	   	 
	   	 int y = (panelHeight - textHeight)  / yDivision  + fm.getAscent();
	   	 
	   	 g2d.drawString(s, x, y);  // Draw the string.
    }
    
    /**
     * Draws the string s with font f and grapics2d g2d by dividing the drawable area into divisions and then using the specified
     * division (yLocation) and centered on the x axis to draw the string there. Ex: yDivision = 4 so yLocation = 4 would write in the lower fourth
     * area of the screen
     *
     * @param s the s
     * @param f the f
     * @param g2d the g2d
     * @param xDivision the x division
     * @param yDivision the y division
     * @param yLocation the y location
     */
    public void customDrawString(String s, Font f, Graphics2D g2d, int xDivision, int yDivision,  int yLocation)
    {
    	 // Find the size of string s in font f in the current Graphics context g.
	   	 FontMetrics fm   = g2d.getFontMetrics(f);
	   	 java.awt.geom.Rectangle2D rect = fm.getStringBounds(s, g2d);
	
	   	 int textHeight = (int)(rect.getHeight()); 
	   	 int textWidth  = (int)(rect.getWidth());
	   	 int panelHeight= this.getHeight();
	   	 int panelWidth = this.getWidth();
	
	   	 // Center text horizontally and vertically
	   	 int x = (panelWidth  - textWidth)  / xDivision;
	   	 
	   	 int y = ( (yLocation * panelHeight) - (panelHeight - textHeight) ) / yDivision  + fm.getAscent();
	   	 
	   	 g2d.drawString(s, x, y);  // Draw the string.
    }
    
    /**
     * Returns the position of the mouse pointer in game frame/window.
     * If mouse position is null than this method return 0,0 coordinate.
     *
     * @return the point
     */
    private Point mousePosition()
    {
        try
        {
            Point mp = this.getMousePosition();
            
            if(mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (Exception e)
        {
            return new Point(0, 0);
        }
    }
    
    /**
     * This method is called when keyboard key is released.
     * 
     * @param e KeyEvent
     */
    @Override
    public void keyReleasedFramework(KeyEvent e)
    {
        switch (gameState)
        {
            case MAIN_MENU:
            	if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    newGame();
            break;
            case PLAYING:
            break;
            case GAMEOVER:
                if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER)
                    restartGame();
            break;
        }
    }
    
    /**
     * This method is called when keyboard key is pressed.
     * 
     * @param e KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (gameState)
        {
            case MAIN_MENU:
            break;
            case PLAYING:
            	//arrow keys
            	if(e.getKeyCode() == KeyEvent.VK_UP)
            		game.getSnake().setDirection(Snake.Direction.UP);
            	if(e.getKeyCode() == KeyEvent.VK_DOWN)
              	  game.getSnake().setDirection(Snake.Direction.DOWN);
            	if(e.getKeyCode() == KeyEvent.VK_LEFT)
              	  game.getSnake().setDirection(Snake.Direction.LEFT);
            	if(e.getKeyCode() == KeyEvent.VK_RIGHT)
              	  game.getSnake().setDirection(Snake.Direction.RIGHT);
            	
            	//wasd
            	if(e.getKeyCode() == KeyEvent.VK_W)
            		game.getSnake().setDirection(Snake.Direction.UP);
            	if(e.getKeyCode() == KeyEvent.VK_S)
              	  game.getSnake().setDirection(Snake.Direction.DOWN);
            	if(e.getKeyCode() == KeyEvent.VK_A)
              	  game.getSnake().setDirection(Snake.Direction.LEFT);
            	if(e.getKeyCode() == KeyEvent.VK_D)
              	  game.getSnake().setDirection(Snake.Direction.RIGHT);
            	
            	//buttons for item use
            	if(e.getKeyCode() == KeyEvent.VK_NUMPAD1 || e.getKeyCode() == KeyEvent.VK_1)
            	{
        			try {
	            		if (game.getItemPouch()[0] != null) //if there's an item
	            		{
	            			game.useItem(game.getItemPouch()[0]); //use it
	            			if(!(game.getItemPouch()[0].hasUses())) //if it doesn't have uses left
	            			{
	            				game.getItemPouch()[0] = null; //remove it 
	            			}
	            		}
        			} catch (IndexOutOfBoundsException e1) {}
            	}
            	if(e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_2)
            	{
            		try {
            			if (game.getItemPouch()[1] != null) //if there's an item
	            		{
	            			game.useItem(game.getItemPouch()[1]); //use it
	            			if(!(game.getItemPouch()[1].hasUses())) //if it doesn't have uses left
	            			{
	            				game.getItemPouch()[1] = null; //remove it 
	            			}
	            		}
            		} catch (IndexOutOfBoundsException e1) {}
            	}
            	if(e.getKeyCode() == KeyEvent.VK_NUMPAD3 || e.getKeyCode() == KeyEvent.VK_3)
            	{
            		try{
            			if (game.getItemPouch()[2] != null) //if there's an item
	            		{
	            			game.useItem(game.getItemPouch()[2]); //use it
	            			if(!(game.getItemPouch()[2].hasUses())) //if it doesn't have uses left
	            			{
	            				game.getItemPouch()[2] = null; //remove it 
	            			}
	            		}
            		} catch (IndexOutOfBoundsException e1) {}
            	} 
            	
            	//FOR DEBUGGING PURPOSES**********************************
            	if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            	{
            		Enemy_Shooter enemy = Enemy_Shooter.makeEnemy_Shooter();
                	game.getEnemies().add(enemy);
            	}
            	if(e.getKeyCode() == KeyEvent.VK_Y)
            		game.getBosses().add(Boss2.makeBoss());
            	if(e.getKeyCode() == KeyEvent.VK_0)
            		game.addToScore(5000);
            	if(e.getKeyCode() == KeyEvent.VK_9)
            		game.getSnake().setState(1);
            	if(e.getKeyCode() == KeyEvent.VK_4)
            	{
            		SuperLaser superLaser = new SuperLaser();
            		for (int j = 0; j < game.getItemPouch().length; j++)
     	            {
	            		if (game.getItemPouch()[j] == null)
			    	   	{
	            			game.getItemPouch()[j] = superLaser;
	            			break;
			    	   	}
     	            }
            	}
            	if(e.getKeyCode() == KeyEvent.VK_3)
            	{
            		SuperGun item = new SuperGun();
            		for (int j = 0; j < game.getItemPouch().length; j++)
     	            {
	            		if (game.getItemPouch()[j] == null)
			    	   	{
	            			game.getItemPouch()[j] = item;
	            			break;
			    	   	}
     	            }
            	}
            	if(e.getKeyCode() == KeyEvent.VK_2)
            	{
            		Laser item = new Laser();
            		for (int j = 0; j < game.getItemPouch().length; j++)
     	            {
	            		if (game.getItemPouch()[j] == null)
			    	   	{
	            			game.getItemPouch()[j] = item;
	            			break;
			    	   	}
     	            }
            	}
            	if(e.getKeyCode() == KeyEvent.VK_1)
            	{
            		Gun item = new Gun();
            		for (int j = 0; j < game.getItemPouch().length; j++)
     	            {
	            		if (game.getItemPouch()[j] == null)
			    	   	{
	            			game.getItemPouch()[j] = item;
	            			break;
			    	   	}
     	            }
            	}
            	if(e.getKeyCode() == KeyEvent.VK_5)
            	{
            		Shrinker item = new Shrinker();
            		for (int j = 0; j < game.getItemPouch().length; j++)
     	            {
	            		if (game.getItemPouch()[j] == null)
			    	   	{
	            			game.getItemPouch()[j] = item;
	            			break;
			    	   	}
     	            }
            	}
            	if(e.getKeyCode() == KeyEvent.VK_6)
            	{
            		Invincibility item = new Invincibility();
            		for (int j = 0; j < game.getItemPouch().length; j++)
     	            {
	            		if (game.getItemPouch()[j] == null)
			    	   	{
	            			game.getItemPouch()[j] = item;
	            			break;
			    	   	}
     	            }
            	}
            	if(e.getKeyCode() == KeyEvent.VK_7)
            	{
            		Genocider item = new Genocider();
            		for (int j = 0; j < game.getItemPouch().length; j++)
     	            {
	            		if (game.getItemPouch()[j] == null)
			    	   	{
	            			game.getItemPouch()[j] = item;
	            			break;
			    	   	}
     	            }
            	}
            	//other items go here for debugging ^^^^^^^^^^
            	
            break;
            case GAMEOVER:
            break;
        }
    }
    
    /**
     * This method is called when mouse button is clicked.
     * 
     * @param e MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
    	switch (gameState)
        {
            case MAIN_MENU:
            	if(e.getButton() == MouseEvent.BUTTON1)
                    newGame();
            break;
            case PLAYING:
            break;
            case GAMEOVER:
            break;
            case DESTROYED:
			break;
            case GAME_CONTENT_LOADING:
			break;
            case OPTIONS:
			break;
            case STARTING:
			break;
            case VISUALIZING:
			break;
        }
    }
    
  // Check if this pit contains the given (x, y), for collision detection
  /* (non-Javadoc)
  * @see javax.swing.JComponent#contains(int, int)
  */
 public boolean contains(int x, int y) {
       if ((x < 0) || (x >= Window.ROWS)) {
          return false;
       }
       if ((y < 0) || (y >= Window.COLUMNS - 5)) {
          return false;
       }
       return true;
    }
}
