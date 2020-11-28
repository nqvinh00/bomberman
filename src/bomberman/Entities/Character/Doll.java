package bomberman.Entities.Character;

import bomberman.Entities.Bomb.Bomb;
import bomberman.Game;
import bomberman.GameBoard;
import bomberman.Graphics.Sprite;

import java.util.ArrayList;
import java.util.Random;

public class Doll extends Enemy {

    public Doll(int x, int y, GameBoard board) {
        super(x, y, board, 200, 1.0, Sprite.doll_dead);
        this.sprite = Sprite.doll_right1;
        this.direction = this.findBomber();
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
    public int findBomber() {
        boolean hasBomb = false;
        for (Bomb bomb: this.board.getBombs()) {
            int bombDetect = this.bombDetect(bomb.getBoardSpriteY(), bomb.getBoardSpriteY());
            if (bombDetect != -1) {
                hasBomb = true;
            }
        }


        if (!hasBomb) {
            if (new Random().nextInt(2) == 1) {
                int row = this.bomberRowDirection();
                if (row != -1) {
                    return row;
                } else {
                    return this.bomberColDirection();
                }
            } else {
                int col = this.bomberColDirection();
                if (col != -1) {
                    return col;
                } else {
                    return this.bomberRowDirection();
                }
            }
        }

        return this.findDirection();
    }
}
