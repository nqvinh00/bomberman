package bomberman.Graphics;

import bomberman.Entities.Character.Bomber;
import bomberman.Entities.Entity;
import bomberman.Game;
import bomberman.GameBoard;

import java.awt.*;
import java.util.Arrays;

public class Screen {
    protected int width;
    protected int height;
    public int[] pixels;
    private int defaultColor = 0xffff00ff; //RGBA
    public static int deltax = 0;
    public static int deltay = 0;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
    }

    public void clear() {
        Arrays.fill(pixels, 0);
    }

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

    public static void setDelta(int x0, int y0) {
        deltax = x0;
        deltay = y0;
    }

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

    public int[] positionToDraw(int width, int height, String s, FontMetrics fontMetrics) {
        int[] result = new int[2];
        result[0] = (width - fontMetrics.stringWidth(s)) / 2;
        result[1] = fontMetrics.getAscent() + (height - (fontMetrics.getAscent() + fontMetrics.getDescent())) / 2;
        return result;
    }

    public void drawGameEnding(Graphics graphics, int points) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, this.width * Game.scale_factor, this.height * Game.scale_factor);
        FontMetrics fontMetrics = graphics.getFontMetrics();

        Font font = new Font("Hack", Font.PLAIN, 60);
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        int[] result = positionToDraw(this.width * Game.scale_factor, this.height * Game.scale_factor, "Game over", fontMetrics);
        graphics.drawString("Game Over", result[0], result[1]);

        font = new Font("Hack", Font.PLAIN, 30);
        graphics.setFont(font);
        graphics.setColor(Color.YELLOW);
        result = positionToDraw(this.width * Game.scale_factor, this.height * Game.scale_factor, "Points: " + points, fontMetrics);
        graphics.drawString("Points: " + points, result[0], result[1]);
    }

    public void drawLevelChanging(Graphics graphics, int level) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, this.width * Game.scale_factor, this.height * Game.scale_factor);
        Font font = new Font("Hack", Font.PLAIN, 60);
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int[] result = positionToDraw(this.width * Game.scale_factor, this.height * Game.scale_factor, "Level: " + level, fontMetrics);
        graphics.drawString("Level " + level, result[0], result[1]);
    }

    public void drawGamePausing(Graphics graphic) {
        Font font = new Font("Hack", Font.PLAIN, 60);
        graphic.setFont(font);
        graphic.setColor(Color.WHITE);
        FontMetrics fontMetrics = graphic.getFontMetrics();
        int[] result = positionToDraw(this.width * Game.scale_factor, this.height * Game.scale_factor, "Paused", fontMetrics);
        graphic.drawString("Paused", result[0], result[1]);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
