package bomberman.Entities.Item;

import bomberman.Entities.BoardSprite.BoardSprite;
import bomberman.Entities.Character.Bomber;
import bomberman.Entities.Entity;
import bomberman.Graphics.Sprite;
import bomberman.Sound.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class Item extends BoardSprite {
    protected int level;
    protected boolean active = false;
    private Sound sound = new Sound();

    /**
     * constructor.
     * @param x pos
     * @param y pos
     * @param sprite of item
     * @param level current level
     */
    public Item(int x, int y, Sprite sprite, int level) {
        super(x, y, sprite);
        this.level = level;
    }

    @Override
    public boolean isCollided(Entity e) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (e instanceof Bomber) {
            this.sound.playSound("res/audio/powerup.wav");
            ((Bomber) e).addItem(this);
            this.remove();
            return true;
        }

        return false;
    }

    /**
     * set value like bomb range, of bomber speed for item when collided.
     */
    public abstract void setValue();

    /**
     * get current level.
     * @return level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * check if item is activated of not.
     * @return true/false
     */
    public boolean isActivated() {
        return !this.active;
    }
}
