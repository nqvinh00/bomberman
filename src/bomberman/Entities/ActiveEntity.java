package bomberman.Entities;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class ActiveEntity extends Entity {
    protected int move_step = 0;
    protected final int MAX = 5000;

    protected void activate() {
        if (move_step < MAX) {
            move_step++;
        } else {
            move_step = 0;
        }
    }

    public abstract void playSound(String filepath) throws LineUnavailableException, IOException, UnsupportedAudioFileException;
}
