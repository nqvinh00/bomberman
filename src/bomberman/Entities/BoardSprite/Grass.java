package bomberman.Entities.BoardSprite;

import bomberman.Entities.Entity;
import bomberman.Graphics.Sprite;

public class Grass extends BoardSprite {
    public Grass(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public boolean isCollided(Entity e) {
        return true;
    }
}
