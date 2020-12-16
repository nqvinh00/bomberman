package bomberman.Entities.Character;

import bomberman.Entities.ActiveEntity;
import bomberman.GameBoard;
import bomberman.Graphics.Screen;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class Character extends ActiveEntity {
    protected boolean alive = true;
    protected boolean moving = false;
    protected GameBoard board;
    protected int direction = -1;
    protected int timeDead = 45;

    /**
     * constructor
     * @param x pos
     * @param y pos
     * @param board game
     */
    public Character(int x, int y, GameBoard board) {
        this.x = x;
        this.y = y;
        this.board = board;
    }

    @Override
    public abstract void update() throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    @Override
    public abstract void render(Screen screen) throws IOException;

    /**
     * character dead.
     * @throws IOException throw io exception
     * @throws LineUnavailableException throw when line audio error
     * @throws UnsupportedAudioFileException throw when audio type not supported
     */
    public abstract void dead() throws IOException, UnsupportedAudioFileException, LineUnavailableException;

    /**
     * actions occur after character dead.
     * @throws IOException throw io exception
     * @throws LineUnavailableException throw when line audio error
     * @throws UnsupportedAudioFileException throw when audio type not supported
     */
    public abstract void afterDead() throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    /**
     * create moving animation.
     * @param deltaX pos to move
     * @param deltaY pos to move
     * @throws IOException throw io exception
     * @throws LineUnavailableException throw when line audio error
     * @throws UnsupportedAudioFileException throw when audio type not supported
     */
    protected abstract void move(double deltaX, double deltaY) throws IOException, LineUnavailableException,
            UnsupportedAudioFileException;

    /**
     * calculate move step.
     * @throws IOException throw io exception
     * @throws LineUnavailableException throw when line audio error
     * @throws UnsupportedAudioFileException throw when audio type not supported
     */
    protected abstract void moveStep() throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    /**
     * check if character alive or dead.
     * @return true/false
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * check if character can move to specific position
     * @param posX pos
     * @param posY pos
     * @return true/false
     * @throws IOException throw io exception
     * @throws LineUnavailableException throw when line audio error
     * @throws UnsupportedAudioFileException throw when audio type not supported
     */
    public abstract boolean canMoveTo(double posX, double posY) throws IOException, UnsupportedAudioFileException,
            LineUnavailableException;

    /**
     * get character direction.
     * @return direction
     */
    public int getDirection() {
        return this.direction;
    }

}
