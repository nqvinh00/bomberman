package bomberman.Entities.BoardSprite;

import bomberman.Entities.Entity;
import bomberman.Game;
import bomberman.Graphics.Screen;
import bomberman.Graphics.Sprite;

public abstract class BoardSprite extends Entity {

    public BoardSprite(int x, int y, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    @Override
    public boolean isCollided(Entity e) {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity((int) (this.x * Game.boardsprite_size), (int) (this.y * Game.boardsprite_size), this);
    }
}
