package bomberman.Entities.BoardSprite;

import bomberman.Entities.Entity;
import bomberman.Graphics.Sprite;

public class Grass extends BoardSprite {

    /**
     * constructor.
     * @param x pos
     * @param y pos
     * @param sprite grass
     */
    public Grass(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public boolean isCollided(Entity e) {
        return true;
    }
}
