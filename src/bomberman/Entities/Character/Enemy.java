package bomberman.Entities.Character;

import bomberman.Entities.Bomb.BombExplosion;
import bomberman.Entities.Entity;
import bomberman.Game;
import bomberman.GameBoard;
import bomberman.Graphics.Screen;
import bomberman.Graphics.Sprite;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Random;

public abstract class Enemy extends Character {
    protected int pointKill;
    protected double speed;
    protected double maxStep;
    protected double moveStep;
    protected int animation = 30;
    protected Sprite deadSprite;

    public Enemy(int x, int y, GameBoard board, int pointKill, double speed, Sprite sprite) {
        super(x, y, board);
        this.pointKill = pointKill;
        this.speed = speed;
        this.deadSprite = sprite;
        this.maxStep = Game.boardsprite_size / this.speed;
        this.moveStep = this.maxStep;
        this.timeDead = 20;
    }

    @Override
    public void update() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        activate();
        if (!this.alive) {
            afterDead();
            return;
        }
        Bomber bomber = this.board.getBomber();
        if (bomber != null) {
            if (Math.abs((int) bomber.getX() - (int) this.x) <= 10 && Math.abs((int) bomber.getY() - (int) this.y) <= 10) {
                bomber.setProcess(true);
                this.isCollided(bomber);
            }
        }
        this.moveStep();
    }

    @Override
    public void render(Screen screen) {
        if (this.alive) {
            this.selectSprite();
        } else {
            if (this.timeDead > 0) {
                this.sprite = this.deadSprite;
                this.move_step = 0;
            } else {
                this.sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, this.move_step, 45);
            }
        }
        screen.renderEntity((int) this.x, (int) this.y - this.sprite.getSize(), this);
    }

    @Override
    public boolean isCollided(Entity e) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (e instanceof BombExplosion) {
            dead();
            return false;
        }

        if (e instanceof Bomber) {
            ((Bomber) e).dead();
            return false;
        }

        return true;
    }

    @Override
    public void dead() {
        if (!this.alive) {
            return;
        }
        this.alive = false;
        this.board.setPoint(this.board.getPoint() + this.pointKill);
    }

    @Override
    public void afterDead() {
        if (this.timeDead > 0) {
            this.timeDead--;
        } else {
            if (this.animation > 0) {
                this.animation--;
            } else {
                this.remove();
            }
        }
    }

    @Override
    protected void move(double deltaX, double deltaY) {
        if (!this.alive) {
            return;
        }
        this.x += deltaX;
        this.y += deltaY;
    }

    @Override
    protected void moveStep() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        int x_ = 0;
        int y_ = 0;
        if (this.moveStep <= 0) {
            this.direction = this.findDirection();
            this.moveStep = this.maxStep;
        }

        switch (this.direction) {
            case 0 -> y_--;
            case 1 -> x_++;
            case 2 -> y_++;
            case 3 -> x_--;
        }

        if (canMoveTo(x_, y_)) {
            this.moveStep -= 1 + (this.maxStep - (int) this.maxStep) / this.maxStep;
            this.move(x_ * this.speed, y_ * this.speed);
            this.moving = true;
        } else {
            this.moveStep = 0;
            this.moving = false;
        }
    }

    @Override
    public boolean canMoveTo(double posX, double posY) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        double x_ = this.x;
        double y_ = this.y - 16;

        switch (this.direction) {
            case 0 -> {
                x_ += this.sprite.getSize() / 2;
                y_ += this.sprite.getSize() - 1;
            }
            case 1 -> {
                x_++;
                y_ += this.sprite.getSize() / 2;
            }
            case 2 -> {
                x_ += this.sprite.getSize() / 2;
                y_++;
            }
            case 3 -> {
                x_ += this.sprite.getSize() - 1;
                y_ += this.sprite.getSize() / 2;
            }
        }

        Entity entity = this.board.getEntity((int) (x_ / Game.boardsprite_size) + (int) posX,
                (int) (y_ / Game.boardsprite_size) + (int) posY, this);
        return entity.isCollided(this);
    }

    public abstract void selectSprite();

    /**
     * use for low level enemy.
     * @return random(1, 4)
     */
    public int findDirection() {
        return new Random().nextInt(4);
    }

    public abstract int findBomber();

    public int bomberColDirection() {
        if (this.board.getBomber().getBoardSpriteX() < this.getBoardSpriteX()) {
            return 3;
        } else if (this.board.getBomber().getBoardSpriteX() > this.getBoardSpriteX()) {
            return 1;
        }
        return -1;
    }

    public int bomberRowDirection() {
        if (this.board.getBomber().getBoardSpriteY() < this.getBoardSpriteY()) {
            return 0;
        } else if (this.board.getBomber().getBoardSpriteY() > this.getBoardSpriteY()) {
            return 2;
        }
        return -1;
    }

    @Override
    public void playSound(String filepath) {

    }

    public int bombDetect(int x, int y) {
        int x_ = this.getBoardSpriteX();
        int y_ = this.getBoardSpriteY();

        // row
        if (y_ == y) {
            if (x - x_ > 0 && x - x_ <= Game.bomb_range) {
                return 1; // right
            }

            if (x - x_ < 0 && x - x_ >= Game.bomb_range) {
                return 3; // left
            }
        }

        // col
        if (x_ == x) {
            if (y - y_ > 0 && y - y_ <= Game.bomb_range) {
                return 2; // up
            }

            if (y - y_ < 0 && y - y_ >= Game.bomb_range) {
                return 0; // down
            }
        }

        // top corner
        if (y - y_ < 0 && y - y_ >= Game.bomb_range) {
            if (x - x_ > 0 && x - x_ <= Game.bomb_range) {
                return 12; // top right
            }

            if (x - x_ < 0 && x - x_ >= Game.bomb_range) {
                return 32; // top left
            }
        }

        // down corner
        if (y - y_ < 0 && y - y_ >= Game.bomb_range) {
            if (x - x_ > 0 && x - x_ <= Game.bomb_range) {
                return 10; // down right
            }

            if (x - x_ < 0 && x - x_ >= Game.bomb_range) {
                return 30; // down left
            }
        }
        return -1;
    }
}
