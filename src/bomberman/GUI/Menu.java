package bomberman.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.*;

public class Menu extends JMenuBar {
    public GameFrame frame;
    private JMenu gameMenu = new JMenu("Game");
    private JMenu option = new JMenu("Options");
    private JMenu help = new JMenu("Help");

    public Menu(GameFrame frame) {
        this.frame = frame;

        // Game menu
        JMenuItem newGame = new JMenuItem("New game");
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        newGame.addActionListener(new GameMenuListener(frame));
        this.gameMenu.add(newGame);
        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
        exit.addActionListener(new GameMenuListener(frame));
        this.gameMenu.add(exit);

        // Option menu
        JMenuItem pause = new JMenuItem("Pause");
        pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        pause.addActionListener(new OptionMenuListener(frame));
        this.option.add(pause);
        JMenuItem unpause = new JMenuItem("Resume");
        unpause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
        unpause.addActionListener(new OptionMenuListener(frame));
        this.option.add(unpause);

        // Help
        JMenuItem keyboard = new JMenuItem("Keyboard control");
        keyboard.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        keyboard.addActionListener(new HelpMenuListener(frame));
        this.help.add(keyboard);
        JMenuItem info = new JMenuItem("About");
        info.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
        info.addActionListener(new HelpMenuListener(frame));
        this.help.add(info);
    }

    static class GameMenuListener implements ActionListener {
        private GameFrame frame;

        public GameMenuListener(GameFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getActionCommand().equals("New Game")) {
                try {
                    frame.newGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (actionEvent.getActionCommand().equals("Exit")) {
                frame.dispose();
            }
        }
    }

    static class OptionMenuListener implements ActionListener {
        private GameFrame frame;

        public OptionMenuListener(GameFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getActionCommand().equals("Pause")) {
                this.frame.pauseGame();
            }

            if (actionEvent.getActionCommand().equals("Unpause")) {
                this.frame.resumeGame();
            }
        }
    }

    static class HelpMenuListener implements ActionListener {
        private GameFrame frame;

        public HelpMenuListener(GameFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getActionCommand().equals("Keyboard")) {
                JOptionPane.showMessageDialog(null, "Up, Down, Right, Left arrow button to move bomber.\nSpace button to place bomb.");
            }

            if (actionEvent.getActionCommand().equals("About")) {
                JOptionPane.showMessageDialog(null, "Nguyen Quang Vinh - UET\nK63K1 - 18021429.");
            }
        }
    }
}