/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package core;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * Create a JPanel on which we draw and listen for keyboard and mouse events.
 * 
 * @author www.gametutorial.net
 */

public abstract class Canvas extends JPanel implements ActionListener, KeyListener, MouseListener {
	
	/** The load button. */
	protected static JButton startButton, optionsButton, exitButton, loadButton; //menu buttons 
	
	/** The color. */
	private Color color;
	
    // Keyboard states - Here are stored states for keyboard keys - is it down or not.
    /** The keyboard state. */
    private static boolean[] keyboardState = new boolean[525];
    
    // Mouse states - Here are stored states for mouse keys - is it down or not.
    /** The mouse state. */
    private static boolean[] mouseState = new boolean[3];
        
    /**
     * Main menu.
     *
     * @param egg the egg
     */
    public static void mainMenu(boolean egg) //enables or disables main menu
    {
    	startButton.setEnabled(egg);
    	startButton.setVisible(egg);
    	/*
    	optionsButton.setEnabled(egg);
    	optionsButton.setVisible(egg);
    	
    	exitButton.setEnabled(egg);
    	exitButton.setVisible(egg);
    	
    	loadButton.setEnabled(egg);
    	loadButton.setVisible(egg);
    	*/
    }
    
    //**
    /**
     * Gets the start button.
     *
     * @return the start button
     */
    public JButton getStartButton()
    {
    	return startButton;
    }
    
    /**
     * Sets the start button.
     *
     * @param button the new start button
     */
    public void setStartButton(JButton button)
    {
    	startButton = button;
    }
    
    
    /**
     * Gets the color.
     *
     * @return the color
     */
    public Color getColor()
    {
    	return color;
    }
    
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
     * Instantiates a new canvas.
     */
    public Canvas()
    {
        // We use double buffer to draw on the screen.
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(color);
        
        // If you will draw your own mouse cursor or if you just want that mouse cursor disappear, 
        // insert "true" into if condition and mouse cursor will be removed.
        if(false)
        {
            BufferedImage blankCursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg, new Point(0, 0), null);
            this.setCursor(blankCursor);
        }
        
        // Adds the keyboard listener to JPanel to receive key events from this component.
        this.addKeyListener(this);
        // Adds the mouse listener to JPanel to receive mouse events from this component.
        this.addMouseListener(this);
        
        //**
        
    }
    
    // This method is overridden in Framework.java and is used for drawing to the screen.
    /**
     * Draw.
     *
     * @param g2d the g2d
     */
    protected abstract void Draw(Graphics2D g2d);//change modifier to private if things go no-no
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;        
        super.paintComponent(g2d);        
        Draw(g2d);
    }
       
    //Buttons
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
    	switch (e.getActionCommand())
    	{
    		case "start" :
    			Framework.setGame(new Game()); //start game
    			mainMenu(false); //disable the main menu
    			break;
    			
    		case "load" :
    			break;
    			
    		case "options" :
    			break;
    			
    		case "exit" :
    			break;
    	}
    }
    
    // Keyboard
    /**
     * Is keyboard key "key" down?.
     *
     * @param key Number of key for which you want to check the state.
     * @return true if the key is down, false if the key is not down.
     */
    public static boolean keyboardKeyState(int key)
    {
        return keyboardState[key];
    }
    
    // Methods of the keyboard listener.
    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(KeyEvent e) 
    {
        keyboardState[e.getKeyCode()] = true;
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        keyboardState[e.getKeyCode()] = false;
        keyReleasedFramework(e);
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    @Override
    public void keyTyped(KeyEvent e) { }
    
    /**
     * Key released framework.
     *
     * @param e the e
     */
    public abstract void keyReleasedFramework(KeyEvent e);
    
    
    // Mouse
    /**
     * Is mouse button "button" down?
     * Parameter "button" can be "MouseEvent.BUTTON1" - Indicates mouse button #1
     * or "MouseEvent.BUTTON2" - Indicates mouse button #2 ...
     * 
     * @param button Number of mouse button for which you want to check the state.
     * @return true if the button is down, false if the button is not down.
     */
    public static boolean mouseButtonState(int button)
    {
        return mouseState[button - 1];
    }
    
    // Sets mouse key status.
    /**
     * Mouse key status.
     *
     * @param e the e
     * @param status the status
     */
    private void mouseKeyStatus(MouseEvent e, boolean status)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
            mouseState[0] = status;
        else if(e.getButton() == MouseEvent.BUTTON2)
            mouseState[1] = status;
        else if(e.getButton() == MouseEvent.BUTTON3)
            mouseState[2] = status;
    }
    
    // Methods of the mouse listener.
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
        mouseKeyStatus(e, true);
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {
        mouseKeyStatus(e, false);
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e) { }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered(MouseEvent e) { }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited(MouseEvent e) { }
    
}
