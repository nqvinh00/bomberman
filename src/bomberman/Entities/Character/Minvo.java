package bomberman.Entities.Character;

import bomberman.GameBoard;
import bomberman.Graphics.Sprite;

public class Minvo extends Enemy {

    /**
     * constructor
     * @param x pos
     * @param y pos
     * @param board game
     */
    public Minvo(int x, int y, GameBoard board) {
        super(x, y, board, 250, 1.0 * 1.5, Sprite.minvo_dead);
        this.sprite = Sprite.minvo_right1;
        this.direction = this.findDirection();
    }

    @Override
    public void selectSprite() {
        switch (this.direction) {
            case 0, 2, 1 -> this.sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3,
                    this.move_step, 60);
            case 3 -> this.sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3,
                    this.move_step, 60);
        }
    }
}
