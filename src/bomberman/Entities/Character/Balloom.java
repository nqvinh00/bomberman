package bomberman.Entities.Character;

import bomberman.GameBoard;
import bomberman.Graphics.Sprite;

public class Balloom extends Enemy {

    public Balloom(int x, int y, GameBoard board) {
        super(x, y, board, 100, 1.0 / 2, Sprite.balloom_dead);
        this.sprite = Sprite.balloom_right1;
        this.direction = this.findDirection();
    }

    @Override
    public void selectSprite() {
        switch (this.direction) {
            case 0, 2, 1 -> this.sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3,
                    this.move_step, 60);
            case 3 -> this.sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3,
                    this.move_step, 60);
        }
    }
}
