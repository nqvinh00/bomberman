package bomberman.Entities.Character;

import bomberman.Game;
import bomberman.GameBoard;
import bomberman.Graphics.Sprite;

import java.util.Random;

public class Balloom extends Enemy {

    public Balloom(int x, int y, GameBoard board) {
        super(x, y, board, 100, Game.player_speed / 2, Sprite.balloom_dead);
        this.sprite = Sprite.balloom_right1;
        this.direction = this.findDirection();
    }

    @Override
    public void selectSprite() {
        switch (this.direction) {
            case 0: case 2:
            case 1:
                this.sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3,
                        this.move_step, 60);
                break;
            case 3:
                this.sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3,
                        this.move_step, 60);
                break;
        }
    }

    @Override
    public int findDirection() {
        return new Random().nextInt(4);
    }

    @Override
    public int findBomber() {
        return 0;
    }
}