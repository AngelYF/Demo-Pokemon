package por.ayf.eng.pkmn.game.entd;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Class that define the entity of the game.
 *
 * @author: Ángel Yagüe Flor.
 * @version: 1.0 - Stable.
 * @version: 1.1 - Refactor the project.
 */

public abstract class Entity extends Element {
    protected BufferedImage currentTexture;
    protected HashMap<String, BufferedImage> textures;

    /**
     * Method that return the current texture of a entity.
     *
     * @return The current texture of a entity.
     */

    public BufferedImage getImagenActual() {
        return currentTexture;
    }

    /**
     * Method will assign a new current texture of a entity.
     *
     * @param currentTexture: new texture.
     */

    public void setCurrentTexture(BufferedImage currentTexture) {
        this.currentTexture = currentTexture;
    }

    /**
     * Method that return the textures of a entity.
     *
     * @return The textures of a entity.
     */

    public HashMap<String, BufferedImage> getTextures() {
        return textures;
    }

    /**
     * Abstract method for load the textures of a entity.
     */

    protected abstract void loadTextures();
}
