package bomberman.GUI;

import javax.swing.*;
import bomberman.Game;
import java.awt.*;

public class GameStatPanel extends JPanel {
    private final JLabel time;
    private final JLabel points;
    private final JLabel lives;

    public GameStatPanel(Game game) {
        this.setLayout(new GridLayout());
        this.time = new JLabel("Time: " + game.getGameBoard().getTime());
        this.time.setForeground(Color.WHITE);
        this.time.setHorizontalAlignment(JLabel.CENTER);

        this.points = new JLabel("Points: " + game.getGameBoard().getPoint());
        this.points.setForeground(Color.WHITE);
        this.points.setHorizontalAlignment(JLabel.CENTER);

        this.lives = new JLabel("Lives: " + game.getGameBoard().getLive());
        this.lives.setForeground(Color.WHITE);
        this.lives.setHorizontalAlignment(JLabel.CENTER);

        this.add(this.time);
        this.add(this.lives);
        this.add(this.points);
        this.setBackground(Color.GRAY);
        this.setPreferredSize(new Dimension(0, 40));
    }

    public void setTime(int time) {
        this.time.setText("Time: " + time);
    }

    public void setLives(int lives) {
        this.lives.setText("Lives: " + lives);
    }

    public void setPoints(int points) {
        this.points.setText("Points: " + points);
    }
}
