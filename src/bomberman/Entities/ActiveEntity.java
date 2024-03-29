package bomberman.Entities;

import bomberman.Sound.Sound;

public abstract class ActiveEntity extends Entity {
    protected int move_step = 0;
    protected final int MAX = 5000;
    protected Sound sound = new Sound();

    /**
     * create and reset action of active entity
     */
    protected void activate() {
        if (move_step < MAX) {
            move_step++;
        } else {
            move_step = 0;
        }
    }

    /**
     * play sound effect.
     * @param filepath of audio file
     */
    protected void playSound(String filepath) {
        this.sound.playSound(filepath);
    }
}
