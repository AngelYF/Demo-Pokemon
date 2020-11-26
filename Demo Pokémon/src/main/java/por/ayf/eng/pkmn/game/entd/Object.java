package por.ayf.eng.pkmn.game.entd;

import java.util.ArrayList;

import por.ayf.eng.pkmn.game.Game;

/**
 *  Class that define a object of the game.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 1.0 - Stable.
 *  @version: 1.1 - Refactor the project.
 */

public class Object extends Element {
	private ArrayList<String> texts;	

	public Object(int x, int y, ArrayList<String> texts) {
		this.setXPosition(x);
		this.setYPosition(y);
		this.setXAbsolutePosition(this.getXPosition() * Game.SIZE);
		this.setYAbsolutePosition(this.getYPosition() * Game.SIZE);
		
		this.texts = texts;
	}

	/**
	 *  Return the texts of the object.
	 * 
	 *  @return Texts of the object.
	 */
	
	public ArrayList<String> getTexts() {
		return texts;
	}
}
