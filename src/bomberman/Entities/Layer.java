package bomberman.Entities;

import bomberman.Entities.BoardSprite.Brick;
import bomberman.Graphics.Screen;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Layer extends Entity {
    protected ArrayList<Entity> entities = new ArrayList<Entity>();

    public Layer(int _x, int _y, Entity ... entities) {
        x = _x;
        y = _y;

        for (int i = 0; i < entities.length; i++) {
            this.entities.add(entities[i]);
            if (i > 0) {
                if (entities[i] instanceof Brick) {
                    ((Brick)entities[i]).addGrass(entities[i - 1].getSprite());
                }
            }
        }
    }

    @Override
    public void update() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        this.clearRemovedEntity();
        this.getLastEntity().update();
    }

    @Override
    public void render(Screen screen) throws IOException {
        this.getLastEntity().render(screen);
    }

    @Override
    public boolean isCollided(Entity e) throws IOException {
        return getLastEntity().isCollided(e);
    }

    public Entity getLastEntity() {
        return this.entities.get(entities.size() - 1);
    }

    public void clearRemovedEntity() {
        Entity last = this.getLastEntity();
        if (last.isRemoved()) {
            entities.remove(last);
        }
    }

    public void addEntityBeforeLast(Entity e) {
        this.entities.add(entities.size() - 1, e);
    }
}
