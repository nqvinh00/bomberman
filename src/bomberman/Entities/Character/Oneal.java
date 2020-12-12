package bomberman.Entities.Character;

import bomberman.GameBoard;
import bomberman.Graphics.Sprite;

public class Oneal extends Enemy {

    public Oneal(int x, int y, GameBoard board) {
        super(x, y, board, 150, 2 * 1.0 / 3, Sprite.oneal_dead);
        this.sprite = Sprite.oneal_right1;
        this.direction = this.findDirection();
    }

    @Override
    public void selectSprite() {
        switch (this.direction) {
            case 0: case 2:
            case 1:
                if (this.moving) {
                    this.sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3,
                            this.move_step, 60);
                } else {
                    this.sprite = Sprite.oneal_right1;
                }
                break;
            case 3:
                if (this.moving) {
                    this.sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3,
                            this.move_step, 60);
                } else {
                    this.sprite = Sprite.oneal_right1;
                }
                break;
        }
    }

    @Override
    public int findDirection() {
        return this.findBomberWithBFS();
    }
}
