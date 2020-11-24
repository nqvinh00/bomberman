package bomberman.Entities.Item;

import bomberman.Entities.BoardSprite.BoardSprite;
import bomberman.Graphics.Sprite;

public abstract class Item extends BoardSprite {
    protected int level;
    protected boolean active = false;

    public Item(int x, int y, Sprite sprite, int level) {
        super(x, y, sprite);
        this.level = level;
    }

    public abstract void setValue();

    public int getLevel() {
        return this.level;
    }

    public boolean isActived() {
        return this.active;
    }

    public void playSound() {

    }
}
