package bomberman.Entities;

import bomberman.Game;
import bomberman.Graphics.Screen;
import bomberman.Graphics.Sprite;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class Entity {
    protected double x;
    protected double y;
    protected boolean removed = false;
    protected Sprite sprite;
    private boolean process = false;

    /**
     * update entity in game loop.
     * @throws IOException throw io exception
     * @throws LineUnavailableException throw when line audio error
     * @throws UnsupportedAudioFileException throw when audio type not supported
     */
    public abstract void update() throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    /**
     * render entity.
     * @param screen param
     * @throws IOException throw io exception
     */
    public abstract void render(Screen screen) throws IOException;

    /**
     * remove entity.
     */
    public void remove() {
        this.removed = true;
    }

    /**
     * check if entity is removed or not.
     * @return true/false
     */
    public boolean isRemoved() {
        return this.removed;
    }

    /**
     * get sprite of entity.
     * @return sprite
     */
    public Sprite getSprite() {
        return this.sprite;
    }

    /**
     * check if this entity is collided with another entity or not.
     * @param e entity
     * @return true/false
     * @throws IOException throw io exception
     * @throws LineUnavailableException throw when line audio error
     * @throws UnsupportedAudioFileException throw when audio type not supported
     */
    public abstract boolean isCollided(Entity e) throws IOException, UnsupportedAudioFileException,
            LineUnavailableException;

    /**
     * get x pos.
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * get y pos.
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * get real x pos on map (through boardsprite_size).
     * @return real x
     */
    public int getBoardSpriteX() {
        return (int) ((x + sprite.SIZE / 2) / Game.boardsprite_size);
    }

    /**
     * get real y pos on map (through boardsprite_size).
     * @return real y
     */
    public int getBoardSpriteY() {
        return (int) ((y - sprite.SIZE / 2) / Game.boardsprite_size);
    }

    /**
     * set entity is processing in order to avoid other method update entity.
     * @param process true/false
     */
    public void setProcess(boolean process) {
        this.process = process;
    }

    /**
     * check if entity is processed or not.
     * @return true/false
     */
    public boolean isProcessed() {
        return this.process;
    }
}
