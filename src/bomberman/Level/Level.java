package bomberman.Level;

import bomberman.Entities.BoardSprite.Wall;
import bomberman.GameBoard;
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
        this.height = scanner.nextInt();
        this.width = scanner.nextInt();
        this.lines = new String[this.height];

        for (int i = 0; i < this.height; i++) {
            lines[i] = scanner.nextLine().substring(0, this.width);
        }
        scanner.close();
    }

    public void createEntities() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                addEntity(j, i, lines[i].charAt(j));
            }
        }
    }

    public void addEntity(int x, int y, char c) {
        int position = x + y * this.width;
        switch (c) {
            case '#': // Wall
                this.gameBoard.addEntity(position, new Wall(x, y, Sprite.wall));
                break;
        }
    }
}
