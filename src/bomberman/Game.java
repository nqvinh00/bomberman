package bomberman;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import bomberman.Graphics.Screen;
import bomberman.GUI.GameFrame;
import bomberman.Control.Input;

public class Game extends Canvas {
    // Game components
    private GameBoard gameBoard;
    private Screen screen;
    private GameFrame game;
    private Input keyboard_input;

    // Game configs
    public static int boardsprite_size = 16;
    public static int width = 247; // boardsprite_size * (31/2) - 1
    public static int height = 208;
    public static int scale_factor = 3;
    public static String window_title = "Bomberman";
    protected static int screen_delay = 3;
    public static int bomb_number = 1;
    public static int bomb_range = 1;
    public static double player_speed = 1.0;
    public static int time = 300;
    public static int point = 0;
    public static int live = 3;
    private boolean isRunning = false;
    private boolean isPaused = true;
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public Game(GameFrame game) {
        this.game = game;
        this.game.setTitle(window_title);
        this.keyboard_input = new Input();
        this.addKeyListener(this.keyboard_input);
        this.screen = new Screen(width, height);
        this.gameBoard = new GameBoard(this, keyboard_input, screen);
    }

    public void render() {
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            this.createBufferStrategy(3);
            return;
        }

        this.screen.clear();
        this.gameBoard.render(screen);

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics graphics = buffer.getDrawGraphics();
        // awt components
        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
