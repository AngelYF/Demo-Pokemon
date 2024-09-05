package por.ayf.eng.pkmn.game.entd;

import por.ayf.eng.pkmn.game.Game;
import por.ayf.eng.pkmn.util.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * Class that define the player of the game.
 *
 * @author: Ángel Yagüe Flor.
 * @version: 1.0 - Stable.
 * @version: 1.1 - Refactor of the project.
 */

public class Player extends Entity {
    private int position;    // Where he sees.

    public Player(int x, int y) {
        this.setPosition(4);
        this.setXPosition(x);
        this.setYPosition(y);
        this.setXAbsolutePosition(this.getXPosition() * Game.SIZE);
        this.setYAbsolutePosition(this.getYPosition() * Game.SIZE);

        this.textures = new HashMap<String, BufferedImage>();

        loadTextures();
    }

    /**
     * Method that load the textures of the player.
     */

    protected void loadTextures() {
        try {
            this.textures.put("frente_1", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_frente_1.png")));
            this.textures.put("frente_2", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_frente_2.png")));
            this.textures.put("frente_3", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_frente_3.png")));
            this.textures.put("frente_4", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_frente_4.png")));

            this.textures.put("espalda_1", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_espalda_1.png")));
            this.textures.put("espalda_2", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_espalda_2.png")));
            this.textures.put("espalda_3", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_espalda_3.png")));
            this.textures.put("espalda_4", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_espalda_4.png")));

            this.textures.put("izquierda_1", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_izquierda_1.png")));
            this.textures.put("izquierda_2", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_izquierda_2.png")));
            this.textures.put("izquierda_3", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_izquierda_3.png")));
            this.textures.put("izquierda_4", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_izquierda_4.png")));

            this.textures.put("derecha_1", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_derecha_1.png")));
            this.textures.put("derecha_2", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_derecha_2.png")));
            this.textures.put("derecha_3", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_derecha_3.png")));
            this.textures.put("derecha_4", ImageIO.read(getClass().getResource("/images/textures/characters/player/Protagonista_derecha_4.png")));
        } catch (IOException ex) {
            Util.logMessage(Util.LEVEL_ERROR, "Ha ocurrido un error al leer una textura.", Player.class, ex);
        }
    }

    /**
     * Method that return the position where the player looks.
     *
     * @return Position where the player looks
     */

    public int getPosition() {
        return position;
    }

    /**
     * Method that set the new position where the player looks.
     *
     * @param position: New position where the player looks.
     */

    public void setPosition(int position) {
        this.position = position;
    }
}
