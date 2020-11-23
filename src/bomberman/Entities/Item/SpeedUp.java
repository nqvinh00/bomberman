package bomberman.Entities.Item;

import bomberman.Entities.Character.Bomber;
import bomberman.Entities.Entity;
import bomberman.Game;
import bomberman.Graphics.Sprite;

public class SpeedUp extends Item {

    public SpeedUp(int x, int y, Sprite sprite, int level) {
        super(x, y, sprite, level);
    }

    @Override
    public void setValue() {
        this.active = true;
        Game.player_speed += 0.5;
    }

    @Override
    public boolean isCollided(Entity e) {
        if (e instanceof Bomber) {
            ((Bomber) e).addItem(this);
            this.remove();
            return true;
        }
        return false;
    }
}
