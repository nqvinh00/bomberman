package bomberman.Entities.BoardSprite;

import bomberman.Entities.Character.Bomber;
import bomberman.Entities.Entity;
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
    public boolean isCollided(Entity e) throws IOException {
        if (e instanceof Bomber) {
            if (!this.gameBoard.isNoEnemies()) {
                return false;
            }

            if (e.getX() == this.getBoardSpriteX() && e.getY() == this.getBoardSpriteY()) {
                if (this.gameBoard.isNoEnemies()) {
                    this.gameBoard.nextLevel();
                }
            }
            return true;
        }
        return false;
    }
}
