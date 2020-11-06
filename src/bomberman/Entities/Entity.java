package bomberman.Entities;

import bomberman.Game;
import bomberman.Graphics.Render;
import bomberman.Graphics.Screen;
import bomberman.Graphics.Sprite;

public abstract class Entity implements Render{
    protected double x, y;
    protected boolean removed = false;
    protected Sprite sprite;

    @Override
    public abstract void update();

    @Override
    public abstract void render(Screen screen);

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public abstract boolean isCollided(Entity e);

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
}
