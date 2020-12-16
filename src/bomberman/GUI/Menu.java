package bomberman.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

public class Menu extends JMenuBar {
    public GameFrame frame;
    private final JMenu gameMenu = new JMenu("Game");
    private final JMenu option = new JMenu("Options");
    private final JMenu help = new JMenu("Help");

    /**
     * constructor.
     * @param frame game frame
     */
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

        this.add(this.gameMenu);
        this.add(this.option);
        this.add(this.help);
    }

    static class GameMenuListener implements ActionListener {
        private final GameFrame frame;

        /**
         * constructor for game menu.
         * @param frame game frame
         */
        public GameMenuListener(GameFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getActionCommand().equals("New game")) {
                try {
                    frame.newGame();
                } catch (IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }
            }

            if (actionEvent.getActionCommand().equals("Exit")) {
                frame.dispose();
            }
        }
    }

    static class OptionMenuListener implements ActionListener {
        private final GameFrame frame;

        /**
         * constructor for option menu.
         * @param frame game frame
         */
        public OptionMenuListener(GameFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getActionCommand().equals("Pause")) {
                this.frame.pauseGame();
            }

            if (actionEvent.getActionCommand().equals("Resume")) {
                this.frame.resumeGame();
            }
        }
    }

    static class HelpMenuListener implements ActionListener {
        private final GameFrame frame;

        /**
         * constructor for help menu.
         * @param frame game frame
         */
        public HelpMenuListener(GameFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getActionCommand().equals("Keyboard control")) {
                JOptionPane.showMessageDialog(null, "Up, Down, Right, Left arrow button to move "
                        + "bomber.\nSpace button to place bomb.");
            }

            if (actionEvent.getActionCommand().equals("About")) {
                JOptionPane.showMessageDialog(null, "Nguyen Quang Vinh - UET\nK63K1 - 18021429.");
            }
        }
    }
}