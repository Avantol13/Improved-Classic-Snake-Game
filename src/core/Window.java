/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package core;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// TODO: Auto-generated Javadoc
/**
 * Creates frame and set its properties.
 * 
 * @author www.gametutorial.net
 */

public class Window extends JFrame{
	   // Define constants for the game
	   /** The Constant ROWS. */
   	public final static int ROWS = 50;      // number of rows (in cells)
	   
   	/** The Constant COLUMNS. */
   	public final static int COLUMNS = 50;   // number of columns (in cells)
	   
   	/** The Constant CELL_SIZE. */
   	public final static int CELL_SIZE = 12; // Size of a cell (in pixels)
	   
   	/** The canvas width. */
   	static int CANVAS_WIDTH  = COLUMNS * CELL_SIZE;  // width and height of the game screen
	   
   	/** The canvas height. */
   	static int CANVAS_HEIGHT = ROWS * CELL_SIZE;
	
   	/** The content pane for the window. Starts with main menu */
   	public static JPanel contentPane = new Framework();
	  
	/**
	 * Sets the canvas width.
	 *
	 * @param width the new canvas width
	 */
	public void setCanvasWidth(int width)
	{
		Window.CANVAS_WIDTH = width;
	}
	
	/**
	 * Sets the canvas height.
	 *
	 * @param height the new canvas height
	 */
	public void setCanvasHeight(int height)
	{
		Window.CANVAS_HEIGHT  = height;
	}
	
	
    /**
     * Instantiates a new window.
     */
    public Window()
    {
        // Sets the title for this frame.
        this.setTitle("E-Destroyer McSnake 1325.2 (The Beginning)");
        
        // Sets size of the frame.
        if(false) // Full screen mode
        {
            // Disables decorations for this frame.
            this.setUndecorated(true);
            // Puts the frame to full screen.
            this.setExtendedState(this.MAXIMIZED_BOTH);
        }
        else // Window mode
        {
        	this.pack();
            // Size of the frame.
            this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
            // Puts frame to center of the screen.
            this.setLocationRelativeTo(null);
            // So that frame cannot be resizable by the user.
            this.setResizable(false);
        }
        
        // Exit the application when user close frame.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        this.setContentPane(contentPane);
                
        //Display the window.
        this.setVisible(true);
    }
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args)
    {
        // Use the event dispatch thread to build the UI for thread-safety.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }
}
