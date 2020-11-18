package bomberman.Entities.Character;

import bomberman.Entities.Entity;
import bomberman.GameBoard;
import bomberman.Graphics.Screen;

import java.io.IOException;

public abstract class Enemy extends Character {

    public Enemy(int x, int y, GameBoard board) {
        super(x, y, board);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {

    }

    @Override
    public boolean isCollided(Entity e) throws IOException {
        return false;
    }

    @Override
    public void dead() {

    }

    @Override
    protected void afterDead() throws IOException {

    }

    @Override
    protected void move(double deltaX, double deltaY) {

    }

    @Override
    protected void moveStep() {

    }

    @Override
    public boolean canMoveTo(double posX, double posY) throws IOException {
        return false;
    }
}
