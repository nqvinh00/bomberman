package bomberman.GUI;

import bomberman.Game;
import bomberman.GameBoard;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    private JPanel container;
    private GameStatPanel gameStatPanel;
    private Game game;
    private GameBoard gameBoard;

    public GameFrame() throws IOException {
        this.setJMenuBar(new Menu(this));
        this.container = new JPanel(new BorderLayout());
        this.gamePanel = new GamePanel(this);
        this.game = this.gamePanel.getGame();
        this.gameStatPanel = new GameStatPanel(this.game);
        this.container.add(gameStatPanel, BorderLayout.PAGE_START);
        this.container.add(gamePanel, BorderLayout.PAGE_END);
        this.gameBoard = this.game.getGameBoard();
        this.container.setVisible(true);
        this.add(container);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.game.start();
    }

    public void newGame() throws IOException {
        this.gameBoard.newGame();
    }

    public void changeLevel(int level) throws IOException {
        this.gameBoard.changeLevel(level);
    }

    public void pauseGame() {
        this.gameBoard.pauseGame();
    }

    public void resumeGame() {
        this.gameBoard.resumeGame();
    }

    public void setTime(int time) {
        this.gameStatPanel.setTime(time);
    }

    public void setLives(int lives) {
        this.gameStatPanel.setLives(lives);
    }

    public void setPoints(int points) {
        this.gameStatPanel.setPoints(points);
    }
}
