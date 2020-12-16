package bomberman.GUI;

import bomberman.Game;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameFrame extends JFrame {
    private final Game game;
    private final GamePanel gamePanel;
    private final JPanel container;
    private final GameStatPanel gameStatPanel;

    /**
     * constructor.
     * @throws IOException throw io exception
     * @throws LineUnavailableException throw when line audio error
     * @throws UnsupportedAudioFileException throw when audio type not supported
     */
    public GameFrame() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        this.setJMenuBar(new Menu(this));
        this.container = new JPanel(new BorderLayout());
        this.gamePanel = new GamePanel(this);
        this.gameStatPanel = new GameStatPanel(this.gamePanel.getGame());
        this.container.add(gameStatPanel, BorderLayout.PAGE_START);
        this.container.add(gamePanel, BorderLayout.PAGE_END);
        this.game = this.gamePanel.getGame();
        this.add(container);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.game.start();
    }

    /**
     * new game.
     * @throws IOException throw io exception
     * @throws LineUnavailableException throw when line audio error
     */
    public void newGame() throws IOException, LineUnavailableException {
        this.game.getGameBoard().newGame();
    }

    /**
     * pause game.
     */
    public void pauseGame() {
        this.game.getGameBoard().pauseGame();
    }

    /**
     * resume game.
     */
    public void resumeGame() {
        this.game.getGameBoard().resumeGame();
    }

    /**
     * set time per level.
     * @param time of level
     */
    public void setTime(int time) {
        this.gameStatPanel.setTime(time);
    }

    /**
     * set lives for bomber.
     * @param lives value
     */
    public void setLives(int lives) {
        this.gameStatPanel.setLives(lives);
    }

    /**
     * point for bomber.
     * @param points value
     */
    public void setPoints(int points) {
        this.gameStatPanel.setPoints(points);
    }
}
