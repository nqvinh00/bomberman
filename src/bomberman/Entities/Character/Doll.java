package bomberman.Entities.Character;

import bomberman.Game;
import bomberman.GameBoard;
import bomberman.Graphics.Sprite;

public class Doll extends Enemy {

    public Doll(int x, int y, GameBoard board) {
        super(x, y, board, 200, Game.player_speed, Sprite.doll_dead);
        this.sprite = Sprite.doll_right1;
        this.direction = this.getDirection();
    }

    @Override
    public void selectSprite() {
        switch (this.direction) {
            case 0, 2, 1 -> this.sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3,
                    this.move_step, 60);
            case 3 -> this.sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3,
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
