package bomberman.Graphics;

import bomberman.Entities.Character.Bomber;
import bomberman.Entities.Entity;
import bomberman.Game;
import bomberman.GameBoard;
import bomberman.Sound.Sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Screen {
    protected int width;
    protected int height;
    public int[] pixels;
    private final int defaultColor = 0xffff00ff; //RGBA
    public static int deltax = 0;
    public static int deltay = 0;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
    }

    /**
     * fill all pixels with 0 to clear map
     */
    public void clear() {
        Arrays.fill(pixels, 0);
    }

    /**
     * render entity.
     * @param x pos
     * @param y pos
     * @param entity to render
     */
    public void renderEntity(int x, int y, Entity entity) {
        x -= deltax;
        y -= deltay;
        int x_;
        int y_;

        for (int i = 0; i < entity.getSprite().getSize(); i++) {
            y_ = y + i;
            for (int j = 0; j < entity.getSprite().getSize(); j++) {
                x_ = x + j;
                if (x_ < -entity.getSprite().getSize() || x_ >= this.width || y_ < 0 || y_ >= this.height) {
                    break;
                }

                if (x_ < 0) {
                    x_ = 0;
                }

                int color = entity.getSprite().getPixel(j + i * entity.getSprite().getSize());
                if (color != this.defaultColor) {
                    this.pixels[x_ + y_ * this.width] = color;
                }
            }
        }
    }

    /**
     * render entity with a sprite like grass, portal, item.
     * @param x pos
     * @param y pos
     * @param entity to render
     * @param sprite under
     */
    public void renderEntityUnderSprite(int x, int y, Entity entity, Sprite sprite) {
        x -= deltax;
        y -= deltay;
        int x_;
        int y_;

        for (int i = 0; i < entity.getSprite().getSize(); i++) {
            y_ = y + i;
            for (int j = 0; j < entity.getSprite().getSize(); j++) {
                x_ = x + j;
                if (x_ < -entity.getSprite().getSize() || x_ >= this.width || y_ < 0 || y_ >= this.height) {
                    break;
                }

                if (x_ < 0) {
                    x_ = 0;
                }

                int color = entity.getSprite().getPixel(j + i * entity.getSprite().getSize());
                if (color != this.defaultColor) {
                    this.pixels[x_ + y_ * this.width] = color;
                } else {
                    this.pixels[x_ + y_ * this.width] = sprite.getPixel(j + i * sprite.getSize());
                }
            }
        }
    }

    /**
     * delta setter.
     * @param x0 delta
     * @param y0 delta
     */
    public static void setDelta(int x0, int y0) {
        deltax = x0;
        deltay = y0;
    }

    /**
     * calculate delta with bomber pos and board size
     * @param bomber character
     * @param board game
     * @return delta
     */
    public static int calculateDelta(Bomber bomber, GameBoard board) {
        if (bomber == null) {
            return 0;
        }
        int temp = deltax;
        double x = bomber.getX() / 16;
        int first = board.getWidth() / 4;
        int last = board.getWidth() - first;
        if (x > first + 0.5 && x < last - 0.5) {
            temp = (int) bomber.getX() - (Game.width / 2);
        }
        return temp;
    }

    /**
     * draw game ending screen.
     * @param graphics param
     * @param points of bomber
     */
    public void drawGameEnding(Graphics graphics, int points) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, this.width * Game.scale_factor, this.height * Game.scale_factor);

        Font font = new Font("Hack", Font.PLAIN, 60);
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        graphics.drawString("Game Over", 230, 270);

        font = new Font("Hack", Font.PLAIN, 30);
        graphics.setFont(font);
        graphics.setColor(Color.YELLOW);
        graphics.drawString("Points: " + points, 290, 332);
    }

    public void drawLevelChanging(Graphics graphics, int level) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, this.width * Game.scale_factor, this.height * Game.scale_factor);
        Font font = new Font("Hack", Font.PLAIN, 60);
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        graphics.drawString("Level " + level, 226, 322);
    }

    public void drawGamePausing(Graphics graphic) {
        Font font = new Font("Hack", Font.PLAIN, 60);
        graphic.setFont(font);
        graphic.setColor(Color.WHITE);
        graphic.drawString("Paused", 226, 322);
    }

    public void drawGameWinning(Graphics graphics, int points) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, this.width * Game.scale_factor, this.height * Game.scale_factor);

        Font font = new Font("Hack", Font.PLAIN, 60);
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        graphics.drawString("Win", 320, 270);

        font = new Font("Hack", Font.PLAIN, 30);
        graphics.setFont(font);
        graphics.setColor(Color.YELLOW);
        graphics.drawString("Points: " + points, 270, 332);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
