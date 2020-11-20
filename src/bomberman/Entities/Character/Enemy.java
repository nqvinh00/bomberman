package bomberman.Entities.Character;

import bomberman.Entities.Bomb.BombExplosion;
import bomberman.Entities.Entity;
import bomberman.Game;
import bomberman.GameBoard;
import bomberman.Graphics.Screen;
import bomberman.Graphics.Sprite;

import java.io.IOException;

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
    public void update() throws IOException {
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
    public boolean isCollided(Entity e) {
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
    protected void moveStep() throws IOException {
        int x_ = 0;
        int y_ = 0;
        if (this.moveStep <= 0) {
            this.direction = this.findDirection();
            this.moveStep = this.maxStep;
        }

        switch (this.direction) {
            case 0: y_--; break;
            case 1: x_++; break;
            case 2: y_++; break;
            case 3: x_--; break;
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
    public boolean canMoveTo(double posX, double posY) throws IOException {
        double x_ = this.x;
        double y_ = this.y - 16;

        switch (this.direction) {
            case 0:
                x_ += this.sprite.getSize() / 2;
                y_ += this.sprite.getSize() - 1;
                break;
            case 1:
                x_++;
                y_ += this.sprite.getSize() / 2;
                break;
            case 2:
                x_ += this.sprite.getSize() / 2;
                y_++;
                break;
            case 3:
                x_ += this.sprite.getSize() - 1;
                y_ += this.sprite.getSize() / 2;
                break;
        }

        Entity entity = this.board.getEntity((int) (x_ / Game.boardsprite_size) + (int) posX,
                (int) (y_ / Game.boardsprite_size) + (int) posY, this);
        return entity.isCollided(this);
    }

    public abstract void selectSprite();

    public abstract int findDirection(); // use for low level creeps

    public abstract int findBomber(); // maybe use for high level creeps

}
