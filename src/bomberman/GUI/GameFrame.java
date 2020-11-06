package bomberman.GUI;

import javax.swing.*;

import java.awt.*;

public class GameFrame extends JFrame {
    private GamePanel game_panel = new GamePanel(this);
    private JPanel container = new JPanel(new BorderLayout());
    private Menu menu;

    public GameFrame() {
        this.setJMenuBar(new Menu(this));
        game_panel.setLayout(new BorderLayout());
//        game_panel.setPreferredSize();
    }
    public void newGame() {

    }
}
