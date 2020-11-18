package bomberman.Control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class Input implements KeyListener {
    private boolean[] keys = new boolean[256];
    public boolean up, down, right, left, space;

    public void update() {
        this.up = keys[KeyEvent.VK_UP];
        this.down = keys[KeyEvent.VK_DOWN];
        this.right = keys[KeyEvent.VK_RIGHT];
        this.left = keys[KeyEvent.VK_LEFT];
        this.space = keys[KeyEvent.VK_SPACE];
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
