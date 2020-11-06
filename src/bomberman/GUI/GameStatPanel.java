package bomberman.GUI;

import javax.swing.*;
import bomberman.GameBoard;

import java.awt.*;

public class GameStatPanel extends JPanel {
    private JLabel time;
    private JLabel points;
    private JLabel lives;

    public GameStatPanel(GameBoard game) {
//        this.setLayout(new BorderLayout());
        this.setLayout(new GridLayout());
        time = new JLabel("Time: ");
    }
}
