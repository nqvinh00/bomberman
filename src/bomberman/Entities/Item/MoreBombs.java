package bomberman.Entities.Item;

import bomberman.Entities.Character.Bomber;
import bomberman.Entities.Entity;
import bomberman.Game;
import bomberman.Graphics.Sprite;

public class MoreBombs extends Item {

    public MoreBombs(int x, int y, Sprite sprite, int level) {
        super(x, y, sprite, level);
    }

    @Override
    public void setValue() {
        this.active = true;
        Game.bomb_number += 1;
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
