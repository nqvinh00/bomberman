package bomberman.Entities.BoardSprite;

import bomberman.Entities.Character.Bomber;
import bomberman.Entities.Entity;
import bomberman.GameBoard;
import bomberman.Graphics.Sprite;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class Portal extends BoardSprite {
    protected GameBoard gameBoard;

    /**
     * constructor.
     * @param x pos
     * @param y pos
     * @param sprite portal
     * @param gameBoard of game
     */
    public Portal(int x, int y, Sprite sprite, GameBoard gameBoard) {
        super(x, y, sprite);
        this.gameBoard = gameBoard;
    }

    @Override
    public boolean isCollided(Entity e) {
        return e instanceof Bomber;
    }

    @Override
    public void update() throws IOException, LineUnavailableException {
        if (this.gameBoard.isNoEnemies() && Math.abs(gameBoard.getBomber().getX() - this.x * 16) <= 16 &&
                Math.abs(gameBoard.getBomber().getY() - this.y * 16) <= 16) {
            this.gameBoard.nextLevel();
        }
    }
}
