package bomberman.Entities.Character;

import bomberman.Game;
import bomberman.GameBoard;
import bomberman.Graphics.Sprite;

public class Kondoria extends Enemy {

    public Kondoria(int x, int y, GameBoard board) {
        super(x, y, board, 300, Game.player_speed / 2, Sprite.kondoria_dead);
        this.sprite = Sprite.kondoria_right1;
        this.direction = this.findBomber();
    }

    @Override
    public void selectSprite() {
        switch (this.direction) {
            case 0, 2, 1 -> this.sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3,
                    this.move_step, 60);
            case 3 -> this.sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3,
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
