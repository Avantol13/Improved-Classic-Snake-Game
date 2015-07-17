/*
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * | E-Destroyer McSnake 1325.2 (The Beginning) |
 * |<><><><><><><><><><><><><><><><><><><><><><>|
 * 
 *   []-------A game by Tiger Festival-------[]
 */
package core;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;

// TODO: Auto-generated Javadoc
/**
 * The Class MainMenu.
 */
public class MainMenu extends JInternalFrame  {
	
	/** The action. */
	private final Action action = new SwingAction();

	/**
	 * Create the panel.
	 */
	public MainMenu() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 600, 21);
		add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewGame = new JMenuItem("New Game");
		mntmNewGame.setAction(action);
		mnFile.add(mntmNewGame);
		
		JMenuItem mntmLoadGame = new JMenuItem("Load Game");
		mnFile.add(mntmLoadGame);
		
		JMenu menu = new JMenu("");
		menuBar.add(menu);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

	}
	
	/**
	 * The Class SwingAction.
	 */
	private class SwingAction extends AbstractAction {
		
		/**
		 * Instantiates a new swing action.
		 */
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			
		}
	}
}
