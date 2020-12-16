package bomberman.Entities.Bomb;

import bomberman.Entities.Entity;
import bomberman.GameBoard;
import bomberman.Graphics.Screen;
import bomberman.Entities.Character.Character;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class BombDirection extends Entity {
    private final GameBoard board;
    private final int direction;
    private final int bombRange;
    private final BombExplosion[] explosions;

    /**
     * constructor.
     * @param x pos
     * @param y pos
     * @param direction value
     * @param range bomb
     * @param board game
     * @throws IOException throw io exception
     * @throws LineUnavailableException throw when line audio error
     * @throws UnsupportedAudioFileException throw when audio type not supported
     */
    public BombDirection(int x, int y, int direction, int range, GameBoard board) throws IOException,
            LineUnavailableException, UnsupportedAudioFileException {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.board = board;
        this.bombRange = range;
        this.explosions = new BombExplosion[calculateRealBombRange()];
        this.createExplosions();
    }

    @Override
    public void update() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        for (BombExplosion bombExplosion: this.explosions) {
            Character character = this.board.getCharacterAtPos(bombExplosion.getX(), bombExplosion.getY());
            if (character != null) {
                bombExplosion.isCollided(character);
            }
        }
    }

    @Override
    public void render(Screen screen) {
        for (BombExplosion explosion: this.explosions) {
            explosion.render(screen);
        }
    }

    @Override
    public boolean isCollided(Entity e) {
        return false;
    }

    /**
     * get explosion at specific position.
     * @param x pos
     * @param y pos
     * @return explosion
     */
    public BombExplosion getExplosionAtPos(int x, int y) {
        for (BombExplosion explosion : this.explosions) {
            if (explosion.getX() == x && explosion.getY() == y) {
                return explosion;
            }
        }
        return null;
    }

    /**
     * calculate the real bomb range could be on the game board, base on bom range parameter.
     * @return range
     * @throws IOException file not found
     */
    public int calculateRealBombRange() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        int range_ = 0;
        int x_ = (int) x;
        int y_ = (int) y;
        while (range_ < this.bombRange) {
            switch (this.direction) {
                case 0 -> y_--;
                case 1 -> x_++;
                case 2 -> y_++;
                case 3 -> x_--;
            }

            Entity entity = this.board.getEntity(x_, y_, null);
            // bomb explodes under character
            if (entity instanceof Character) {
                ++range_;
            }

            // entity cannot pass through the bomb
            if (!entity.isCollided(this)) {
                break;
            }
            ++range_;
        }
        return range_;
    }

    /**
     * create explosions for the bomb.
     */
    public void createExplosions() {
        boolean isLastExplosion;
        int x_ = (int) this.x;
        int y_ = (int) this.y;
        for (int i = 0; i < this.explosions.length; i++) {
            isLastExplosion = i == this.explosions.length - 1;
            switch (this.direction) {
                case 0 -> y_--;
                case 1 -> x_++;
                case 2 -> y_++;
                case 3 -> x_--;
            }
            this.explosions[i] = new BombExplosion(x_, y_, this.direction, isLastExplosion);
        }
    }
}
