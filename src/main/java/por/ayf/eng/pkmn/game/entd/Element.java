package por.ayf.eng.pkmn.game.entd;

/**
 * Class that define a element of the game.
 *
 * @author: Ángel Yagüe Flor.
 * @version: 1.0 - Stable.
 * @version: 1.1 - Refactor the project.
 */

public abstract class Element {
    protected int xPosition;
    protected int yPosition;
    protected int xAbsolutePosition;
    protected int yAbsolutePosition;

    /**
     * Return the X position of the element.
     *
     * @return The X position of the element.
     */

    public int getXPosition() {
        return xPosition;
    }

    /**
     * Set the new X position to the element.
     *
     * @param xPosition: The new X position of the element.
     */

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * Return the Y position of the element.
     *
     * @return The Y position of the element.
     */

    public int getYPosition() {
        return yPosition;
    }

    /**
     * Set the new Y position to the element.
     *
     * @param yPosition: The new Y position of the element.
     */

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * Return the X absolute position of the element.
     *
     * @return The X absolute position of the element.
     */

    public int getXAbsolutePosition() {
        return xAbsolutePosition;
    }

    /**
     * Set the new X absolute position to the element.
     *
     * @param xAbsolutePosition: The new X absolute position of the element.
     */

    public void setXAbsolutePosition(int xAbsolutePosition) {
        this.xAbsolutePosition = xAbsolutePosition;
    }

    /**
     * Return the Y absolute position of the element.
     *
     * @return The Y absolute position of the element.
     */

    public int getYAbsolutePosition() {
        return yAbsolutePosition;
    }

    /**
     * Set the new Y absolute position to the element.
     *
     * @param yAbsolutePosition: The new Y absolute position of the element.
     */

    public void setYAbsolutePosition(int yAbsolutePosition) {
        this.yAbsolutePosition = yAbsolutePosition;
    }
}
