package bomberman.Entities.Character;

import bomberman.Entities.ActiveEntity;
import bomberman.GameBoard;
import bomberman.Graphics.Screen;

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
    public abstract void update() throws IOException;

    @Override
    public abstract void render(Screen screen) throws IOException;

    public abstract void dead();

    protected abstract void afterDead() throws IOException;

    protected abstract void move(double deltaX, double deltaY) throws IOException;

    protected abstract void moveStep() throws IOException;

    public boolean isAlive() {
        return this.alive;
    }

    public boolean isMoving() {
        return this.moving;
    }

    public abstract boolean canMoveTo(double posX, double posY) throws IOException;

    public int getDirection() {
        return this.direction;
    }
}
