package bomberman.Control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
    private final boolean[] keys = new boolean[1000];
    public boolean up, down, right, left, space;
    // use in game master mode,
    public boolean f1, f2, f3, f4, f5, esc;

    /**
     * define movement and functional key.
     */
    public void update() {
        this.up = keys[KeyEvent.VK_UP];
        this.down = keys[KeyEvent.VK_DOWN];
        this.right = keys[KeyEvent.VK_RIGHT];
        this.left = keys[KeyEvent.VK_LEFT];
        this.space = keys[KeyEvent.VK_SPACE];
        this.f1 = keys[KeyEvent.VK_F1];
        this.f2 = keys[KeyEvent.VK_F2];
        this.f3 = keys[KeyEvent.VK_F3];
        this.f4 = keys[KeyEvent.VK_F4];
        this.f5 = keys[KeyEvent.VK_F5];
        this.esc = keys[KeyEvent.VK_ESCAPE];
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = false;
    }
}
