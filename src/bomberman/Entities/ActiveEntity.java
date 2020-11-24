package bomberman.Entities;

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

    public abstract void playSound();
}
