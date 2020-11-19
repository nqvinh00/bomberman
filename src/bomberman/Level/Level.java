package bomberman.Level;

import bomberman.Entities.BoardSprite.Brick;
import bomberman.Entities.BoardSprite.Grass;
import bomberman.Entities.BoardSprite.Portal;
import bomberman.Entities.BoardSprite.Wall;
import bomberman.Entities.Character.Bomber;
import bomberman.Entities.Item.BombRangeIncrement;
import bomberman.Entities.Item.MoreBombs;
import bomberman.Entities.Item.SpeedUp;
import bomberman.Entities.Layer;
import bomberman.Game;
import bomberman.GameBoard;
import bomberman.Graphics.Screen;
import bomberman.Graphics.Sprite;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Level {
    protected int width;
    protected int height;
    protected int level;
    protected String[] lines;
    protected GameBoard gameBoard;

    public Level(String filePath, GameBoard gameBoard) throws IOException {
        this.loadLevel(filePath);
        this.gameBoard = gameBoard;
    }

    public void loadLevel(String filePath) throws IOException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        this.level = scanner.nextInt();
        this.height = scanner.nextInt();;
        this.width = scanner.nextInt();;
        this.lines = new String[this.height];
        scanner.nextLine();
        for (int i = 0; i < this.height; i++) {
            lines[i] = scanner.nextLine().substring(0, this.width);
        }
        scanner.close();
    }

    public void createEntities() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.addEntity(j, i, lines[i].charAt(j));
            }
        }
    }

    public void addEntity(int x, int y, char c) {
        int position = x + y * this.width;
        Layer layer;
        switch (c) {
            case '#': // Wall
                this.gameBoard.addEntity(position, new Wall(x, y, Sprite.wall));
                break;
            case 'b': // powerup_bomb item under brick
                layer = new Layer(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick));
                if (!this.gameBoard.isItemUsed(x, y, this.level)) {
                    layer.addEntityBeforeLast(new MoreBombs(x, y, Sprite.powerup_bombs, this.level));
                }
                this.gameBoard.addEntity(position, layer);
                break;
            case 'f': // powerup_frame item under brick
                layer = new Layer(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick));
                if (!this.gameBoard.isItemUsed(x, y, this.level)) {
                    layer.addEntityBeforeLast(new BombRangeIncrement(x, y, Sprite.powerup_flames, this.level));
                }
                this.gameBoard.addEntity(position, layer);
                break;
            case 's': // powerup_speed item under brick
                layer = new Layer(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick));
                if (!this.gameBoard.isItemUsed(x, y, this.level)) {
                    layer.addEntityBeforeLast(new SpeedUp(x, y, Sprite.powerup_speed, this.level));
                }
                this.gameBoard.addEntity(position, layer);
                break;
            case '*': // brick
                layer = new Layer(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick));
                this.gameBoard.addEntity(position, layer);
                break;
            case 'x': // portal under brick
                layer = new Layer(x, y, new Grass(x, y, Sprite.grass), new Portal(x, y, Sprite.portal, this.gameBoard), new Brick(x, y, Sprite.brick));
                this.gameBoard.addEntity(position, layer);
                break;
            case 'p': // bomber
                this.gameBoard.addCharacter(new Bomber(x * Game.boardsprite_size, y * Game.boardsprite_size + Game.boardsprite_size, this.gameBoard));
                Screen.setDelta(0, 0);
                this.gameBoard.addEntity(position, new Grass(x, y, Sprite.grass));
                break;
            default: // grass for default
                this.gameBoard.addEntity(position, new Grass(x, y, Sprite.grass) );
                break;

        }
    }

    public int getLevel() {
        return this.level;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
