package bomberman;

import bomberman.Bomb.Bomb;
import bomberman.Entities.Character.Bomber;
import bomberman.Entities.Entity;
import bomberman.Graphics.Render;
import bomberman.Graphics.Screen;
import bomberman.Control.Input;
import bomberman.Level.Level;
import bomberman.Entities.Character.Character;
import java.util.ArrayList;

public class GameBoard implements Render {
    public int width;
    public int height;
    protected Level level;
    protected Game gameplay;
    protected Input keyboard_input;
    protected Screen screen;
    public Entity[] entities;
    public ArrayList<Character> characters = new ArrayList<Character>();
    protected ArrayList<Bomb> bombs = new ArrayList<Bomb>();
//    private ArrayList<Message>;
//     1: game over 2: level change 3: paused
    private int screenNum = -1;
    private int time = Game.time;
    private int point = Game.point;
    private int live = Game.live;

    public GameBoard(Game gameplay, Input keyboard_input, Screen screen) {
        this.gameplay = gameplay;
        this.keyboard_input = keyboard_input;
        this.screen = screen;
        this.changeLevel(1);
    }

    @Override
    public void update() {
        if (this.gameplay.isPaused()) {
            return;
        }
        this.updateEntities();
        this.updateCharacters();
        this.updateBombs();
        this.levelEnd();

        for (Character c: characters) {
            Character c_ = c;
            if (((Entity) c_).isRemoved()) {
                characters.remove(c);
            }
        }
    }

    @Override
    public void render(Screen screen) {

    }

    public void newGame() {

    }

    private void resetGameSettings() {
//        this.time = Game.time;
        this.point = Game.point;
        this.live = Game.live;
        this.gameplay.player_speed = 1.0;
        this.gameplay.bomb_number = 1;
        this.gameplay.bomb_range = 1;
    }

    public void changeLevel(int level) {
        this.time = Game.time;
        this.screenNum = 2;
        this.gameplay.screen_delay = 3;
        this.gameplay.setPaused(true);
        this.characters.clear();
        this.bombs.clear();
//        this.level = new
    }

    protected void levelEnd() {
        if (this.time <= 0) {
            this.restartLevel();
        }
    }

    public void gameEnd() {
        this.screenNum = 1;
        this.gameplay.screen_delay = 3;
        this.gameplay.setPaused(true);
    }

    public void restartLevel() {

    }

    public void nextLevel() {

    }

    public void updateEntities() {
        if (this.gameplay.isPaused()) {
            return;
        }

        for (int i = 0; i < this.entities.length; i++) {
            this.entities[i].update();
        }
    }

    public void updateCharacters() {
        if (this.gameplay.isPaused()) {
            return;
        }

        for (Character c: characters) {
            c.update();
        }
    }

    public void updateBombs() {
        if (this.gameplay.isPaused()) {
            return;
        }

        for (Bomb b: bombs) {
            b.update();
        }
    }

    public void addEntity(int position, Entity entity) {
        this.entities[position] = entity;
    }

    public boolean isNoEnemies() {
        int n = 0;
        for (int i = 0; i < this.characters.size(); i++) {
            if (!(this.characters.get(i) instanceof Bomber)) {
                n++;
            }
        }
        return n == 0;
    }
}
