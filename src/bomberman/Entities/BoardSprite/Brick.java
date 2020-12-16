package bomberman.Entities.BoardSprite;

import bomberman.Entities.Bomb.BombDirection;
import bomberman.Entities.Character.Kondoria;
import bomberman.Entities.Entity;
import bomberman.Game;
import bomberman.GameBoard;
import bomberman.Graphics.Screen;
import bomberman.Graphics.Sprite;

import java.util.Random;

public class Brick extends BoardSprite {
    protected Sprite grass = Sprite.grass; // if object is destroyed, its position will be a grass
    protected boolean destroyed = false;
    private int animate = 0;
    private int disappear = 15;

    /**
     * constructor
     * @param x pos
     * @param y pos
     * @param sprite brick
     */
    public Brick(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void render(Screen screen) {
        int x_ = (int) (this.x * Game.boardsprite_size);
        int y_ = (int) (this.y * Game.boardsprite_size);
        if (this.destroyed) {
            Random rand = new Random();
            int i = rand.nextInt(3);
            if (i == 0) {
                this.sprite = Sprite.brick_exploded;
            } else if (i == 1) {
                this.sprite = Sprite.brick_exploded1;
            } else {
                this.sprite = Sprite.brick_exploded2;
            }
            screen.renderEntityUnderSprite(x_, y_, this, sprite);
        } else {
            screen.renderEntity(x_, y_, this);
        }
    }

    @Override
    public boolean isCollided(Entity e) {
        if (e instanceof BombDirection) {
            this.destroyed = true;
            GameBoard.destroyedBrick.add(this);
        }

        return e instanceof Kondoria;
    }

    /**
     * add grass below.
     * @param grass sprite
     */
    public void addGrass(Sprite grass) {
        this.grass = grass;
    }

    @Override
    public void update() {
        if (this.destroyed) {
            int max = 5000;
            if (this.animate < max) {
                this.animate++;
            } else {
                this.animate = 0;
            }

            if (this.disappear > 0) {
                this.disappear--;
            } else {
                this.remove();
            }
        }
    }

    /**
     * check if brick is destroyed or not.
     * @return true/false
     */
    public boolean isDestroyed() {
        return this.destroyed;
    }
}
