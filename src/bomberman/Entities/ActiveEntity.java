package bomberman.Entities;

import bomberman.Sound.Sound;

public abstract class ActiveEntity extends Entity {
    protected int move_step = 0;
    protected final int MAX = 5000;
    protected Sound sound = new Sound();

    protected void activate() {
        if (move_step < MAX) {
            move_step++;
        } else {
            move_step = 0;
        }
    }

    protected void playSound(String filepath) {
        this.sound.playSound(filepath);
    }
}
