package bomberman;

import bomberman.Entities.BoardSprite.Brick;
import bomberman.Entities.Bomb.Bomb;
import bomberman.Entities.Bomb.BombExplosion;
import bomberman.Entities.Character.Bomber;
import bomberman.Entities.Character.Enemy;
import bomberman.Entities.Entity;
import bomberman.Entities.Item.Item;
import bomberman.Graphics.Screen;
import bomberman.Control.Input;
import bomberman.Level.Level;
import bomberman.Entities.Character.Character;
import bomberman.Sound.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GameBoard {
    protected Level level;
    protected Game gameplay;
    protected Input keyboard_input;
    protected Screen screen;
    public Entity[] entities;
    public ArrayList<Character> characters = new ArrayList<>();
    protected ArrayList<Bomb> bombs = new ArrayList<>();
//    1: game over 2: level change 3: paused 4: game win
    private int screenNum = -1;
    private int time = Game.time;
    private int point = Game.point;
    private int live = Game.live;
    public static ArrayList<Brick> destroyedBrick;

    public GameBoard(Game gameplay, Input keyboard_input, Screen screen) throws IOException, LineUnavailableException {
        this.gameplay = gameplay;
        this.keyboard_input = keyboard_input;
        this.screen = screen;
        this.changeLevel(1);
    }

    public void update() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        if (this.gameplay.isPaused()) {
            return;
        }
        this.updateEntities();
        this.updateCharacters();
        this.updateBombs();
        this.whenGameEnd();
        for (int i = 0; i < characters.size(); i++) {
            Character character = characters.get(i);

            if (!character.isAlive()) {
                character.afterDead();
            }

            if (character.isRemoved()) {
                this.characters.remove(i);
            }
        }

        for (int i = 0; i < this.bombs.size(); i++) {
            Bomb bomb = this.bombs.get(i);
            if (bomb.isRemoved()) {
                this.bombs.remove(i);
                Game.bomb_number++;
            }
        }
        this.gameMasterMode();
    }

    public void render(Screen screen) throws IOException {
        if (this.gameplay.isPaused()) {
            return;
        }
        // Visible range to render
        int x0 = Screen.deltax / 16;
        int x1 = (Screen.deltax + screen.getWidth() + Game.boardsprite_size) / Game.boardsprite_size;
        int y0 = Screen.deltay / 16;
        int y1 = (Screen.deltay + screen.getHeight()) / Game.boardsprite_size;
        for (int iy = y0 ; iy < y1; iy++) {
            for (int ix = x0; ix < x1; ix++) {
                if (ix + iy * this.level.getWidth() != 403) this.entities[ix + iy * this.level.getWidth()].render(screen);
            }
        }
        this.renderBombs(screen);
        this.renderChars(screen);
    }

    /**
     * reset all settings for new game.
     */
    private void resetGameSettings() {
        this.point = Game.point;
        this.live = Game.live;
        this.getBomber().clearUsedItems();
        Game.player_speed = 1.0;
        Game.bomb_number = 1;
        Game.bomb_range = 1;
    }

    /**
     *
     * @param level_ level to change.
     * @throws IOException if file level.txt not found
     */
    public void changeLevel(int level_) throws IOException {
        this.time = Game.time;
        this.screenNum = 2;
        Game.screen_delay = 2;
        this.gameplay.setPaused(true);
        this.characters.clear();
        this.bombs.clear();
        this.level = new Level("res/levels/Level" + level_ + ".txt", this);
        this.entities = new Entity[this.level.getHeight() * this.level.getWidth()];
        this.level.createEntities();
        Sound sound = new Sound();
        sound.playSound("res/audio/level_change.wav");
        destroyedBrick = new ArrayList<>();
    }

    /**
     * create new game.
     * @throws IOException if file level.txt not found
     */
    public void newGame() throws IOException {
        this.resetGameSettings();
        this.changeLevel(1);
    }

    /**
     * draw game screen.
     * @param graphics object
     */
    public void drawScreen(Graphics graphics) {
        if (this.screenNum == 1) {
            this.screen.drawGameEnding(graphics, this.point);
        } else if (this.screenNum == 2) {
            this.screen.drawLevelChanging(graphics, this.level.getLevel());
        } else if (this.screenNum == 3) {
            this.screen.drawGamePausing(graphics);
        } else if (this.screenNum == 4) {
            this.screen.drawGameWinning(graphics, this.point);
        }
    }

    /**
     * restart the current level in case time out and bomber cannot find the portal.
     * @throws IOException if file level.txt not found
     */
    protected void levelEnd() throws IOException {
        if (this.time <= 0) {
            this.restartLevel();
        }
    }

    /**
     * make game end.
     */
    public void gameEnd() {
        this.screenNum = 1;
        Game.screen_delay = 2;
        this.gameplay.setPaused(true);
    }

    /**
     * restart the current level in case time out and bomber cannot find the portal.
     * @throws IOException if file level.txt not found
     */
    public void whenGameEnd() throws IOException {
        if (this.time <= 0) {
            this.restartLevel();
        }
    }

    /**
     * pause game.
     */
    public void pauseGame() {
        Game.screen_delay = 2;
        if (this.screenNum <= 0) {
            this.screenNum = 3;
        }
        this.gameplay.setPaused(true);
    }

    public void setScreenNum(int i) {
        this.screenNum = i;
    }

    /**
     * unpause game.
     */
    public void resumeGame() {
        Game.screen_delay = 2;
        this.screenNum = -1;
        this.gameplay.run();
    }

    /**
     * restart the current level/ while dead.
     * @throws IOException if file level.txt not found
     */
    public void restartLevel() throws IOException {
        this.changeLevel(this.level.getLevel());
    }

    /**
     * next level.
     * @throws IOException if file level.txt not found
     */
    public void nextLevel() throws IOException {
        if (this.level.getLevel() == 5) {
            this.screenNum = 4;
            Game.screen_delay = 2;
            this.gameplay.setPaused(true);
            Sound sound = new Sound();
            sound.playSound("res/audio/win.wav");
        } else {
            this.changeLevel(this.level.getLevel() + 1);
        }
    }

    /**
     * update entities animation.
     */
    public void updateEntities() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        if (this.gameplay.isPaused()) {
            return;
        }

        for (Entity entity : this.entities) {
            entity.update();
        }
    }

    /**
     * add character to game board.
     * @param c character to add
     */
    public void addCharacter(Character c) {
        this.characters.add(c);
    }

    public void addBomb(Bomb bomb) {
        this.bombs.add(bomb);
    }

    /**
     * update character animation.
     */
    public void updateCharacters() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        if (this.gameplay.isPaused()) {
            return;
        }

        for (Character c: characters) {
            if (!c.isProcessed()) {
                c.update();
            }
        }
    }

    /**
     * update bombs animation.
     */
    public void updateBombs() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        if (this.gameplay.isPaused()) {
            return;
        }

        for (Bomb b: bombs) {
            b.update();
        }
    }

    /**
     * add an entity.
     * @param position to add
     * @param entity to add
     */
    public void addEntity(int position, Entity entity) {
        this.entities[position] = entity;
    }

    /**
     * check if there are no enemies on board/ all other character != Bomber.
     * @return true/false
     */
    public boolean isNoEnemies() {
        int n = 0;
        for (Character character : this.characters) {
            if (character instanceof Enemy) {
                n++;
            }
        }
        return n == 0;
    }

    public void renderBombs(Screen screen) {
        for (Bomb b: this.bombs) {
            b.render(screen);
        }
    }

    public void renderChars(Screen screen) throws IOException {
        for (Character c: this.characters) {
            c.render(screen);
        }
    }

    /**
     * getter of bombs.
     * @return bombs list
     */
    public ArrayList<Bomb> getBombs() {
        return this.bombs;
    }

    /**
     * getter of keyboard_input
     * @return keyboard_input
     */
    public Input getKeyboardInput() {
        return this.keyboard_input;
    }

    /**
     * add live to bomber, use with game restart or new level.
     * @param i lives
     */
    public void addLive(int i) {
        this.live += i;
    }

    /**
     * check if an item used/ bomber walk through an item
     * @param x x pos
     * @param y y pos
     * @param level game level
     * @return true/false
     */
    public boolean isItemUsed(int x, int y, int level) {
        for (Item i: Bomber.items) {
            if (i.getX() == x && i.getY() == y && i.getLevel() == level) {
                return true;
            }
        }
        return false;
    }

    /**
     * get character at specific position
     * @param x x pos
     * @param y y pos
     * @return character
     */
    public Character getCharacterAtPos(double x, double y) {
        for (Character c: this.characters) {
            if (c.getBoardSpriteX() == x && c.getBoardSpriteY() == y) {
                return c;
            }
        }
        return null;
    }

    /**
     * get the character at specific position but exclude 1 character.
     * @param x x pos
     * @param y y pos
     * @param character excluded
     * @return character
     */
    public Character getCharacterAtPosAndExclude(int x, int y, Character character) {
        for (Character c: this.characters) {
            if (c == character) {
                continue;
            }

            if (c.getBoardSpriteX() == x && c.getBoardSpriteY() == y) {
                return c;
            }
        }
        return null;
    }

    public Bomber getBomber() {
        for (Character c: this.characters) {
            if (c instanceof Bomber) {
                return (Bomber) c;
            }
        }
        return null;
    }

    public Bomb getBombAtPos(double x, double y) {
        for (Bomb b: this.bombs) {
            if (b.getX() == x && b.getY() == y) {
                return b;
            }
        }
        return null;
    }

    public BombExplosion getExplosionAt(int x, int y) {
        for (Bomb bomb: bombs) {
            BombExplosion bombExplosion = bomb.getBombExplosionAtPos(x, y);
            if (bombExplosion != null) {
                return bombExplosion;
            }
        }
        return null;
    }

    public Entity getEntityAtPos(double x, double y) {
        return this.entities[(int) x + (int) y * this.level.getWidth()];
    }

    public Entity getEntity(double x, double y, Character character) {
        Entity entity;
        entity = getBombAtPos(x, y);
        if (entity != null) {
            return entity;
        }

        entity = getEntityAtPos((int) x, (int) y);
        if (entity != null) {
            return entity;
        }

        entity = getCharacterAtPosAndExclude((int) x, (int) y, character);
        if (entity != null) {
            return entity;
        }

        entity = getExplosionAt((int) x, (int) y);
        return entity;
    }

    private void gameMasterMode() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        if (this.keyboard_input.f2) {
            this.resetGameSettings();
            this.changeLevel(2);
        }

        if (this.keyboard_input.f1) {
            this.resetGameSettings();
            this.changeLevel(1);
        }

        if (this.keyboard_input.f3) {
            this.resetGameSettings();
            this.changeLevel(3);
        }

        if (this.keyboard_input.f4) {
            this.resetGameSettings();
            this.changeLevel(4);
        }

        if (this.keyboard_input.f5) {
            this.resetGameSettings();
            this.changeLevel(5);
        }

        if (this.keyboard_input.esc) {
            for (Character character: this.characters) {
                if (character instanceof Enemy) {
                    character.dead();
                }
            }
        }
    }

    public int getScreenNum() {
        return this.screenNum;
    }

    public int getTime() {
        return this.time;
    }

    public int getPoint() {
        return this.point;
    }

    public int getLive() {
        return this.live;
    }

    public int getWidth() {
        return this.level.getWidth();
    }

    public int getHeight() {
        return this.level.getHeight();
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Game getGame() {
        return this.gameplay;
    }

}
