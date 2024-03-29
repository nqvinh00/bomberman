package bomberman.Level;

import bomberman.Entities.BoardSprite.Brick;
import bomberman.Entities.BoardSprite.Grass;
import bomberman.Entities.BoardSprite.Portal;
import bomberman.Entities.BoardSprite.Wall;
import bomberman.Entities.Character.*;
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
    public static char[][] charMap;

    /**
     * constructor.
     * @param filePath of file level
     * @param gameBoard param
     * @throws IOException throw io exception
     */
    public Level(String filePath, GameBoard gameBoard) throws IOException {
        this.loadLevel(filePath);
        this.gameBoard = gameBoard;
        charMap = new char[this.height][this.width];
    }

    /**
     * read file level to generate map level.
     * @param filePath of file level
     * @throws IOException throw io exception
     */
    public void loadLevel(String filePath) throws IOException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        this.level = scanner.nextInt();
        this.height = scanner.nextInt();
        this.width = scanner.nextInt();
        this.lines = new String[this.height];
        scanner.nextLine();
        for (int i = 0; i < this.height; i++) {
            lines[i] = scanner.nextLine().substring(0, this.width);
        }
        scanner.close();
    }

    /**
     * create entities at pos by char in file level.
     */
    public void createEntities() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                charMap[i][j] = lines[i].charAt(j);
                this.addEntity(j, i, charMap[i][j]);
            }
        }
    }

    /**
     * add entity and layer entities with char and pos.
     * @param x pos
     * @param y pos
     * @param c char
     */
    public void addEntity(int x, int y, char c) {
        int position = x + y * this.width;
        Layer layer;
        switch (c) {
            // Wall
            case '#' -> this.gameBoard.addEntity(position, new Wall(x, y, Sprite.wall));
            // powerup_bomb item under brick
            case 'b' -> {
                layer = new Layer(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick));
                if (!this.gameBoard.isItemUsed(x, y, this.level)) {
                    layer.addEntityBeforeLast(new MoreBombs(x, y, Sprite.powerup_bombs, this.level));
                }
                this.gameBoard.addEntity(position, layer);
            }
            // powerup_frame item under brick
            case 'f' -> {
                layer = new Layer(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick));
                if (!this.gameBoard.isItemUsed(x, y, this.level)) {
                    layer.addEntityBeforeLast(new BombRangeIncrement(x, y, Sprite.powerup_flames, this.level));
                }
                this.gameBoard.addEntity(position, layer);
            }
            // powerup_speed item under brick
            case 's' -> {
                layer = new Layer(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick));
                if (!this.gameBoard.isItemUsed(x, y, this.level)) {
                    layer.addEntityBeforeLast(new SpeedUp(x, y, Sprite.powerup_speed, this.level));
                }
                this.gameBoard.addEntity(position, layer);
            }
            // brick
            case '*' -> {
                layer = new Layer(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick));
                this.gameBoard.addEntity(position, layer);
            }
            // portal under brick
            case 'x' -> {
                layer = new Layer(x, y, new Grass(x, y, Sprite.grass), new Portal(x, y, Sprite.portal, this.gameBoard),
                        new Brick(x, y, Sprite.brick));
                this.gameBoard.addEntity(position, layer);
            }
            // bomber
            case 'p' -> {
                this.gameBoard.addCharacter(new Bomber(x * Game.boardsprite_size,
                        y * Game.boardsprite_size + Game.boardsprite_size, this.gameBoard));
                Screen.setDelta(0, 0);
                this.gameBoard.addEntity(position, new Grass(x, y, Sprite.grass));
            }
            // balloom
            case '1' -> {
                this.gameBoard.addCharacter(new Balloom(x * Game.boardsprite_size,
                        y * Game.boardsprite_size + Game.boardsprite_size, this.gameBoard));
                this.gameBoard.addEntity(position, new Grass(x, y, Sprite.grass));
            }
            // oneal
            case '2' -> {
                this.gameBoard.addCharacter(new Oneal(x * Game.boardsprite_size,
                        y * Game.boardsprite_size + Game.boardsprite_size, this.gameBoard));
                this.gameBoard.addEntity(position, new Grass(x, y, Sprite.grass));
            }
            //doll
            case '3' -> {
                this.gameBoard.addCharacter(new Doll(x * Game.boardsprite_size,
                        y * Game.boardsprite_size + Game.boardsprite_size, this.gameBoard));
                this.gameBoard.addEntity(position, new Grass(x, y, Sprite.grass));
            }
            // Minvo
            case '4' -> {
                this.gameBoard.addCharacter(new Minvo(x * Game.boardsprite_size,
                        y * Game.boardsprite_size + Game.boardsprite_size, this.gameBoard));
                this.gameBoard.addEntity(position, new Grass(x, y, Sprite.grass));
            }
            // Kondoria
            case '5' -> {
                this.gameBoard.addCharacter(new Kondoria(x * Game.boardsprite_size,
                        y * Game.boardsprite_size + Game.boardsprite_size, this.gameBoard));
                this.gameBoard.addEntity(position, new Grass(x, y, Sprite.grass));
            }

            // grass for default
            default -> this.gameBoard.addEntity(position, new Grass(x, y, Sprite.grass));
        }
    }

    /**
     * get current level.
     * @return level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * width getter.
     * @return width defines in  file level
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * height getter.
     * @return height defines in  file level
     */
    public int getHeight() {
        return this.height;
    }
}
