package bomberman.GUI;

import bomberman.Game;
import bomberman.GameBoard;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Game game;

    public GamePanel(GameFrame frame) {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(Game.width * Game.scale_factor, Game.height * Game.scale_factor));
        this.game = new Game(frame);
        this.add(game);
        this.setVisible(true);
        this.setFocusable(true);
    }

    public void resize() {
        this.setPreferredSize(new Dimension(Game.width * Game.scale_factor, Game.height * Game.scale_factor));
        this.revalidate();
        this.repaint();
    }

    public Game getGame() {
        return this.game;
    }
}