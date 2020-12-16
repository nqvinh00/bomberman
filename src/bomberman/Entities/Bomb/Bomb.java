package bomberman.Entities.Bomb;

import bomberman.Entities.Character.Bomber;
import bomberman.Entities.Character.Character;
import bomberman.Entities.Entity;
import bomberman.Entities.ActiveEntity;
import bomberman.Game;
import bomberman.GameBoard;
import bomberman.Graphics.Screen;
import bomberman.Graphics.Sprite;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Bomb extends ActiveEntity {
    private final GameBoard board;
    private boolean exploded = false;
    private BombDirection[] directions = null;
    private boolean passed = true;
    private double explodedTime = 100;
    public int explosionDisappear = 20;

    /**
     * constructor.
     * @param x pos
     * @param y pos
     * @param board game
     */
    public Bomb(int x, int y, GameBoard board) {
        this.x = x;
        this.y = y;
        this.board = board;
        this.sprite = Sprite.bomb;
    }

    @Override
    public void update() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        if (this.explodedTime > 0) {
            this.explodedTime--;
        } else {
            if (!this.exploded) {
                this.createExplosion();
            } else {
                this.playSound("res/audio/explode.wav");
                this.updateExplosion();
            }

            if (this.explosionDisappear > 0) {
                this.explosionDisappear--;
            } else {
                this.remove();
            }
        }
        activate();
    }

    @Override
    public void render(Screen screen) {
        if (this.exploded) {
            this.sprite = Sprite.bomb_exploded2;
            this.renderExplosion(screen);
        } else {
            this.sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, this.move_step,60);
        }

        screen.renderEntity((int) (this.x * 16), (int) (this.y * 16), this);
    }

    @Override
    public boolean isCollided(Entity e) {
        if (e instanceof Bomber) {
            double x_ = e.getX() - this.x * Game.boardsprite_size;
            double y_ = e.getY() - this.y * Game.boardsprite_size;

            // if bomber has moved out of the bomb
            if (!(x_ >= -10 && x_ < 16 && y_ >= 1 && y_ <= 24)) {
                this.passed = false;
            }
            return this.passed;
        }

        if (e instanceof BombDirection) {
            this.explode();
            return true;
        }
        return false;
    }

    /**
     * render when bomb explodes.
     * @param screen game
     */
    public void renderExplosion(Screen screen) {
        for (BombDirection direction : this.directions) {
            direction.render(screen);
        }
    }

    /**
     * create explosion with bomb direction objects.
     * @throws IOException throw io exception
     * @throws LineUnavailableException throw when line audio error
     * @throws UnsupportedAudioFileException throw when audio type not supported
     */
    public void createExplosion() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        this.passed = true;
        this.exploded = true;
        Character character = this.board.getCharacterAtPos(x, y);
        if (character != null) {
            character.setProcess(true);
            character.dead();
        }
        this.directions = new BombDirection[4];

        for (int i = 0; i < this.directions.length; i++) {
            // i for direction parameter
            this.directions[i] = new BombDirection((int) this.x, (int) this.y, i, Game.bomb_range, this.board);
        }
        this.passed = false;
    }

    /**
     * make bomb explode.
     */
    public void explode() {
        this.explodedTime = 0;
    }

    /**
     * check if bomb is exploded or not.
     * @return true/false
     */
    public boolean isExploded() {
        return this.exploded;
    }

    /**
     * update bomb directions.
     * @throws IOException throw io exception
     * @throws LineUnavailableException throw when line audio error
     * @throws UnsupportedAudioFileException throw when audio type not supported
     */
    public void updateExplosion() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        for (BombDirection direction : this.directions) {
            direction.update();
        }
    }

    /**
     * getter for bomb explosion.
     * @param x pos
     * @param y pos
     * @return BombExplosion obj
     */
    public BombExplosion getBombExplosionAtPos(int x, int y) {
        if (!this.exploded) {
            return null;
        }

        for (BombDirection direction : this.directions) {
            if (direction == null) {
                return null;
            }
            BombExplosion bombExplosion = direction.getExplosionAtPos(x, y);
            if (bombExplosion != null) {
                return bombExplosion;
            }
        }
        return null;
    }
}
