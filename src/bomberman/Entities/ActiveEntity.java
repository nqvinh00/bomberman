package bomberman.Entities;

public abstract class MovableEntity extends Entity {
    protected int move_step = 0;
    protected final int MAX = 5000;

    protected void move() {
        if (move_step < MAX) {
            move_step++;
        } else {
            move_step = 0;
        }
    }
}
