package por.ayf.eng.pkmn.game.map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import por.ayf.eng.pkmn.game.entd.Element;

/**
 *  Class that define the current map of the game.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 1.0 - Stable.
 *  @version: 1.1 - Refactor the project.
 */

public class Map {
	private int xPositionCamera;			
	private int yPositionCamera;			
	private BufferedImage bufferedMap;			
	private BufferedImage bufferedSubmap;		
	private List<Element> elements;	
	private int[][] mapMatrix;		

	public Map(int xPositionCamera, int yPositionCamera, int[][] mapMatrix) {
		this.xPositionCamera = xPositionCamera;
		this.yPositionCamera = yPositionCamera;
		this.mapMatrix = mapMatrix;
		this.elements = new ArrayList<Element>();
	}
	
	/**
	 *  Method that return the X position of the camera.
	 * 
	 *  @return X position of the camera.
	 */
	
	public int getXPositionCamera() {
		return xPositionCamera;
	}

	/**
	 *  Method that set a new X position to the camera.
	 * 
	 *  @param xPositionCamera: New X position of the camera.
	 */
	
	public void setXPositionCamera(int xPositionCamera) {
		this.xPositionCamera = xPositionCamera;
	}

	/**
	 *  Method that return the Y position of the camera.
	 * 
	 *  @return Y position of the camera.
	 */
	
	public int getYPositionCamera() {
		return yPositionCamera;
	}

	/**
	 *  Method that set a new Y position to the camera.
	 * 
	 *  @param yPositionCamera: New Y position of the camera.
	 */
	
	public void setYPositionCamera(int yPositionCamera) {
		this.yPositionCamera = yPositionCamera;
	}
	
	/**
	 *  Method that return the image of the map.
	 * 
	 *  @return Current image of the map.
	 */
	
	public BufferedImage getBufferedMap() {
		return bufferedMap;
	}

	/**
	 *  Method that set a new image of a map.
	 * 
	 *  @param bufferedMap: New image of the map.
	 */
	
	public void setBufferedMap(BufferedImage bufferedMap) {
		this.bufferedMap = bufferedMap;
	}

	/**
	 *  Method that return the image of the submap.
	 * 
	 *  @return Current image of the submap.
	 */
	
	public BufferedImage getBufferedSubmap() {
		return bufferedSubmap;
	}

	/**
	 *  Method that set a new image of a submap.
	 * 
	 *  @param bufferedSubmap: New image of a submap.
	 */
	
	public void setBufferedSubmap(BufferedImage bufferedSubmap) {
		this.bufferedSubmap = bufferedSubmap;
	}

	/**
	 *  Method that return the elements of the map.
	 * 
	 *  @return Elements of the map.
	 */
	
	public List<Element> getElements() {
		return elements;
	}
	
	/**
	 *  Method that return the matrix of the map.
	 * 
	 *  @return Map of matrix.
	 */
	
	public int[][] getMapMatrix() {
		return mapMatrix;
	}
}
