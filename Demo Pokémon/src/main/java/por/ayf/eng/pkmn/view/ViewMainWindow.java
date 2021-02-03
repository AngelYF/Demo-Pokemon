package por.ayf.eng.pkmn.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import por.ayf.eng.pkmn.game.Game;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *  Window of the game.
 *  
 *  @author: Ángel Yagüe Flor.
 *  @version: 1.0 - Stable.
 *  @version: 1.1 - Refactor the project.
 */

public class ViewMainWindow extends JFrame {
	private static final long serialVersionUID = 1L;						
	
	public final static int WIDTH = Game.SIZE * Game.COL;					
	public final static int HIGH = (Game.SIZE * Game.ROW) + Game.SIZE;		
	
	private JPanel contentPane; 											
	private Game game;													
	
	private int numberPulses = 11;										
	private int numberTimes = 0;										
	
	public ViewMainWindow() {
		initComponents();
	}
	
	private void initComponents() {
		this.setTitle("Demo de Pokémon");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icons/Icon.png")));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(0, 0, WIDTH, HIGH);
		this.setLayout(null); 
		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/* ABOUT THIS PART:
		 * 
		 * Uno de los problemas en esta demo, es la gestión de eventos cuando se presiona una tecla. Como no sé realmente como controlar de una manera apropiada
		 * he optado por una solución bastante chapuza, pero que en un futuro espero corregir. 
		 */
		
		this.addKeyListener(new KeyAdapter() {
			
	        public void keyPressed(KeyEvent evt) {
	        	if(numberPulses == 11 || numberTimes < 2) {
	        		numberPulses = 0;
	        		numberTimes++;
	        		
	        		switch(evt.getKeyCode()) {
		        		case KeyEvent.VK_UP: 				// UP.
		        			game.moveCharacter(1);
		        			break;
		        		case KeyEvent.VK_LEFT: 				// LEFT.
		        			game.moveCharacter(2);
		        			break;
		        		case KeyEvent.VK_RIGHT: 			// RIGHT.
			        		game.moveCharacter(3);
			        		break;
		        		case KeyEvent.VK_DOWN: 				// DOWN.
			        		game.moveCharacter(4);
			        		break;
		        		case KeyEvent.VK_A:
		        			game.dialog();
		        			break;
	        		}
	        	} else {
	        		numberPulses++;
	        	}
	        }
	        
	        public void keyReleased(KeyEvent evt) {
	        	numberPulses = 11;
	        	numberTimes = 0;
	        }
	    });
		
		this.game = new Game();
		contentPane.add(game);
		
		this.game.setFocusable(false); // Necessary because if I click in the screen, don't lose the focus.
		this.setVisible(true);
	}
}
