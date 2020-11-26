package por.ayf.eng.pkmn.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

import por.ayf.eng.pkmn.game.audio.Audio;
import por.ayf.eng.pkmn.game.entd.Player;
import por.ayf.eng.pkmn.game.entd.Npc;
import por.ayf.eng.pkmn.game.entd.Object;
import por.ayf.eng.pkmn.game.map.MapM;
import por.ayf.eng.pkmn.game.map.Map;
import por.ayf.eng.pkmn.view.ViewMainWindow;

/**
 *  Canvas where the game happens.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 1.0 - Stable.
 *  @version: 1.1 - Refactoring of the proyect.
 */

public class Game extends Canvas {
	private static final long serialVersionUID = 1L;
	
	public final static int SIZE = 32; 							
	public final static int ROW = 10; 							
	public final static int COL = 15; 							
	private final int xPositionCenter = (COL / 2); 				
	private final int yPositionCenter = ((ROW / 2) - 1); 		
	
	private int xPositionInitial; 									
	private int yPositionInitial; 									
	
	private BufferedImage bufferedGame; 								
	private Graphics2D graphicGame;							
	private BufferedImage bufferedScreen; 							
	private Graphics2D graphicScreen;							
	
	private Map map; 											
	private Player player; 									
	private BufferedImage textBox;								
	private String text1 = ""; 								 
	private String text2 = ""; 								
	private int countDiag = 0;								
	private int indexTexts;								
	private boolean lastText = false;					
	
	private Clip sound; 										
	private Clip music;									
	
	private boolean inMovement = false;						
	private boolean inDialog = false;							
	
	public Game() {
		this.setBounds(0, 0, ViewMainWindow.WIDTH, ViewMainWindow.HIGH);
		
		this.map = new Map(10, 9, MapM.matrixLittlerootTown);
		loadElements(); 								
		loadMap("LittlerootTownMap.png");						
		
		this.xPositionInitial = this.map.getXPositionCamera() + xPositionCenter;
		this.yPositionInitial = this.map.getYPositionCamera() + yPositionCenter;
		
		this.player = new Player(xPositionInitial, yPositionInitial);
		this.player.setCurrentTexture(this.player.getTextures().get("frente_1"));
		
		loadResources();
		this.repaint();
		
		Audio.playBackgroundMusic(music, "LittlerootTown.wav", 55000, -1);
	}
	
	/**
	 *  Method that paint the game.
	 *  
	 *  @param graphics: Graphics will paint.
	 */
	
	public void paint(Graphics graphics) {
		update(graphics); 
	}
	
	/**
	 *  Method that call to paint for avoid black screens.
	 *  
	 *  @param graphics: Graphics will draw.
	 */
	
	public void update(Graphics graphics) {	
		createBuffers();
		
		if(this.inMovement) {
			drawMovement(graphics);
		}
		else {
			drawScreen(graphics);
		}
	}
	
	/**
	 *  Method will draw the screen of the game.
	 * 
	 *  @param graphics: Graphics will draw the game.
	 */
	
	private void drawScreen(Graphics graphics) {
		drawMap(this.graphicGame);
		drawCharacters(this.graphicGame);
		drawSubmap(this.graphicScreen);
		
		if(this.inDialog) {
			drawTextBox(this.graphicScreen);
			drawText(graphicScreen);
		}

		graphics.drawImage(this.bufferedScreen, 0, 0, null);
	}
	
	/** 
	 *  Method will draw the map of the game.
	 *  
	 *  @param graphics: Graphics will draw the map.
	 */
	
	private void drawMap(Graphics2D graphics) {
		graphics.drawImage(this.map.getBufferedMap(), 0, 0, null);
	}
	
	/**
	 *  Method will do a clipping on the image of the game for after put it in the screen.
	 * 
	 *  @param graphics: Graphics will draw the submap.
	 */
	
	private void drawSubmap(Graphics2D graphics) {
		if(this.inMovement == false) {
			this.map.setBufferedSubmap(this.bufferedGame.getSubimage(this.map.getXPositionCamera() * SIZE, (this.map.getYPositionCamera() * SIZE) + (SIZE / 2) - 10, ViewMainWindow.WIDTH, ViewMainWindow.HIGH));
		}
		
		graphics.drawImage(this.map.getBufferedSubmap(), 0, 0, null);
	}
	
	/**
	 *  Method that draw the player in the game.
	 * 
	 *  @param graphics: Graphics will draw the player.
	 */
	
	private void drawPlayer(Graphics2D graphics) {
		graphics.drawImage(this.player.getImagenActual(), this.player.getXAbsolutePosition(), this.player.getYAbsolutePosition(), null);
	}
	
	/**
	 *  Method will draw the characters that there are in the game.
	 * 
	 *  @param graphics: Graphics will draw the characters.
	 */
	
	private void drawCharacters(Graphics2D graphics) {
		boolean drawed = false;
		
		for(int i = 0; i < this.map.getElements().size(); i++) {
			if(this.map.getElements().get(i) instanceof Npc) {
				
				Npc npc = (Npc) this.map.getElements().get(i);
				
				if(npc.getYPosition() < this.player.getYPosition()) {  
					graphics.drawImage(npc.getTextures().get(npc.getState()), npc.getXAbsolutePosition(), npc.getYAbsolutePosition(), null);
				}
				else { 
					if(drawed == false) {
						drawed = true;
						drawPlayer(graphics);
					}
					
					graphics.drawImage(npc.getTextures().get(npc.getState()), npc.getXAbsolutePosition(), npc.getYAbsolutePosition(), null);
				}
			}
		}
		
		if(drawed == false) {
			drawed = true;
			drawPlayer(graphics);
		}
	}
	
	/**
	 *  Method will draw the text box on the screen.
	 * 
	 *  @param graphics: Graphics for draw on the screen.
	 */
	
	private void drawTextBox(Graphics2D graphics) {
		graphics.drawImage(this.textBox, 0, (SIZE * (ROW - 3)) + 10, null);
	}
	
	/** 
	 *  Method will draw the text on the screen.
	 * 
	 *  @param graphics: Graphics for draw on the screen.
	 */
	
	private void drawText(Graphics2D graphics) {
		graphics.setFont(new Font("Power Clear", Font.TRUETYPE_FONT, 20));
		graphics.drawString(this.text1, 25, (SIZE * (ROW - 3)) + 50);
		this.text1 = "";
		
		graphics.setFont(new Font("Power Clear", Font.TRUETYPE_FONT, 20));
		graphics.drawString(this.text2, 25, (SIZE * (ROW - 2)) + 45);
		this.text2 = "";
		
		if(this.inDialog == true && this.lastText == false) {
			 int[] xCoords = new int[4];
			 int[] yCoords = new int[4];
			 
			 xCoords[0] = (SIZE * (COL - 2)) + 10;
			 yCoords[0] = (SIZE * (ROW - 2)) + 40;
			 xCoords[1] = (SIZE * (COL - 2)) + 5;
			 yCoords[1] = (SIZE * (ROW - 2)) + 45;
			 xCoords[2] = (SIZE * (COL - 2)) + 15;
			 yCoords[2] = (SIZE * (ROW - 2)) + 45;
			 
			 graphics.setColor(Color.BLACK);
			 graphics.fillPolygon(xCoords,yCoords,3);
		}
	}
	
	/**
	 *  M�todo que cambiar� las posiciones del jugador seg�n al sitio donde se mueva.
	 * 
	 * 	@param graphics para pintar en el canvas.
	 */
	
	private void drawMovement(Graphics graphics) {
		switch(this.player.getPosition()) {
		
			case 1:	// UP
				
				if(this.map.getMapMatrix()[this.player.getYPosition() - 1][this.player.getXPosition()] == 1 ||
				   this.map.getMapMatrix()[this.player.getYPosition() - 1][this.player.getXPosition()] == 2 ||
				   this.map.getMapMatrix()[this.player.getYPosition() - 1][this.player.getXPosition()] == 3) {
					
					this.player.setCurrentTexture(this.player.getTextures().get("espalda_1"));
					drawScreen(graphics);
					Audio.playSound(this.sound, "Collision.wav");
					
					try {
						Thread.sleep(500);
					} 
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					for(int i = 1; i <= 4; i++) {
						
						this.map.setBufferedSubmap(this.bufferedGame.getSubimage(this.map.getXPositionCamera() * SIZE, (this.map.getYPositionCamera() * SIZE) + (SIZE / 2) - 10 - (i * 8), ViewMainWindow.WIDTH, ViewMainWindow.HIGH));
						
						switch(i) {
							case 1:
								this.player.setCurrentTexture(this.player.getTextures().get("espalda_2"));
								break;
							case 2:
								this.player.setCurrentTexture(this.player.getTextures().get("espalda_3"));
								break;
							case 3:
								this.player.setCurrentTexture(this.player.getTextures().get("espalda_4"));
								break;
							case 4:
								this.player.setCurrentTexture(this.player.getTextures().get("espalda_1"));
								break;
						}
						
						this.player.setYAbsolutePosition(this.player.getYAbsolutePosition() - 8);
						
						drawScreen(graphics);
						
						try {
							Thread.sleep(100);
						} 
						catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					this.player.setYPosition(this.player.getYPosition() - 1);
					this.player.setYAbsolutePosition(this.player.getYPosition() * SIZE);
					this.map.setYPositionCamera(this.map.getYPositionCamera() - 1);
				}
				break;
				
			case 2: // LEFT
				
				if(this.map.getMapMatrix()[this.player.getYPosition()][this.player.getXPosition() - 1] == 1 ||
				   this.map.getMapMatrix()[this.player.getYPosition()][this.player.getXPosition() - 1] == 2 ||
				   this.map.getMapMatrix()[this.player.getYPosition()][this.player.getXPosition() - 1] == 3) {
					
					this.player.setCurrentTexture(this.player.getTextures().get("izquierda_1"));
					drawScreen(graphics);
					Audio.playSound(this.sound, "Colision.wav");
					
					try {
						Thread.sleep(500);
					} 
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					for(int i = 1; i <= 4; i++) {
						
						this.map.setBufferedSubmap(this.bufferedGame.getSubimage(this.map.getXPositionCamera() * SIZE - (i * 8), (this.map.getYPositionCamera() * SIZE) + (SIZE / 2) - 10, ViewMainWindow.WIDTH, ViewMainWindow.HIGH));
						
						switch(i) {
							case 1:
								this.player.setCurrentTexture(this.player.getTextures().get("izquierda_2"));
								break;
							case 2:
								this.player.setCurrentTexture(this.player.getTextures().get("izquierda_3"));
								break;
							case 3:
								this.player.setCurrentTexture(this.player.getTextures().get("izquierda_4"));
								break;
							case 4:
								this.player.setCurrentTexture(this.player.getTextures().get("izquierda_1"));
								break;
						}
						
						this.player.setXAbsolutePosition(this.player.getXAbsolutePosition() - 8);
						
						drawScreen(graphics);
						
						try {
							Thread.sleep(100);
						} 
						catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					this.player.setXPosition(this.player.getXPosition() - 1);
					this.player.setXAbsolutePosition(this.player.getXPosition() * SIZE);
					this.map.setXPositionCamera(this.map.getXPositionCamera() - 1);
				}
				break;
				
			case 3: // RIGHT
				
				if(this.map.getMapMatrix()[this.player.getYPosition()][this.player.getXPosition() + 1] == 1 || 
				   this.map.getMapMatrix()[this.player.getYPosition()][this.player.getXPosition() + 1] == 2 ||
				   this.map.getMapMatrix()[this.player.getYPosition()][this.player.getXPosition() + 1] == 3) {
					
					this.player.setCurrentTexture(this.player.getTextures().get("derecha_1"));
					drawScreen(graphics);
					Audio.playSound(this.sound, "Colision.wav");
					
					try {
						Thread.sleep(500);
					} 
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					for(int i = 1; i <= 4; i++) {
						
						this.map.setBufferedSubmap(this.bufferedGame.getSubimage(this.map.getXPositionCamera() * SIZE + (i * 8), (this.map.getYPositionCamera() * SIZE) + (SIZE / 2) - 10, ViewMainWindow.WIDTH, ViewMainWindow.HIGH));
						
						switch(i) {
							case 1:
								this.player.setCurrentTexture(this.player.getTextures().get("derecha_2"));
								break;
							case 2:
								this.player.setCurrentTexture(this.player.getTextures().get("derecha_3"));
								break;
							case 3:
								this.player.setCurrentTexture(this.player.getTextures().get("derecha_4"));
								break;
							case 4:
								this.player.setCurrentTexture(this.player.getTextures().get("derecha_1"));
								break;
						}
						
						this.player.setXAbsolutePosition(this.player.getXAbsolutePosition() + 8);
						
						drawScreen(graphics);
						
						try {
							Thread.sleep(100);
						} 
						catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					this.player.setXPosition(this.player.getXPosition() + 1);
					this.player.setXAbsolutePosition(this.player.getXPosition() * SIZE);
					this.map.setXPositionCamera(this.map.getXPositionCamera() + 1);
				}
				break;
				
			case 4: // DOWN
				
				if(this.map.getMapMatrix()[this.player.getYPosition() + 1][this.player.getXPosition()] == 1 || 
				   this.map.getMapMatrix()[this.player.getYPosition() + 1][this.player.getXPosition()] == 2 || 
				   this.map.getMapMatrix()[this.player.getYPosition() + 1][this.player.getXPosition()] == 3) {
					
					this.player.setCurrentTexture(this.player.getTextures().get("frente_1"));
					drawScreen(graphics);
					Audio.playSound(this.sound, "Colision.wav");
					
					try {
						Thread.sleep(500);
					} 
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					for(int i = 1; i <= 4; i++) {
						
						this.map.setBufferedSubmap(this.bufferedGame.getSubimage(this.map.getXPositionCamera() * SIZE, (this.map.getYPositionCamera() * SIZE) + (SIZE / 2) - 10 + (i * 8), ViewMainWindow.WIDTH, ViewMainWindow.HIGH));
						
						switch(i) {
							case 1:
								this.player.setCurrentTexture(this.player.getTextures().get("frente_2"));
								break;
							case 2:
								this.player.setCurrentTexture(this.player.getTextures().get("frente_3"));
								break;
							case 3:
								this.player.setCurrentTexture(this.player.getTextures().get("frente_4"));
								break;
							case 4:
								this.player.setCurrentTexture(this.player.getTextures().get("frente_1"));
								break;
						}
						
						this.player.setYAbsolutePosition(this.player.getYAbsolutePosition() + 8);
						
						drawScreen(graphics);
						
						try {
							Thread.sleep(100);
						} 
						catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					this.player.setYPosition(this.player.getYPosition() + 1);
					this.player.setYAbsolutePosition(this.player.getYPosition() * SIZE);
					this.map.setYPositionCamera(this.map.getYPositionCamera() + 1);
				}
				break;
		}
		
		this.inMovement = false;
	}
	
	/**
	 *  Method for move a character to the player.
	 * 
	 *  @param direction: Direction where the character moves.
	 */
	
	public void moveCharacter(int direction) {
		
		if(this.inDialog == true) { // If there is a dialog, you can't move.
			return;
		}
		
		this.player.setPosition(direction); 	
		this.inMovement = true; 				
		this.repaint();							
	}
	
	/**
	 *  Method that manage how the dialog interact in the game.
	 */
	
	public void dialog() {
		if(this.inDialog == false) { 
			
			switch(this.player.getPosition()) {
				case 1: // UP
					if(MapM.matrixLittlerootTown[this.player.getYPosition() - 1][this.player.getXPosition()] == 2 || MapM.matrixLittlerootTown[this.player.getYPosition() - 1][this.player.getXPosition()] == 3) {
						this.inDialog = true;
						
						for(int i = 0; i < this.map.getElements().size(); i++) {
							if(this.map.getElements().get(i) instanceof Npc) {
								
								Npc npc = (Npc) this.map.getElements().get(i);
								
								if(this.player.getYPosition() - 1 == npc.getYPosition() && this.player.getXPosition() == npc.getXPosition()) { 
									if(npc.getIsStatic() == false) { 
										npc.setState("frente");
									}	
									
									for(int j = 0; j < npc.getTexts().size(); j++) {
										if(this.countDiag == 2) { 
											this.indexTexts = j;
											this.countDiag = 0;
											break;
										}
										else {
											if(this.countDiag == 0) { 
												this.text1 = npc.getTexts().get(j);
												this.countDiag++;
											}
											else if(this.countDiag == 1) {
												this.text2 = npc.getTexts().get(j);
												this.countDiag++;
											}
										}
									}
									
									if(npc.getTexts().size() <= 2) {
										this.lastText = true;
									}
									
									break; 
								}
							}
							else if(this.map.getElements().get(i) instanceof Object) {
								Object obj = (Object) this.map.getElements().get(i);
								
								if(this.player.getYPosition() - 1 == obj.getYPosition() && this.player.getXPosition() == obj.getXPosition()) {
									
									for(int j = 0; j < obj.getTexts().size(); j++) {
										if(this.countDiag == 2) {
											this.indexTexts = j;
											this.countDiag = 0;
											break;
										}
										else {
											if(this.countDiag == 0) {
												this.text1 = obj.getTexts().get(j);
												this.countDiag++;
											}
											else if(this.countDiag == 1) {
												this.text2 = obj.getTexts().get(j);
												this.countDiag++;
											}
										}
									}
									
									if(obj.getTexts().size() <= 2) {
										this.lastText = true;
									}
									break;
								}
							}
						}
						
						Audio.playSound(sound, "Choose.wav");
					}
					break;
				case 2: // LEFT
					if(MapM.matrixLittlerootTown[this.player.getYPosition()][this.player.getXPosition() - 1] == 3) {
						this.inDialog = true;
						
						for(int i = 0; i < this.map.getElements().size(); i++) {
							if(this.map.getElements().get(i) instanceof Npc) {
								
								Npc npc = (Npc) this.map.getElements().get(i);
								
								if(this.player.getYPosition() == npc.getYPosition() && this.player.getXPosition() - 1 == npc.getXPosition()) {
									if(npc.getIsStatic() == false) {
										npc.setState("derecha");
									}
									
									for(int j = 0; j < npc.getTexts().size(); j++) {
										if(this.countDiag == 2) {
											this.indexTexts = j;
											this.countDiag = 0;
											break;
										}
										else {
											if(this.countDiag == 0) {
												this.text1 = npc.getTexts().get(j);
												this.countDiag++;
											}
											else if(this.countDiag == 1) {
												this.text2 = npc.getTexts().get(j);
												this.countDiag++;
											}
										}
									}

									if(npc.getTexts().size() <= 2) {
										this.lastText = true;
									}
									break;
								}
							}
						}
						
						Audio.playSound(sound, "Choose.wav");
					}
					else if(MapM.matrixLittlerootTown[this.player.getYPosition()][this.player.getXPosition() - 1] == 2) {
						this.inDialog = true;
						this.lastText = true;
						Audio.playSound(sound, "Choose.wav");
						this.text1 = "No se puede leer desde esta posici�n.";
						this.text2 = "";
					}
					break;
				case 3: // RIGHT
					if(MapM.matrixLittlerootTown[this.player.getYPosition()][this.player.getXPosition() + 1] == 3) {
						this.inDialog = true;
						
						for(int i = 0; i < this.map.getElements().size(); i++) {
							if(this.map.getElements().get(i) instanceof Npc) {
								
								Npc npc = (Npc) this.map.getElements().get(i);
								
								if(this.player.getYPosition() == npc.getYPosition() && this.player.getXPosition() + 1 == npc.getXPosition()) {
									if(npc.getIsStatic() == false) {
										npc.setState("izquierda");
									}
									
									for(int j = 0; j < npc.getTexts().size(); j++) {
										if(this.countDiag == 2) {
											this.indexTexts = j;
											this.countDiag = 0;
											break;
										}
										else {
											if(this.countDiag == 0) {
												this.text1 = npc.getTexts().get(j);
												this.countDiag++;
											}
											else if(this.countDiag == 1) {
												this.text2 = npc.getTexts().get(j);
												this.countDiag++;
											}
										}
									}

									if(npc.getTexts().size() <= 2) {
										this.lastText = true;
									}
									break;
								}
							}
						}
						
						Audio.playSound(sound, "Choose.wav");
					}
					else if(MapM.matrixLittlerootTown[this.player.getYPosition()][this.player.getXPosition() + 1] == 2) {
						this.inDialog = true;
						this.lastText = true;
						Audio.playSound(sound, "Choose.wav");
						this.text1 = "No se puede leer desde esta posici�n.";
						this.text2 = "";
					}
					break;
				case 4: // DOWN
					if(MapM.matrixLittlerootTown[this.player.getYPosition() + 1][this.player.getXPosition()] == 3) {
						this.inDialog = true;
						
						for(int i = 0; i < this.map.getElements().size(); i++) {
							if(this.map.getElements().get(i) instanceof Npc) {
							
								Npc npc = (Npc) this.map.getElements().get(i);
								
								if(this.player.getYPosition() + 1 == npc.getYPosition() && this.player.getXPosition() == npc.getXPosition()) {
									if(npc.getIsStatic() == false) {
										npc.setState("espalda");
									}
									
									for(int j = 0; j < npc.getTexts().size(); j++) {
										if(this.countDiag == 2) {
											this.indexTexts = j;
											this.countDiag = 0;
											break;
										}
										else {
											if(this.countDiag == 0) {
												this.text1 = npc.getTexts().get(j);
												this.countDiag++;
											}
											else if(this.countDiag == 1) {
												this.text2 = npc.getTexts().get(j);
												this.countDiag++;
											}
										}
									}

									if(npc.getTexts().size() <= 2) {
										this.lastText = true;
									}
									break;
								}
							}
						}
						
						Audio.playSound(sound, "Choose.wav");
					}
					else if(MapM.matrixLittlerootTown[this.player.getYPosition() + 1][this.player.getXPosition()] == 2) {
						this.inDialog = true;
						this.lastText = true;
						this.countDiag = 0;
						this.indexTexts = 0;
						Audio.playSound(sound, "Choose.wav");
						this.text1 = "No se puede leer desde esta posición.";
						this.text2 = "";
					}
					break;
			}
		}
		else { 
			if(this.lastText == false) {
				
				switch(this.player.getPosition()) {
					case 1:	// UP
						if(MapM.matrixLittlerootTown[this.player.getYPosition() - 1][this.player.getXPosition()] == 2 || MapM.matrixLittlerootTown[this.player.getYPosition() - 1][this.player.getXPosition()] == 3) {
							
							for(int i = 0; i < this.map.getElements().size(); i++) {
								if(this.map.getElements().get(i) instanceof Npc) {
									
									Npc npc = (Npc) this.map.getElements().get(i);
									
									if(this.player.getYPosition() - 1 == npc.getYPosition() && this.player.getXPosition() == npc.getXPosition()) {	
						
										for(int j = this.indexTexts; j < npc.getTexts().size(); j++) {
											if(this.countDiag == 2) {
												this.indexTexts = j;
												this.countDiag = 0;
												break;
											}
											else {
												if(this.countDiag == 0) {
													this.text1 = npc.getTexts().get(j);
													this.countDiag++;
												}
												else if(this.countDiag == 1) {
													this.text2 = npc.getTexts().get(j);
													this.countDiag++;
												}
											}
											
											if(j == npc.getTexts().size() - 1) {
												this.lastText = true;
											}
										}
										
										Audio.playSound(sound, "Choose.wav");
										break;
									}
								}
								else if(this.map.getElements().get(i) instanceof Object) {
									Object obj = (Object) this.map.getElements().get(i);
									
									if(this.player.getYPosition() - 1 == obj.getYPosition() && this.player.getXPosition() == obj.getXPosition()) {
										for(int j = this.indexTexts; j < obj.getTexts().size(); j++) {
											if(this.countDiag == 2) {
												this.indexTexts = j;
												this.countDiag = 0;
												break;
											}
											else {
												if(this.countDiag == 0) {
													this.text1 = obj.getTexts().get(j);
													this.countDiag++;
												}
												else if(this.countDiag == 1) {
													this.text2 = obj.getTexts().get(j);
													this.countDiag++;
												}
											}
											
											if(j == obj.getTexts().size() - 1) {
												this.lastText = true;
											}
										}
	
										Audio.playSound(sound, "Choose.wav");
										break;
									}
								}
							}
						}
						break;
					case 2: // LEFT
						if(MapM.matrixLittlerootTown[this.player.getYPosition()][this.player.getXPosition() - 1] == 3) {
							
							for(int i = 0; i < this.map.getElements().size(); i++) {
								if(this.map.getElements().get(i) instanceof Npc) {
									
									Npc npc = (Npc) this.map.getElements().get(i);
									
									if(this.player.getYPosition() == npc.getYPosition() && this.player.getXPosition() - 1 == npc.getXPosition()) {	
						
										for(int j = this.indexTexts; j < npc.getTexts().size(); j++) {
											if(this.countDiag == 2) {
												this.indexTexts = j;
												this.countDiag = 0;
												break;
											}
											else {
												if(this.countDiag == 0) {
													this.text1 = npc.getTexts().get(j);
													this.countDiag++;
												}
												else if(this.countDiag == 1) {
													this.text2 = npc.getTexts().get(j);
													this.countDiag++;
												}
											}
											
											if(j == npc.getTexts().size() - 1) {
												this.lastText = true;
											}
										}
										
										Audio.playSound(sound, "Choose.wav");
										break;
									}
								}
							}
							
						}
						break;
					case 3: // RIGHT
						if(MapM.matrixLittlerootTown[this.player.getYPosition()][this.player.getXPosition() + 1] == 3) {
							
							for(int i = 0; i < this.map.getElements().size(); i++) {
								if(this.map.getElements().get(i) instanceof Npc) {
									
									Npc npc = (Npc) this.map.getElements().get(i);
									
									if(this.player.getYPosition() == npc.getYPosition() && this.player.getXPosition() + 1 == npc.getXPosition()) {	
						
										for(int j = this.indexTexts; j < npc.getTexts().size(); j++) {
											if(this.countDiag == 2) {
												this.indexTexts = j;
												this.countDiag = 0;
												break;
											}
											else {
												if(this.countDiag == 0) {
													this.text1 = npc.getTexts().get(j);
													this.countDiag++;
												}
												else if(this.countDiag == 1) {
													this.text2 = npc.getTexts().get(j);
													this.countDiag++;
												}
											}
											
											if(j == npc.getTexts().size() - 1) {
												this.lastText = true;
											}
										}
										
										Audio.playSound(sound, "Choose.wav");
										break;
									}
								}
							}
							
						}
						break;
					case 4: // DOWN
						if(MapM.matrixLittlerootTown[this.player.getYPosition() + 1][this.player.getXPosition()] == 3) {
							
							for(int i = 0; i < this.map.getElements().size(); i++) {
								if(this.map.getElements().get(i) instanceof Npc) {
									
									Npc npc = (Npc) this.map.getElements().get(i);
									
									if(this.player.getYPosition() + 1 == npc.getYPosition() && this.player.getXPosition() == npc.getXPosition()) {	
						
										for(int j = this.indexTexts; j < npc.getTexts().size(); j++) {
											if(this.countDiag == 2) {
												this.indexTexts = j;
												this.countDiag = 0;
												break;
											}
											else {
												if(this.countDiag == 0) {
													this.text1 = npc.getTexts().get(j);
													this.countDiag++;
												}
												else if(this.countDiag == 1) {
													this.text2 = npc.getTexts().get(j);
													this.countDiag++;
												}
											}
											
											if(j == npc.getTexts().size() - 1) {
												this.lastText = true;
											}
										}
										
										Audio.playSound(sound, "Choose.wav");
										break;
									}
								}
							}
							
						}
						break;
				}
			}
			else {
				this.inDialog = false;
				this.countDiag = 0;
				this.indexTexts = 0;
				this.lastText = false;
			}
		}
		
		this.repaint();
	}
	
	/**
	 *  Method that load the map of the game.
	 * 
	 *  @param name: Name of the file of the map.
	 */
	
	private void loadMap(String name) {
		try {
			this.map.setBufferedMap(ImageIO.read(new File("src/main/resources/images/textures/map/" + name)));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 *  Method that load the resources of the game.
	 */
	
	private void loadResources() {
		try {
			this.textBox = ImageIO.read(new File("src/main/resources/images/textures/miscellaneous/Textbox.png"));
			
			// Install the font.
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/pkmndp.ttf")));
		}
		catch(IOException e) {
			e.printStackTrace();
		} 
		catch (FontFormatException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 *  Method that load the elements that can interact with the game. 
	 */
	
	private void loadElements() {
		ArrayList<String> aux;
		
		this.map.getElements().add(new Npc(17, 2, null, "TRocket", "derecha", false));
		this.map.getElements().add(new Npc(18, 2, null, "Policia", "izquierda", false));
		
		aux = new ArrayList<String>();
		aux.add("Policia:  No puedes pasar por aqu. El acceso ha sido");
		aux.add("restringido.");
		aux.add("¿Por qué no te das una vuelta por el pueblo?");
		
		this.map.getElements().add(new Npc(17, 4, aux, "Policia", "frente", false));
		
		aux = new ArrayList<String>();
		aux.add("Policia:  Estamos deteniendo a un malhechor que se");
		aux.add("las ha dado de listo, por lo que ahora mismo no");
		aux.add("puedes pasar por aquí. Lo siento.");
		
		this.map.getElements().add(new Npc(18, 4, aux, "Policia", "frente", false));
		
		aux = new ArrayList<String>();
		aux.add("Red:  ...");
		
		this.map.getElements().add(new Npc(26, 7, aux, "Red", "espalda", true));
		
		aux = new ArrayList<String>();
		aux.add("Gary:  Mírale, ya se las da otra vez de importante.");
		aux.add("¿No podría ser sociable por una vez al menos?");
		aux.add("Siempre hay que estar detrás de él, y nunca me");
		aux.add("pregunta que tal todo, ni nada...");
		
		this.map.getElements().add(new Npc(26, 11, aux, "Gary", "espalda", false));
		
		aux = new ArrayList<String>();
		aux.add("Prof.Oak:  Bienvenido a la demo técnica de Pokémon.");
		aux.add("");
		aux.add("En esta demo se pretende simular el comportamiento");
		aux.add("a la hora de moverse por un mapa de Pokémon.");
		aux.add("Por lo visto, están deteniendo a un miembro del Team");
		aux.add("Rocket más adelante. Por lo que tus acciones");
		aux.add("están limitadas dentro de este pueblo. No obstante,");
		aux.add("espero que disfrutes de tu estancia por aquí.");
		
		this.map.getElements().add(new Npc(17, 11, aux, "Oak", "frente", false));
		
		aux = new ArrayList<String>();
		aux.add("Jan:  ¡Ah! ¡Buenas tío! ¿Qué tal?");
		aux.add("");
		aux.add("Tío, acabo de pintar la cosa más fantabulosa que te");
		aux.add("puedas echar a la cara.");
		aux.add("La he publicado en mi galería, pero las muy perras");
		aux.add("de ahí adelante no nos dejaran salir del pueblo.");
		aux.add("¡Tenemos que quedar para ir joder! En serio tío,");
		aux.add("es lo mejor que verás en décadas ñiajaja.");
		
		this.map.getElements().add(new Npc(21, 12, aux, "Jan", "frente", false));
		
		aux = new ArrayList<String>();
		aux.add("Álvaro:  Ángel, estoy hecho de oro en el Animal");
		aux.add("Crossing, ¿cuándo nos tomamos algo pisha?");
		aux.add("Yo invito. ¡Qué soy ricooooooooo!");
		aux.add("");
		aux.add("Ángel:  ¡Pero si es un juego Álvaro! Ese oro no");
		aux.add("existe.");
		aux.add("Álvaro:  Eres un rayao de la vida. Este oro existe");
		aux.add("porque lo digo yo.");
		aux.add("¡A la próxima, me vas a comer tol coño!");

		this.map.getElements().add(new Npc(23, 16, aux, "Alvaro", "frente", false));
		
		aux = new ArrayList<String>();
		aux.add("Endika:  Tío, a ver si el profesor este abre de una");
		aux.add("vez el puto laboratorio este y me da a mi Charizard.");
		aux.add("Toda la puta mañana esperando. Llevo ya 5 monsters");
		aux.add("y creo que todavía caen otros 5...");
		aux.add("Ángel:  Endika, no es un Charizard, es un Charma...");
		aux.add("");
		aux.add("Endika:  ¡TE QUIEREEEES CALLAR! ¡AMIGO! ¡POR FAVOR!");
		aux.add("¡POR FAVOR! ¡AMIGO!");

		this.map.getElements().add(new Npc(16, 20, aux, "Endika", "frente", false));
		
		aux = new ArrayList<String>();
		aux.add("Casa de Ángel.");
		
		this.map.getElements().add(new Object(14, 11, aux));
		
		aux = new ArrayList<String>();
		aux.add("Prefiero dar una vuelta...");
		
		this.map.getElements().add(new Object(12, 11, aux));
		
		aux = new ArrayList<String>();
		aux.add("Casa de Jan.");
		
		this.map.getElements().add(new Object(19, 11, aux));
		
		aux = new ArrayList<String>();
		aux.add("Villa Raíz:  Un pueblo de una tonalidad nada habitual.");
		aux.add("");
		
		this.map.getElements().add(new Object(22, 16, aux));
		
		aux = new ArrayList<String>();
		aux.add("Laboratorio del Prof.Oak.");
		
		this.map.getElements().add(new Object(13, 20, aux));
		
		aux = new ArrayList<String>();
		aux.add("Prof.Oak:  ¡Aléjate de ahí insensato!");
		aux.add("");
		aux.add("¡Todavía tienes 9 años! Vuelve cuando hayas");
		aux.add("crecido.");
		
		this.map.getElements().add(new Object(14, 19, aux));
	}
	
	/** 
	 *  Method that create the buffers for paint.
	 */
	
	private void createBuffers() {
		if(this.bufferedScreen == null) {
			this.bufferedScreen = (BufferedImage) createImage(ViewMainWindow.WIDTH, ViewMainWindow.HIGH);
			this.graphicScreen = (Graphics2D) this.bufferedScreen.getGraphics();
		}
		
		if(this.bufferedGame == null) {
			this.bufferedGame = (BufferedImage) createImage(this.map.getBufferedMap().getWidth(), this.map.getBufferedMap().getHeight());
			this.graphicGame = (Graphics2D) this.bufferedGame.getGraphics();
		}
	}
	
	
}
