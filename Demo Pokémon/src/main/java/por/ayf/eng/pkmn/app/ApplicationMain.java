package por.ayf.eng.pkmn.app;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import por.ayf.eng.pkmn.view.ViewMainWindow;

/**
 *  Main class will execute the application.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 1.0 - Stable.
 *  @version: 1.1 - Refactoring of the proyect.
 */

public class ApplicationMain {
	public static void main(String[] args) {
		try {  // This try-catch will change the regular aparence of JFrame of Java.
			
           // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); 				Other type of view.
           // UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 	Other.
			
           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // By default.
           new ViewMainWindow();
        } 
		catch (ClassNotFoundException ex) {
          Logger.getLogger(ViewMainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } 
		catch (InstantiationException ex) {
          Logger.getLogger(ViewMainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } 
		catch (IllegalAccessException ex) {
          Logger.getLogger(ViewMainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } 
		catch (UnsupportedLookAndFeelException ex) {
          Logger.getLogger(ViewMainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}