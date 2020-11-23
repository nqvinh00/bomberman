package bomberman.Entities.Bomb;

import bomberman.Entities.Entity;
import bomberman.Graphics.Screen;
import bomberman.Graphics.Sprite;
import bomberman.Entities.Character.Character;

import java.io.IOException;

public class BombExplosion extends Entity {

    public BombExplosion(int x, int y, int direction, boolean isLastExplosion) {
        this.x = x;
        this.y = y;
        switch (direction) {
            case 0:
                if (isLastExplosion) {
                    this.sprite = Sprite.explosion_vertical_top_last2;
                } else {
                    this.sprite = Sprite.explosion_vertical2;
                }
                break;
            case 1:
                if (isLastExplosion) {
                    this.sprite = Sprite.explosion_horizontal_right_last2;
                } else {
                    this.sprite = Sprite.explosion_horizontal2;
                }
                break;
            case 2:
                if (isLastExplosion) {
                    this.sprite = Sprite.explosion_vertical_down_last2;
                } else {
                    this.sprite = Sprite.explosion_vertical2;
                }
                break;
            case 3:
                if (isLastExplosion) {
                    this.sprite = Sprite.explosion_horizontal_left_last2;
                } else {
                    this.sprite = Sprite.explosion_horizontal2;
                }
                break;
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity((int) this.x * 16, (int) this.y * 16, this);
    }

    @Override
    public boolean isCollided(Entity e) throws IOException {
        if (e instanceof Character) {
            e.setProcess(true);
            ((Character) e).dead();
        }
        return true;
    }
}
