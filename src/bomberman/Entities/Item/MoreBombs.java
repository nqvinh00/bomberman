package bomberman.Entities.Item;

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
}
