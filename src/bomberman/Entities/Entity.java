package bomberman.Entities;

import bomberman.Game;
import bomberman.Graphics.Render;
import bomberman.Graphics.Screen;
import bomberman.Graphics.Sprite;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class Entity implements Render{
    protected double x;
    protected double y;
    protected boolean removed = false;
    protected Sprite sprite;
    private boolean process = false;

    @Override
    public abstract void update() throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    @Override
    public abstract void render(Screen screen) throws IOException;

    public void remove() {
        this.removed = true;
    }

    public boolean isRemoved() {
        return this.removed;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public abstract boolean isCollided(Entity e) throws IOException;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getBoardSpriteX() {
        return (int) ((x + sprite.SIZE / 2) / Game.boardsprite_size);
    }

    public int getBoardSpriteY() {
        return (int) ((y - sprite.SIZE / 2) / Game.boardsprite_size);
    }

    public void setProcess(boolean process) {
        this.process = process;
    }

    public boolean isProcess() {
        return this.process;
    }
}
