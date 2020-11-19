package bomberman.Entities.Character;

import bomberman.Entities.Bomb.Bomb;
import bomberman.Control.Input;
import bomberman.Entities.Bomb.BombDirection;
import bomberman.Entities.Entity;
import bomberman.Entities.Item.Item;
import bomberman.Game;
import bomberman.GameBoard;
import bomberman.Graphics.Screen;
import bomberman.Graphics.Sprite;

import java.io.IOException;
import java.util.ArrayList;

public class Bomber extends Character {
    private ArrayList<Bomb> bombs;
    protected Input keyboard_input;
    protected int bombPlaceDelay = 0;
    public static ArrayList<Item> items = new ArrayList<Item>();

    public Bomber(int x, int y, GameBoard board) {
        super(x, y, board);
        this.bombs = board.getBombs();
        this.keyboard_input = board.getKeyboardInput();
        this.sprite = Sprite.player_right;
    }

    @Override
    public void update() throws IOException {
        if (!this.alive) {
            afterDead();
            return;
        }

        if (bombPlaceDelay > 5000) {
            bombPlaceDelay = 0;
        } else {
            bombPlaceDelay++;
        }
        this.activate();
        this.moveStep();
        this.whenPlaceBomb();

    }

    @Override
    public void render(Screen screen) {
        this.calculateDelta();
        if (this.alive) {
            switch (this.direction) {
                case 0:
                    this.sprite = Sprite.player_up;
                    if (this.moving) {
                        this.sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, this.move_step, 20);
                    }
                    break;

                case 1:
                    this.sprite = Sprite.player_right;
                    if (this.moving) {
                        this.sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, this.move_step, 20);
                    }
                    break;

                case 2:
                    this.sprite = Sprite.player_down;
                    if (this.moving) {
                        this.sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, this.move_step, 20);
                    }
                    break;

                case 3:
                    this.sprite = Sprite.player_left;
                    if (this.moving) {
                        this.sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, this.move_step, 20);
                    }
                    break;
                default:
                    this.sprite = Sprite.player_right;
                    break;
                }
        } else {
            this.sprite = Sprite.player_dead1;
        }
        screen.renderEntity((int) x, (int) y - this.sprite.getSize(), this);
    }

    @Override
    public void dead() {
        if (!this.alive) {
            return;
        }
        this.alive = false;
        this.board.addLive(-1);
        this.clearUsedItems();
    }

    public void calculateDelta() {
        int delta = Screen.calculateDelta(this, this.board);
        Screen.setDelta(delta, 0);
    }

    @Override
    public void afterDead() throws IOException {
        if (this.timeDead > 0) {
            --this.timeDead;
        } else {
            if (this.bombs.size() == 0) {
                if (this.board.getLive() == 0) {
                    this.board.gameEnd();
                } else {
                    this.board.restartLevel();
                }
            }
        }
    }

    @Override
    protected void move(double deltaX, double deltaY) throws IOException {
        if (deltaY < 0) {
            this.direction = 0;
        }

        if (deltaX > 0) {
            this.direction = 1;
        }

        if (deltaY > 0) {
            this.direction = 2;
        }

        if (deltaX < 0) {
            this.direction = 3;
        }

//        if (canMoveTo(deltaX, deltaY)) {
//            this.x += deltaX;
//            this.y += deltaY;
//        }

        if (canMoveTo(deltaX, 0)) {
            this.x += deltaX;
        }

        if (canMoveTo(0, deltaY)) {
            this.y += deltaY;
        }
    }

    @Override
    protected void moveStep() throws IOException {
        int x_ = 0;
        int y_ = 0;
        if (this.keyboard_input.up) {
            y_--;
        }

        if (this.keyboard_input.down) {
            y_++;
        }

        if (this.keyboard_input.right) {
            x_++;
        }

        if (this.keyboard_input.left) {
            x_--;
        }
        if (x_ != 0 || y_ != 0) {
            this.move(x_ * Game.player_speed, y_ * Game.player_speed);
            this.moving = true;
        } else {
            this.moving = false;
        }
    }

    @Override
    public boolean canMoveTo(double posX, double posY) throws IOException {
        for (int dir = 0; dir < 4; dir++) {
            double x_ = (this.x + posX + dir % 2 * 11) / Game.boardsprite_size;
            double y_ = (this.y + posY + dir / 2 * 12 - 13) / Game.boardsprite_size;
            Entity entity = this.board.getEntity(x_, y_, this);
            if (!entity.isCollided(this)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isCollided(Entity e) {
        if (e instanceof BombDirection) {
            this.dead();
            return false;
        }

        if (e instanceof Enemy) {
            this.dead();
            return true;
        }

        return true;
    }

    public void addItem(Item item) {
        if (!item.isRemoved()) {
            items.add(item);
            item.setValue();
        }
    }

    public void placeBomb(int x, int y) {
        this.board.addBomb(new Bomb(x, y, this.board));
    }

    public void whenPlaceBomb() {
        if (this.keyboard_input.space && Game.bomb_number > 0 && this.bombPlaceDelay > 0) {
            int x_ = (int) ((this.x + this.sprite.getSize() / 2) / Game.boardsprite_size);
            int y_ = (int) ((this.y + this.sprite.getSize() / 2 - this.sprite.getSize()) / Game.boardsprite_size);
            this.placeBomb(x_, y_);
            Game.bomb_number--;
            this.bombPlaceDelay = 25;
        }
    }

    public void clearUsedItems() {
        items.removeIf(item -> !item.isActived());
    }

    public void clearAllItems() {
        for (Item item: items) {
            items.remove(item);
        }
    }

}
