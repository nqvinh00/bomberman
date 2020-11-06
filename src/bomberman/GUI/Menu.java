package bomberman.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.security.Key;
import javax.swing.*;

import bomberman.GUI.GameFrame;
import bomberman.GameBoard;

public class Menu extends JMenuBar {
    public GameFrame frame;
    private JMenu gameMenu = new JMenu("Game");
    private JMenu option = new JMenu("Options");
    private JMenu help = new JMenu("Help");

    public Menu(GameFrame frame) {
        this.frame = frame;

        // Game menu
        JMenuItem newGame = new JMenuItem("New game");
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newGame.addActionListener(new GameMenuListener(frame));
        this.gameMenu.add(newGame);
        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        exit.addActionListener(new GameMenuListener(frame));
        this.gameMenu.add(exit);

        // Option menu
        JMenuItem pause = new JMenuItem("Pause");
        pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        pause.addActionListener(new OptionMenuListener(frame));
        this.option.add(pause);
        JMenuItem unpause = new JMenuItem("Unpause");
        unpause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        unpause.addActionListener(new OptionMenuListener(frame));
        this.option.add(unpause);

        // Help
        JMenuItem keyboard = new JMenuItem("Keyboard control");
        keyboard.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        keyboard.addActionListener(new HelpMenuListener(frame));
        this.help.add(keyboard);
        JMenuItem info = new JMenuItem("About");
        info.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
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
                frame.newGame();
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

            }

            if (actionEvent.getActionCommand().equals("Unpause")) {

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

            }

            if (actionEvent.getActionCommand().equals("About")) {

            }
        }
    }
}