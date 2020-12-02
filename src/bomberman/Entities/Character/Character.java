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

    public Character(int x, int y, GameBoard board) {
        this.x = x;
        this.y = y;
        this.board = board;
    }

    @Override
    public abstract void update() throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    @Override
    public abstract void render(Screen screen) throws IOException;

    public abstract void dead() throws IOException, UnsupportedAudioFileException, LineUnavailableException;

    public abstract void afterDead() throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    protected abstract void move(double deltaX, double deltaY) throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    protected abstract void moveStep() throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    public boolean isAlive() {
        return this.alive;
    }

    public boolean isMoving() {
        return this.moving;
    }

    public abstract boolean canMoveTo(double posX, double posY) throws IOException, UnsupportedAudioFileException, LineUnavailableException;

    public int getDirection() {
        return this.direction;
    }

}
