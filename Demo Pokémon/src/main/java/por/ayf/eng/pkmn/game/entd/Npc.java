package por.ayf.eng.pkmn.game.entd;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;

import por.ayf.eng.pkmn.game.Game;
import por.ayf.eng.pkmn.util.Util;

/**
 *  Class that define a non played character of the game.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 1.0 - Stable.
 *  @version: 1.1 - Refactor the project.
 */

public class Npc extends Entity {
	private String characterName;		
	private String state;				
	private ArrayList<String> texts;	
	private boolean isStatic;			
	
	public Npc(int xPosition, int yPosition, ArrayList<String> texts, String characterName, String state, boolean isStatic) {
		this.setXPosition(xPosition);
		this.setYPosition(yPosition);
		this.setXAbsolutePosition(this.getXPosition() * Game.SIZE);
		this.setYAbsolutePosition(this.getYPosition() * Game.SIZE);
		
		this.textures = new HashMap<String, BufferedImage>();
		this.texts = texts;
		this.characterName = characterName;
		this.state = state;
		this.isStatic = isStatic;
		
		loadTextures();
	}

	/**
	 *	Method that load the textures of the NPC 	
	 */
	
	protected void loadTextures() {
		try {
			this.textures.put("frente", ImageIO.read(getClass().getResource("/images/textures/characters/others/" + this.characterName + "_frente.png")));
			this.textures.put("izquierda", ImageIO.read(getClass().getResource("/images/textures/characters/others/" + this.characterName + "_izquierda.png")));
			this.textures.put("derecha", ImageIO.read(getClass().getResource("/images/textures/characters/others/" + this.characterName + "_derecha.png")));
			this.textures.put("espalda", ImageIO.read(getClass().getResource("/images/textures/characters/others/" + this.characterName + "_espalda.png")));
		} catch(IOException ex) {
			Util.logMessage(Util.LEVEL_ERROR, "Ha ocurrido un error al leer una textura.", Npc.class, ex);
		}
	}

	/**
	 *  Method that return the name of the character.
	 * 
	 *  @return Name of the NPC.
	 */
	
	public String getCharacterName() {
		return characterName;
	}
	
	/**
	 *  Method that return the texts of the NPC.
	 * 
	 *  @return Texts of the NPC.
	 */
	
	public ArrayList<String> getTexts() {
		return texts;
	}

	/**
	 *  Method that return the state of the NPC.
	 * 
	 *  @return The current state of the NPC.
	 */
	
	public String getState() {
		return state;
	}

	/**
	 *  Method that set a new state for the NPC.
	 * 
	 *  @param state: New state of the NPC.
	 */
	
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 *  Method that return if is static or not.
	 * 
	 *  @return If is static.
	 */
	
	public boolean getIsStatic() {
		return isStatic;
	}
}
