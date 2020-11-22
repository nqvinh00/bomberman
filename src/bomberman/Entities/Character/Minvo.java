package bomberman.Entities.Character;

import bomberman.Game;
import bomberman.GameBoard;
import bomberman.Graphics.Sprite;

public class Minvo extends Enemy {
    public Minvo(int x, int y, GameBoard board) {
        super(x, y, board, 250, Game.player_speed * 1.5, Sprite.minvo_dead);
        this.sprite = Sprite.minvo_right1;
        this.direction = this.findBomber();
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

    @Override
    public int findDirection() {
        return 0;
    }

    @Override
    public int findBomber() {
        return 0;
    }
}
