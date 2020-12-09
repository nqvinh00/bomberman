package bomberman.Entities.Item;

import bomberman.Entities.BoardSprite.BoardSprite;
import bomberman.Entities.Character.Bomber;
import bomberman.Entities.Entity;
import bomberman.Graphics.Sprite;
import bomberman.Sound.Sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public abstract class Item extends BoardSprite {
    protected int level;
    protected boolean active = false;
    private Sound sound = new Sound();

    public Item(int x, int y, Sprite sprite, int level) {
        super(x, y, sprite);
        this.level = level;
    }

    public boolean isCollided(Entity e) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (e instanceof Bomber) {
            this.sound.playSound("res/audio/powerup.wav");
            ((Bomber) e).addItem(this);
            this.remove();
            return true;
        }

        return false;
    }

    public abstract void setValue();

    public int getLevel() {
        return this.level;
    }

    public boolean isActived() {
        return !this.active;
    }
}
