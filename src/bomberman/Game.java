package bomberman;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import bomberman.Graphics.Screen;
import bomberman.GUI.GameFrame;
import bomberman.Control.Input;

public class Game extends Canvas {
    // Game components
    private final GameBoard gameBoard;
    private final Screen screen;
    private final GameFrame gameFrame;
    private final Input keyboard_input;
    private final Clip clip = AudioSystem.getClip();

    // Game configs
    public static int boardsprite_size = 16;
    public static int width = 247;
    public static int height = 208;
    public static int scale_factor = 3;
    public static String window_title = "Bomberman";
    public static int screen_delay = 2;
    public static int bomb_number = 1;
    public static int bomb_range = 1;
    public static double player_speed = 1.0;
    public static int time = 300;
    public static int point = 0;
    public static int live = 3;
    private boolean running = false;
    private boolean paused = false;
    private final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private final int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public Game(GameFrame gameFrame) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        this.gameFrame = gameFrame;
        this.gameFrame.setTitle(window_title);
        this.keyboard_input = new Input();
        this.addKeyListener(this.keyboard_input);
        this.screen = new Screen(width, height);
        this.gameBoard = new GameBoard(this, keyboard_input, screen);
        this.addKeyListener(keyboard_input);
        this.clip.open(AudioSystem.getAudioInputStream(new File("res/audio/bg.wav")));
    }

    public void render() throws IOException {
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            this.createBufferStrategy(3);
            return;
        }

        this.screen.clear();
        this.gameBoard.render(this.screen);

        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

        Graphics graphics = buffer.getDrawGraphics();
        // awt components
        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        graphics.dispose();
        buffer.show();
    }

    public void renderScreen() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            this.createBufferStrategy(3);
            return;
        }

        this.screen.clear();
        Graphics graphics = buffer.getDrawGraphics();
        this.gameBoard.drawScreen(graphics);
        graphics.dispose();
        buffer.show();
    }

    public void run() {
        running = true;
        paused = false;
    }

    public void update() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        this.keyboard_input.update();
        this.gameBoard.update();
    }

    public void start() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        this.running = true;
        long time = System.nanoTime();
        long timer = System.currentTimeMillis();
        double nano = 1000000000.0 / 60;
        double deltaT = 0;
        this.requestFocus();
        this.clip.loop(-1);
        this.gameFrame.setTitle(Game.window_title);
        while (this.running) {
            if (this.gameBoard.getLive() <= 0) {
                this.clip.stop();
            }
            long timeNow = System.nanoTime();
            deltaT += (timeNow - time) / nano;
            time = timeNow;
            while (deltaT >= 1) {
                this.update();
                deltaT--;
            }

            if (this.paused) {
                if (Game.screen_delay <= 0) {
                    this.gameBoard.setScreenNum(-1);
                    this.paused = false;
                }
                this.renderScreen();
            } else {
                this.render();
            }

            if (System.currentTimeMillis() - timer > 1000) {
                if (this.paused) {
                    this.gameFrame.setTime(this.gameBoard.getTime());
                } else {
                    int gameTime = this.gameBoard.getTime() - 1;
                    this.gameFrame.setTime(gameTime);
                    this.gameBoard.setTime(gameTime);
                }
                this.gameFrame.setPoints(this.gameBoard.getPoint());
                this.gameFrame.setLives(Game.this.gameBoard.getLive());
                timer += 1000;
                if (this.gameBoard.getScreenNum() == 2) {
                    --Game.screen_delay;
                }
            }
        }
    }

    public GameBoard getGameBoard() {
        return this.gameBoard;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setPaused(boolean isPaused) {
        this.paused = isPaused;
    }

    public boolean isPaused() {
        return this.paused;
    }
}
