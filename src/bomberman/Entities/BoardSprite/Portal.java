package bomberman.Entities.BoardSprite;

import bomberman.Entities.Character.Bomber;
import bomberman.Entities.Entity;
import bomberman.Game;
import bomberman.GameBoard;
import bomberman.Graphics.Sprite;

import java.io.IOException;

public class Portal extends BoardSprite {
    protected GameBoard gameBoard;

    public Portal(int x, int y, Sprite sprite, GameBoard gameBoard) {
        super(x, y, sprite);
        this.gameBoard = gameBoard;
    }

    @Override
    public boolean isCollided(Entity e) {
        return e instanceof Bomber;
    }

    @Override
    public void update() throws IOException {
        if (this.gameBoard.isNoEnemies() && Math.abs(gameBoard.getBomber().getX() - this.x * 16) <= 16 &&
                Math.abs(gameBoard.getBomber().getY() - this.y * 16) <= 16) {
            this.gameBoard.nextLevel();
        }
    }
}
