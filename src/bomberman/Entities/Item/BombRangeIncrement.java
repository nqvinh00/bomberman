package bomberman.Entities.Item;

import bomberman.Game;
import bomberman.Graphics.Sprite;

public class BombRangeIncrement extends Item {

    public BombRangeIncrement(int x, int y, Sprite sprite, int level) {
        super(x, y, sprite, level);
    }

    @Override
    public void setValue() {
        this.active = true;
        Game.bomb_range += 1;
    }
}
