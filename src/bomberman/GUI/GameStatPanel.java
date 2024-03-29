package bomberman.GUI;

import javax.swing.*;
import bomberman.Game;
import java.awt.*;

public class GameStatPanel extends JPanel {
    private final JLabel time;
    private final JLabel points;
    private final JLabel lives;

    /**
     * constructor.
     * @param game gameplay
     */
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

    /**
     * set time text.
     * @param time value
     */
    public void setTime(int time) {
        this.time.setText("Time: " + time);
    }

    /**
     * set lives text.
     * @param lives value
     */
    public void setLives(int lives) {
        this.lives.setText("Lives: " + lives);
    }

    /**
     * set points text.
     * @param points value.
     */
    public void setPoints(int points) {
        this.points.setText("Points: " + points);
    }
}
